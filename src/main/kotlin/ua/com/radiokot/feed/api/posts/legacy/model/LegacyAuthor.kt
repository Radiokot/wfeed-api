package ua.com.radiokot.feed.api.posts.legacy.model

import com.fasterxml.jackson.annotation.JsonProperty
import ua.com.radiokot.feed.auhtors.model.FeedAuthor
import ua.com.radiokot.feed.sites.model.FeedSite

class LegacyAuthor(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("apiId")
    val apiId: String,
    @JsonProperty("authorName")
    val authorName: String,
    @JsonProperty("authorPhoto")
    val authorPhoto: String,
    @JsonProperty("siteId")
    val siteId: String,
    @JsonProperty("categoryId")
    val categoryId: String,
    @JsonProperty("siteName")
    val siteName: String,
    @JsonProperty("siteUrl")
    val siteUrl: String
) {
    constructor(
        a: FeedAuthor,
        s: FeedSite
    ) : this(
        id = a.id,
        apiId = a.apiId,
        authorName = a.name,
        authorPhoto = a.photoUrl,
        siteId = s.id,
        categoryId = a.categoryId,
        siteName = s.name,
        siteUrl = "https://feed.radiokot.com.ua"
    )
}