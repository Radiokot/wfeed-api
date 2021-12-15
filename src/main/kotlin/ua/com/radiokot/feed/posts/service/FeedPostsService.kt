package ua.com.radiokot.feed.posts.service

import ua.com.radiokot.feed.posts.model.FeedPost
import java.time.Instant

interface FeedPostsService {
    /**
     * @return posts from given authors in descending order
     */
    fun getPosts(
        authorIds: Set<String>,
        fromDate: Instant?,
        limit: Int,
    ): List<FeedPost>
}