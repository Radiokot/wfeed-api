package ua.com.radiokot.feed.api.extensions

import io.javalin.http.Context
import ua.com.radiokot.feed.api.jsonapi.JSON_API_CONTENT_TYPE

fun Context.jsonApi(response: ByteArray) =
    contentType(JSON_API_CONTENT_TYPE).result(response)