package ua.com.radiokot.feed.posts.service

import ua.com.radiokot.feed.posts.model.FeedPost
import java.util.*

interface FeedPostsService {
    /**
     * @return posts from given authors in descending order
     */
    fun getPosts(
        authorIds: Set<String>,
        fromDate: Date?,
        limit: Int,
    ): List<FeedPost>
}