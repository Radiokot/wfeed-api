package ua.com.radiokot.feed.categories.model

import java.sql.ResultSet

data class FeedCategory(
    val id: String,
    val displayOrder: Int?,
    val descriptionRu: String,
    val descriptionUk: String,
    val descriptionEn: String,
    val thumbUrl: String,
) {
    constructor(resultSet: ResultSet) : this(
        id = resultSet.getString("id"),
        displayOrder = resultSet.getInt("display_order"),
        descriptionRu = resultSet.getString("description_ru"),
        descriptionUk = resultSet.getString("description_uk"),
        descriptionEn = resultSet.getString("description_en"),
        thumbUrl = resultSet.getString("thumb")
    )
}