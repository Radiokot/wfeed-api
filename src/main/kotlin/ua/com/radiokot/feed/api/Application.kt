package ua.com.radiokot.feed.api

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.core.validation.JavalinValidation
import mu.KotlinLogging
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import ua.com.radiokot.feed.api.categories.CategoriesJsonApiController
import ua.com.radiokot.feed.api.categories.legacy.LegacyCategoriesApiController
import ua.com.radiokot.feed.api.di.KLoggerKoinLogger
import ua.com.radiokot.feed.api.di.injectionModules
import ua.com.radiokot.feed.api.jsonapi.JsonApiDate
import ua.com.radiokot.feed.api.posts.PostsJsonApiController
import java.util.*

@KoinApiExtension
object Application : KoinComponent {

    @JvmStatic
    fun main(args: Array<String>) {
        startKoin {
            logger(KLoggerKoinLogger(KotlinLogging.logger("Koin")))

            fileProperties("/database.properties")
            environmentProperties()

            modules(injectionModules)
        }

        JavalinValidation.register(Date::class.java, JsonApiDate.fromStringConverter)

        Javalin
            .create { config ->
                config.showJavalinBanner = false
            }
            .routes {
                path("categories") {
                    val controller = get<LegacyCategoriesApiController>()
                    ApiBuilder.get("/", controller::getAll)
                }

                path("v2/") {
                    path("categories") {
                        val controller = get<CategoriesJsonApiController>()
                        ApiBuilder.get("/", controller::getAll)
                    }

                    path("posts") {
                        val controller = get<PostsJsonApiController>()
                        ApiBuilder.get("/", controller::get)
                    }
                }
            }
            .start(getKoin().getProperty("PORT", 8041))
    }
}