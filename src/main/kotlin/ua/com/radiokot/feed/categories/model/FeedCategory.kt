package ua.com.radiokot.feed.categories.model

import java.sql.ResultSet

data class FeedCategory(
    val id: String,
    val descriptionRu: String,
    val descriptionUk: String,
    val descriptionEn: String,
    val thumbUrl: String,
) {
    constructor(r: ResultSet) : this(
        id = r.getString("id"),
        descriptionRu = r.getString("description_ru"),
        descriptionUk = r.getString("description_uk"),
        descriptionEn = r.getString("description_en"),
        thumbUrl = r.getString("thumb")
    )
}