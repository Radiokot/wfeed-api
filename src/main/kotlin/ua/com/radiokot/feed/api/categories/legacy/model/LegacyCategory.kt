package ua.com.radiokot.feed.api.categories.legacy.model

import com.fasterxml.jackson.annotation.JsonProperty
import ua.com.radiokot.feed.categories.model.FeedCategoryWithAuthorCount

class LegacyCategory(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("descriptionRu")
    val descriptionRu: String,
    @JsonProperty("descriptionUk")
    val descriptionUk: String,
    @JsonProperty("descriptionEn")
    val descriptionEn: String,
    @JsonProperty("thumb")
    val thumb: String,
    @JsonProperty("authorsCount")
    val authorsCount: Int
) {
    constructor(c: FeedCategoryWithAuthorCount): this(
        id = c.category.id,
        descriptionRu = c.category.descriptionRu,
        descriptionUk = c.category.descriptionUk,
        descriptionEn = c.category.descriptionEn,
        thumb = c.category.thumbUrl,
        authorsCount = c.authorCount
    )
}