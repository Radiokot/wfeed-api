package ua.com.radiokot.feed.sites.model

import java.sql.ResultSet

class FeedSite(
    val id: String,
    val name: String,
) {
    constructor(r: ResultSet) : this(
        id = r.getString("site.id"),
        name = r.getString("site.name")
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FeedSite

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "FeedSite(id='$id', name='$name')"
    }
}