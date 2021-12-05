package ua.com.radiokot.feed.categories.service

import ua.com.radiokot.feed.categories.model.FeedCategory

interface FeedCategoriesService {
    fun getCategories(ids: Set<String>? = null): Collection<FeedCategory>
}