package ua.com.radiokot.feed.attachments.service

import ua.com.radiokot.feed.attachments.model.FeedAttachment

interface FeedAttachmentsService {
    /**
     * @return attachments for given posts.
     */
    fun getAttachments(postIds: Set<String>): List<FeedAttachment>
}