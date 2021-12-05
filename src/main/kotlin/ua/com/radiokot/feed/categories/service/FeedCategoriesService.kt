package ua.com.radiokot.feed.categories.service

import ua.com.radiokot.feed.categories.model.FeedCategory
import ua.com.radiokot.feed.categories.model.FeedCategoryWithAuthorCount

interface FeedCategoriesService {
    /**
     * @return list of categories ordered by display order.
     */
    fun getCategories(ids: Set<String>? = null): List<FeedCategory>

    /**
     * @return list of categories with author count ordered by display order.
     */
    fun getCategoriesWithAuthorCount(ids: Set<String>? = null): List<FeedCategoryWithAuthorCount>
}