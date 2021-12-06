package ua.com.radiokot.feed.api.posts.legacy.model

import com.fasterxml.jackson.annotation.JsonProperty
import ua.com.radiokot.feed.attachments.model.FeedAttachment

class LegacyAttachment(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("i")
    val i: Int,
    @JsonProperty("type")
    val type: String,
    @JsonProperty("VkAttId")
    val vkAttId: String?,
    @JsonProperty("photoWidth")
    val photoWidth: Int,
    @JsonProperty("photoHeight")
    val photoHeight: Int,
    @JsonProperty("photo130")
    val photo130: String,
    @JsonProperty("photo604")
    val photo604: String,
    @JsonProperty("photo807")
    val photo807: String,
    @JsonProperty("photo1280")
    val photo1280: String,
    @JsonProperty("photo2560")
    val photo2560: String,
) {
    constructor(a: FeedAttachment.Photo): this(
        id = a.postId, // Important
        i = a.i,
        type = FeedAttachment.Photo.TYPE,
        vkAttId = a.apiId,
        photoWidth = a.width,
        photoHeight = a.height,
        photo130 = a.url130 ?: "0",
        photo604 = a.url604 ?: "0",
        photo807 = a.url807 ?: "0",
        photo1280 = a.url1280 ?: "0",
        photo2560 = a.url2560 ?: "0"
    )
}