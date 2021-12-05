package ua.com.radiokot.feed.api.categories.legacy

import io.javalin.http.Context
import ua.com.radiokot.feed.api.categories.legacy.model.LegacyCategoriesResponse
import ua.com.radiokot.feed.api.categories.legacy.model.LegacyCategory
import ua.com.radiokot.feed.categories.service.FeedCategoriesService
import java.net.HttpURLConnection

class LegacyCategoriesApiController(
    private val feedCategoriesService: FeedCategoriesService
) {
    fun getAll(ctx: Context) {
        val categories = feedCategoriesService.getCategoriesWithAuthorCount()

        val response = LegacyCategoriesResponse(
            categories = categories.map(::LegacyCategory)
        )

        ctx.apply {
            status(HttpURLConnection.HTTP_OK)
            json(response)
        }
    }
}