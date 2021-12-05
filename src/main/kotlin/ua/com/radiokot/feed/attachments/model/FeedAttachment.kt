package ua.com.radiokot.feed.attachments.model

import java.sql.ResultSet

sealed class FeedAttachment(
    val id: String,
    val postId: String,
    val apiId: String?,
    val i: Int,
) {
    class Photo(
        id: String,
        postId: String,
        apiId: String?,
        i: Int,
        val width: Int,
        val height: Int,
        val url130: String?,
        val url604: String?,
        val url807: String?,
        val url1280: String?,
        val url2560: String?,
    ) : FeedAttachment(id, postId, apiId, i) {

        constructor(r: ResultSet) : this(
            id = r.getString("id"),
            postId = r.getString("post_id"),
            apiId = r.getString("api_id"),
            i = r.getInt("i"),
            width = r.getInt("photo_width"),
            height = r.getInt("photo_height"),
            url130 = r.getString("photo_130"),
            url604 = r.getString("photo_604"),
            url807 = r.getString("photo_807"),
            url1280 = r.getString("photo_1280"),
            url2560 = r.getString("photo_2560"),
        )

        companion object {
            const val TYPE = "photo"
        }
    }

    companion object {
        fun create(r: ResultSet): FeedAttachment {
            return when (val type = r.getString("type")) {
                Photo.TYPE -> Photo(r)
                else -> throw IllegalStateException("Unknown attachment type $type")
            }
        }
    }
}