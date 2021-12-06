package ua.com.radiokot.feed.api.di

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.jasminb.jsonapi.ResourceConverter
import org.apache.commons.dbcp2.BasicDataSource
import org.koin.core.module.Module
import org.koin.dsl.module
import ua.com.radiokot.feed.api.attachments.model.AttachmentResource
import ua.com.radiokot.feed.api.attachments.model.PhotoAttachmentResource
import ua.com.radiokot.feed.api.authors.model.AuthorResource
import ua.com.radiokot.feed.api.categories.CategoriesJsonApiController
import ua.com.radiokot.feed.api.categories.legacy.LegacyCategoriesApiController
import ua.com.radiokot.feed.api.categories.model.CategoryResource
import ua.com.radiokot.feed.api.extensions.getNotEmptyProperty
import ua.com.radiokot.feed.api.jsonapi.JsonApiDate
import ua.com.radiokot.feed.api.posts.PostsJsonApiController
import ua.com.radiokot.feed.api.posts.legacy.LegacyPostsApiController
import ua.com.radiokot.feed.api.posts.model.PostResource
import ua.com.radiokot.feed.api.status.StatusJsonApiController
import ua.com.radiokot.feed.api.status.model.StatusResource
import ua.com.radiokot.feed.attachments.service.FeedAttachmentsService
import ua.com.radiokot.feed.attachments.service.RealFeedAttachmentsService
import ua.com.radiokot.feed.auhtors.service.FeedAuthorsService
import ua.com.radiokot.feed.auhtors.service.RealFeedAuthorsService
import ua.com.radiokot.feed.categories.service.FeedCategoriesService
import ua.com.radiokot.feed.categories.service.RealFeedCategoriesService
import ua.com.radiokot.feed.posts.service.FeedPostsService
import ua.com.radiokot.feed.posts.service.RealFeedPostsService
import java.util.*
import javax.sql.DataSource

val injectionModules: List<Module> = listOf(
    // JSON
    module {
        single<ObjectMapper> {
            jacksonObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .registerModule(SimpleModule().apply {
                    addSerializer(Date::class.java, JsonApiDate.serializer)
                })
        }
    },

    // Services
    module {
        single<FeedCategoriesService> {
            RealFeedCategoriesService(
                dataSource = get()
            )
        }

        single<FeedAuthorsService> {
            RealFeedAuthorsService(
                dataSource = get()
            )
        }

        single<FeedPostsService> {
            RealFeedPostsService(
                dataSource = get()
            )
        }

        single<FeedAttachmentsService> {
            RealFeedAttachmentsService(
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

    // JSONAPI
    module {
        single {
            ResourceConverter(
                get<ObjectMapper>(),
                CategoryResource::class.java,
                PostResource::class.java,
                AuthorResource::class.java,
                AttachmentResource::class.java,
                PhotoAttachmentResource::class.java,
                StatusResource::class.java
            )
        }
    },

    // API controllers
    module {
        // Legacy categories
        single {
            LegacyCategoriesApiController(
                feedCategoriesService = get()
            )
        }

        // Categories
        single {
            CategoriesJsonApiController(
                feedCategoriesService = get(),
                resourceConverter = get()
            )
        }

        // Legacy posts
        single {
            LegacyPostsApiController(
                feedAuthorsService = get(),
                feedPostsService = get(),
                feedAttachmentsService = get()
            )
        }

        // Posts
        single {
            PostsJsonApiController(
                feedAuthorsService = get(),
                feedPostsService = get(),
                feedAttachmentsService = get(),
                resourceConverter = get()
            )
        }

        // Status
        single {
            StatusJsonApiController(
                feedPostsService = get(),
                resourceConverter = get()
            )
        }
    },
)