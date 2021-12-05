package ua.com.radiokot.feed.api

import mu.KotlinLogging
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import ua.com.radiokot.feed.categories.service.FeedCategoriesService
import ua.com.radiokot.feed.updater.di.KLoggerKoinLogger
import ua.com.radiokot.feed.updater.di.injectionModules

@KoinApiExtension
object Application: KoinComponent {

    @JvmStatic
    fun main(args: Array<String>) {
        startKoin {
            logger(KLoggerKoinLogger(KotlinLogging.logger("Koin")))

            fileProperties("/database.properties")
            environmentProperties()

            modules(injectionModules)
        }

        get<FeedCategoriesService>()
            .getCategories()
            .forEach { println(it) }
    }
}