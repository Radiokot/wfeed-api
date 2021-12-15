package ua.com.radiokot.feed.api.status

import com.github.jasminb.jsonapi.JSONAPIDocument
import com.github.jasminb.jsonapi.ResourceConverter
import com.github.jasminb.jsonapi.SerializationSettings
import io.javalin.http.Context
import ua.com.radiokot.feed.api.extensions.jsonApi
import ua.com.radiokot.feed.api.status.model.StatusResource
import ua.com.radiokot.feed.sites.service.FeedSitesService
import java.net.HttpURLConnection

class StatusJsonApiController(
    private val feedSitesService: FeedSitesService,
    private val resourceConverter: ResourceConverter,
) {
    fun get(ctx: Context) {
        val lastPostDateMap = feedSitesService
            .getLastPostDates()

        val response = JSONAPIDocument(
            StatusResource(lastPostDateMap)
        )

        ctx.apply {
            status(HttpURLConnection.HTTP_OK)
            jsonApi(
                resourceConverter.writeDocument(
                    response,
                    SerializationSettings.Builder()
                        .includeRelationship("last_post_dates")
                        .includeRelationship("site")
                        .build()
                )
            )
        }
    }
}