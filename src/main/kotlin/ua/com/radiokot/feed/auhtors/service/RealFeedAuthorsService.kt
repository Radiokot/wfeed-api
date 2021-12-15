package ua.com.radiokot.feed.auhtors.service

import mu.KotlinLogging
import ua.com.radiokot.feed.auhtors.model.FeedAuthor
import javax.sql.DataSource

class RealFeedAuthorsService(
    private val dataSource: DataSource
) : FeedAuthorsService {
    private val logger = KotlinLogging.logger("RealFeedAuthorsService")

    override fun getAuthors(categoryIds: Set<String>): List<FeedAuthor> {
        logger.debug {
            "get_authors: " +
                    "categoryIds=$categoryIds"
        }

        dataSource.connection.use { connection ->
            val preparedStatement = connection
                .prepareStatement(
                    "SELECT id AS 'author.id', api_id AS 'author.api_id', " +
                            "site_id AS 'author.site_id', category_id AS 'author.category_id', " +
                            "name AS 'author.name', photo AS 'author.photo' " +
                            "FROM author WHERE FIND_IN_SET(author.category_id, ?) <> 0"
                ).apply {
                    setString(1, categoryIds.joinToString(","))
                }

            return preparedStatement.use { statement ->
                statement.executeQuery().use { resultSet ->
                    generateSequence {
                        resultSet
                            .takeIf { it.next() }
                            ?.let(::FeedAuthor)
                    }.toList().also {
                        logger.debug {
                            "got_authors: " +
                                    "categoryIds=$categoryIds, " +
                                    "authors=${it.size}"
                        }
                    }
                }
            }
        }
    }
}