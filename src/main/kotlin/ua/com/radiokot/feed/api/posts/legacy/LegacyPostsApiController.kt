package ua.com.radiokot.feed.api.posts.legacy

import io.javalin.http.Context
import ua.com.radiokot.feed.api.posts.legacy.model.LegacyAttachment
import ua.com.radiokot.feed.api.posts.legacy.model.LegacyAuthor
import ua.com.radiokot.feed.api.posts.legacy.model.LegacyPost
import ua.com.radiokot.feed.api.posts.legacy.model.LegacyPostsResponse
import ua.com.radiokot.feed.attachments.model.FeedAttachment
import ua.com.radiokot.feed.attachments.service.FeedAttachmentsService
import ua.com.radiokot.feed.auhtors.model.FeedAuthor
import ua.com.radiokot.feed.auhtors.service.FeedAuthorsService
import ua.com.radiokot.feed.posts.model.FeedPost
import ua.com.radiokot.feed.posts.service.FeedPostsService
import ua.com.radiokot.feed.sites.model.FeedSite
import ua.com.radiokot.feed.sites.service.FeedSitesService
import java.net.HttpURLConnection
import java.time.Instant

class LegacyPostsApiController(
    private val feedPostsService: FeedPostsService,
    private val feedAuthorsService: FeedAuthorsService,
    private val feedAttachmentsService: FeedAttachmentsService,
    private val feedSitesService: FeedSitesService,
) {
    fun get(ctx: Context) {
        val count = ctx.queryParam<Int>("count", "50")
            .check({ it in (1..50) }, "Count must be between 1 and 50")
            .get()

        val fromDate = ctx.queryParam<Long>("from_date", "0")
            .get()
            .takeIf { it != 0L }
            ?.let(Instant::ofEpochSecond)

        val categories = ctx.queryParam("categories")
            ?.split(',')
            ?.toSet()
            ?.let { it + setOf("0") }
            ?: setOf("0", "1", "2", "3")

        val authorsMap = feedAuthorsService
            .getAuthors(categories)
            .associateBy(FeedAuthor::id)

        val sitesMap = feedSitesService
            .getSites()
            .associateBy(FeedSite::id)

        val posts = feedPostsService.getPosts(
            authorIds = authorsMap.keys,
            fromDate = fromDate,
            limit = count
        )

        val authorsOfPosts = posts
            .map(FeedPost::authorId)
            .distinct()
            .map(authorsMap::getValue)

        val attachments = feedAttachmentsService
            .getAttachments(
                postIds = posts.map(FeedPost::id).toSet()
            )

        val mappedAttachments = attachments
            .mapNotNull { attachment ->
                if (attachment is FeedAttachment.Photo)
                    LegacyAttachment(attachment)
                else
                    null
            }

        val response = LegacyPostsResponse(
            posts = posts.map(::LegacyPost),
            authors = authorsOfPosts.map { author ->
                val site = sitesMap.getValue(author.siteId)
                LegacyAuthor(author, site)
            },
            atts = mappedAttachments,
            info = LegacyPostsResponse.Info(
                authorsCount = authorsOfPosts.size,
                postsCount = posts.size,
                attsCount = mappedAttachments.size,
                categories = categories.joinToString(","),
                time = 228f
            )
        )

        response.posts.forEach { post ->
            if (sitesMap[authorsMap[post.authorId]?.siteId]?.name?.startsWith("VK") == true) {
                post.text = ""
            }
        }

        ctx.apply {
            status(HttpURLConnection.HTTP_OK)
            json(response)
        }
    }
}