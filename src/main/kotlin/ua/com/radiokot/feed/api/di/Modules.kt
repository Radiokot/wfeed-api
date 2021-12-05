package ua.com.radiokot.feed.updater.di

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.commons.dbcp2.BasicDataSource
import org.koin.core.module.Module
import org.koin.dsl.module
import ua.com.radiokot.feed.api.categories.legacy.LegacyCategoriesApiController
import ua.com.radiokot.feed.api.extensions.getNotEmptyProperty
import ua.com.radiokot.feed.categories.service.FeedCategoriesService
import ua.com.radiokot.feed.categories.service.RealFeedCategoriesService
import javax.sql.DataSource

val injectionModules: List<Module> = listOf(
    // JSON
    module {
        single<ObjectMapper> {
            jacksonObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        }
    },

    // Services
    module {
        single<FeedCategoriesService> {
            RealFeedCategoriesService(
                dataSource = get()
            )
        }
    },

    // Database
    module {
        single<DataSource> {
            val dbName = getNotEmptyProperty("DB_NAME")
            val dbHost = getNotEmptyProperty("DB_HOST")
            val dbPort = getNotEmptyProperty("DB_PORT")
            val dbUser = getNotEmptyProperty("DB_USER")
            val dbPassword = getNotEmptyProperty("DB_PASSWORD")

            BasicDataSource().apply {
                url = "jdbc:mysql://$dbHost:$dbPort/$dbName" +
                        "?useSSL=false" +
                        "&useUnicode=yes" +
                        "&character_set_server=utf8mb4" +
                        "characterEncoding=UTF-8"
                username = dbUser
                password = dbPassword

                minIdle = 3
                maxIdle = 9
            }
        }
    },

    // API controllers
    module {
        single {
            LegacyCategoriesApiController(
                feedCategoriesService = get()
            )
        }
    },
)