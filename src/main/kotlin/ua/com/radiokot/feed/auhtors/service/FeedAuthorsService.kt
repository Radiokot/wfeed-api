package ua.com.radiokot.feed.auhtors.service

import ua.com.radiokot.feed.auhtors.model.FeedAuthor

interface FeedAuthorsService {
    fun getAuthors(categoryIds: Set<String>): List<FeedAuthor>
}