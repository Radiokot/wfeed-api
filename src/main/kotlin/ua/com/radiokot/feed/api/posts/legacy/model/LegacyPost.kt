package ua.com.radiokot.feed.api.posts.legacy.model

import com.fasterxml.jackson.annotation.JsonProperty
import ua.com.radiokot.feed.posts.model.FeedPost

class LegacyPost(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("apiId")
    val apiId: String,
    @JsonProperty("authorId")
    val authorId: String,
    @JsonProperty("text")
    val text: String,
    @JsonProperty("date")
    val date: Long,
    @JsonProperty("url")
    val url: String
) {
    constructor(p: FeedPost) : this(
        id = p.id,
        apiId = p.apiId,
        authorId = p.authorId,
        text = p.text ?: "",
        date = p.date.epochSecond,
        url = p.url
    )
}