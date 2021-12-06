package ua.com.radiokot.feed.api.posts.legacy.model

import com.fasterxml.jackson.annotation.JsonProperty

class LegacyPostsResponse(
    @JsonProperty("info")
    val info: Info,
    @JsonProperty("authors")
    val authors: List<LegacyAuthor>,
    @JsonProperty("posts")
    val posts: List<LegacyPost>,
    @JsonProperty("atts")
    val atts: List<LegacyAttachment>
) {
    class Info(
        @JsonProperty("authorsCount")
        val authorsCount: Int,
        @JsonProperty("postsCount")
        val postsCount: Int,
        @JsonProperty("attsCount")
        val attsCount: Int,
        @JsonProperty("time")
        val time: Float,
        @JsonProperty("categories")
        val categories: String
    )
}