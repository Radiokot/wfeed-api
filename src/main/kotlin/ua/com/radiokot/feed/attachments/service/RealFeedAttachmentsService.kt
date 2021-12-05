package ua.com.radiokot.feed.attachments.service

import mu.KotlinLogging
import ua.com.radiokot.feed.attachments.model.FeedAttachment
import javax.sql.DataSource

class RealFeedAttachmentsService(
    private val dataSource: DataSource
): FeedAttachmentsService {
    private val logger = KotlinLogging.logger("RealFeedPostsService")

    override fun getAttachments(postIds: Set<String>): List<FeedAttachment> {
        logger.debug {
            "get_attachments: " +
                    "postIds=$postIds"
        }

        return dataSource.connection.use { connection ->
            val preparedStatement = connection.prepareStatement(
                "SELECT * FROM att " +
                        "WHERE FIND_IN_SET(post_id, ?) <> 0"
            ).apply {
                setString(1, postIds.joinToString(","))
            }

            preparedStatement.use { statement ->
                statement.executeQuery().use { resultSet ->
                    generateSequence {
                        resultSet
                            .takeIf { it.next() }
                            ?.let(FeedAttachment::create)
                    }.toList().also {
                        logger.debug {
                            "got_attachments: " +
                                    "postIds=$postIds,\n" +
                                    "attachments=${it.size}"
                        }
                    }
                }
            }
        }
    }
}