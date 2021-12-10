package ua.com.radiokot.feed.api

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.core.util.Header
import io.javalin.core.validation.JavalinValidation
import mu.KotlinLogging
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import sun.misc.Signal
import ua.com.radiokot.feed.api.categories.CategoriesJsonApiController
import ua.com.radiokot.feed.api.categories.legacy.LegacyCategoriesApiController
import ua.com.radiokot.feed.api.di.KLoggerKoinLogger
import ua.com.radiokot.feed.api.di.injectionModules
import ua.com.radiokot.feed.api.jsonapi.JsonApiDate
import ua.com.radiokot.feed.api.posts.PostsJsonApiController
import ua.com.radiokot.feed.api.posts.legacy.LegacyPostsApiController
import ua.com.radiokot.feed.api.status.StatusJsonApiController
import ua.com.radiokot.feed.api.util.JavalinResponseStatusLogger
import java.time.Instant

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

        JavalinValidation.register(Instant::class.java, JsonApiDate.javalinValidator)

        Javalin
            .create { config ->
                config.showJavalinBanner = false
                config.requestLogger(JavalinResponseStatusLogger())
            }
            .after { ctx ->
                ctx.header(Header.SERVER, "WFeed")
            }
            .routes {
                path("categories") {
                    val controller = get<LegacyCategoriesApiController>()
                    ApiBuilder.get(controller::getAll)
                }

                path("get") {
                    val controller = get<LegacyPostsApiController>()
                    ApiBuilder.get(controller::get)
                }

                path("v2/") {
                    path("categories") {
                        val controller = get<CategoriesJsonApiController>()
                        ApiBuilder.get(controller::getAll)
                    }

                    path("posts") {
                        val controller = get<PostsJsonApiController>()
                        ApiBuilder.get(controller::get)
                    }

                    ApiBuilder.get(get<StatusJsonApiController>()::get)
                }
            }
            .start(getKoin().getProperty("PORT", "8041").toInt())
            .apply {
                // Gracefully stop on SIGINT and SIGTERM.
                listOf("INT", "TERM").forEach {
                    Signal.handle(Signal(it)) { stop() }
                }
            }
    }
}