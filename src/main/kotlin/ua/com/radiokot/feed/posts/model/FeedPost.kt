package ua.com.radiokot.feed.posts.model

import java.sql.ResultSet
import java.time.Instant

data class FeedPost(
    val id: String,
    val apiId: String,
    val date: Instant,
    val authorId: String,
    val text: String?,
    val url: String
) {
    constructor(r: ResultSet) : this(
        id = r.getString("id"),
        apiId = r.getString("api_id"),
        date = r.getTimestamp("date").toInstant(),
        authorId = r.getString("author_id"),
        text = r.getString("text")?.takeIf(String::isNotEmpty),
        url = r.getString("url")
    )
}