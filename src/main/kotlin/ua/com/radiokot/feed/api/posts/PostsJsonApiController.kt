package ua.com.radiokot.feed.api.posts

import com.github.jasminb.jsonapi.JSONAPIDocument
import com.github.jasminb.jsonapi.ResourceConverter
import com.github.jasminb.jsonapi.SerializationSettings
import io.javalin.http.Context
import ua.com.radiokot.feed.api.attachments.model.PhotoAttachmentResource
import ua.com.radiokot.feed.api.authors.model.AuthorResource
import ua.com.radiokot.feed.api.extensions.jsonApi
import ua.com.radiokot.feed.api.posts.model.PostResource
import ua.com.radiokot.feed.attachments.model.FeedAttachment
import ua.com.radiokot.feed.attachments.service.FeedAttachmentsService
import ua.com.radiokot.feed.auhtors.model.FeedAuthor
import ua.com.radiokot.feed.auhtors.service.FeedAuthorsService
import ua.com.radiokot.feed.posts.model.FeedPost
import ua.com.radiokot.feed.posts.service.FeedPostsService
import java.net.HttpURLConnection
import java.time.Instant

class PostsJsonApiController(
    private val feedPostsService: FeedPostsService,
    private val feedAuthorsService: FeedAuthorsService,
    private val feedAttachmentsService: FeedAttachmentsService,
    private val resourceConverter: ResourceConverter,
) {
    fun get(ctx: Context) {
        val fromDate = ctx.queryParam<Instant>(PAGE_CURSOR_KEY)
            .getOrNull()

        val limit = ctx.queryParam<Int>(PAGE_LIMIT_KEY)
            .check({ it in 1..50 }, "Limit must be between 1 and 50")
            .getOrNull()
            ?: 20

        val categories = ctx.queryParam("filter[categories]", "")
            ?.split(',')
            ?.toSet()
            ?.let { it + setOf("0") }
            ?: setOf("0", "1", "2", "3")

        val authorsMap = feedAuthorsService
            .getAuthors(categories)
            .associateBy(FeedAuthor::id)

        val posts = feedPostsService.getPosts(
            authorIds = authorsMap.keys,
            fromDate = fromDate,
            limit = limit
        )

        val attachmentsByPostMap = feedAttachmentsService
            .getAttachments(
                postIds = posts.map(FeedPost::id).toSet()
            )
            .groupBy(FeedAttachment::postId)

        val authorResourceMap = mutableMapOf<String, AuthorResource>()

        val response = JSONAPIDocument(
            posts.map { post ->
                PostResource(
                    post,
                    authorResourceMap
                        .getOrPut(post.authorId) {
                            AuthorResource(authorsMap.getValue(post.authorId))
                        },
                    attachmentsByPostMap
                        .getValue(post.id)
                        .sortedBy(FeedAttachment::i)
                        .map { attachment ->
                            when (attachment) {
                                is FeedAttachment.Photo ->
                                    PhotoAttachmentResource(attachment)
                            }
                        }
                )
            }
        )

        // Include pagination meta if further pagination is possible.
        if (posts.size == limit) {
            response.meta = mapOf(
                "next" to mapOf(
                    PAGE_CURSOR_KEY to posts.last().date
                )
            )
        }

        ctx.apply {
            status(HttpURLConnection.HTTP_OK)
            ctx.jsonApi(
                resourceConverter.writeDocumentCollection(
                    response,
                    SerializationSettings.Builder()
                        .includeRelationship("author")
                        .includeRelationship("attachments")
                        .build()
                )
            )
        }
    }

    private companion object {
        private const val PAGE_LIMIT_KEY = "page[limit]"
        private const val PAGE_CURSOR_KEY = "page[cursor]"
    }
}