package ua.com.radiokot.feed.api.categories

import com.github.jasminb.jsonapi.JSONAPIDocument
import com.github.jasminb.jsonapi.ResourceConverter
import io.javalin.http.Context
import ua.com.radiokot.feed.api.categories.model.CategoryResource
import ua.com.radiokot.feed.api.extensions.jsonApi
import ua.com.radiokot.feed.categories.service.FeedCategoriesService
import java.net.HttpURLConnection

class CategoriesJsonApiController(
    private val feedCategoriesService: FeedCategoriesService,
    private val resourceConverter: ResourceConverter
) {
    fun getAll(ctx: Context) {
        val categories = feedCategoriesService.getCategoriesWithAuthorCount()

        val response = JSONAPIDocument(categories.map(::CategoryResource))

        ctx.apply {
            status(HttpURLConnection.HTTP_OK)
            jsonApi(resourceConverter.writeDocumentCollection(response))
        }
    }
}