package ua.com.radiokot.feed.api

import mu.KotlinLogging
import org.koin.core.context.startKoin
import ua.com.radiokot.feed.updater.di.KLoggerKoinLogger
import ua.com.radiokot.feed.updater.di.injectionModules

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        startKoin {
            logger(KLoggerKoinLogger(KotlinLogging.logger("Koin")))

            fileProperties("/database.properties")
            environmentProperties()

            modules(injectionModules)
        }
    }
}