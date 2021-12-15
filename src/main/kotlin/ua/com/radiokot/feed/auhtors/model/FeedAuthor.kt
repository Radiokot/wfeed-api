package ua.com.radiokot.feed.auhtors.model

import java.sql.ResultSet

data class FeedAuthor(
    val id: String,
    val apiId: String,
    val categoryId: String,
    val siteId: String,
    val name: String,
    val photoUrl: String,
) {
    constructor(r: ResultSet) : this(
        id = r.getString("author.id"),
        categoryId = r.getString("author.category_id"),
        siteId = r.getString("author.site_id"),
        apiId = r.getString("author.api_id"),
        name = r.getString("author.name"),
        photoUrl = r.getString("author.photo"),
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FeedAuthor

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "FeedAuthor(id='$id', name='$name')"
    }
}