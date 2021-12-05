package ua.com.radiokot.feed.api.status

import com.github.jasminb.jsonapi.JSONAPIDocument
import com.github.jasminb.jsonapi.ResourceConverter
import io.javalin.http.Context
import ua.com.radiokot.feed.api.extensions.jsonApi
import ua.com.radiokot.feed.api.status.model.StatusResource
import ua.com.radiokot.feed.auhtors.model.FeedSite
import ua.com.radiokot.feed.posts.service.FeedPostsService
import java.net.HttpURLConnection

class StatusJsonApiController(
    private val feedPostsService: FeedPostsService,
    private val resourceConverter: ResourceConverter,
) {
    fun get(ctx: Context) {
        val lastPostDateMap = FeedSite.values()
            .associateWith(feedPostsService::getLastPostDate)

        val response = JSONAPIDocument(
            StatusResource(lastPostDateMap)
        )

        ctx.apply {
            status(HttpURLConnection.HTTP_OK)
            jsonApi(resourceConverter.writeDocument(response))
        }
    }
}