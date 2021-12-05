package ua.com.radiokot.feed.categories.model

import java.sql.ResultSet

data class FeedCategoryWithAuthorCount(
    val category: FeedCategory,
    val authorCount: Int,
) {
    constructor(r: ResultSet): this(
        category = FeedCategory(r),
        authorCount = r.getInt("authors_count")
    )
}