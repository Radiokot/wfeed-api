package ua.com.radiokot.feed.api

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.apibuilder.ApiBuilder.path
import mu.KotlinLogging
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import ua.com.radiokot.feed.api.categories.legacy.LegacyCategoriesApiController
import ua.com.radiokot.feed.updater.di.KLoggerKoinLogger
import ua.com.radiokot.feed.updater.di.injectionModules

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

        Javalin
            .create { config ->
                config.showJavalinBanner = false
            }
            .routes {
                path("categories") {
                    val controller = get<LegacyCategoriesApiController>()

                    ApiBuilder.get("/", controller::getAll)
                }
            }
            .start(getKoin().getProperty("PORT", 8041))
    }
}