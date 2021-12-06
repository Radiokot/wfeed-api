package ua.com.radiokot.feed.posts.service

import mu.KotlinLogging
import ua.com.radiokot.feed.auhtors.model.FeedSite
import ua.com.radiokot.feed.posts.model.FeedPost
import java.sql.Timestamp
import java.time.Instant
import javax.sql.DataSource

class RealFeedPostsService(
    private val dataSource: DataSource
) : FeedPostsService {
    private val logger = KotlinLogging.logger("RealFeedPostsService")

    override fun getPosts(
        authorIds: Set<String>,
        fromDate: Instant?,
        limit: Int
    ): List<FeedPost> {
        logger.debug {
            "get_posts: " +
                    "authorIds=$authorIds,\n" +
                    "fromDate=$fromDate, " +
                    "limit=$limit"
        }

        return dataSource.connection.use { connection ->
            val preparedStatement = connection.prepareStatement(
                "SELECT * FROM post " +
                        "WHERE ${if (fromDate != null) "date < ?" else "1"} " +
                        "AND FIND_IN_SET(author_id, ?) <> 0 " +
                        "ORDER BY date DESC " +
                        "LIMIT 0, ?"
            )
                .apply {
                    var i = 0
                    if (fromDate != null) {
                        setTimestamp(++i, Timestamp.from(fromDate))
                    }
                    setString(++i, authorIds.joinToString(","))
                    setInt(++i, limit)
                }

            preparedStatement.use { statement ->
                statement.executeQuery().use { resultSet ->
                    generateSequence {
                        resultSet
                            .takeIf { it.next() }
                            ?.let(::FeedPost)
                    }.toList().also {
                        logger.debug {
                            "got_posts: " +
                                    "authorIds=$authorIds,\n" +
                                    "fromDate=$fromDate, " +
                                    "limit=$limit, " +
                                    "posts=${it.size}"
                        }
                    }
                }
            }
        }
    }

    override fun getLastPostDate(site: FeedSite): Instant? {
        logger.debug {
            "get_last_post_date: " +
                    "site=$site"
        }

        return dataSource.connection.use { connection ->
            val preparedStatement = connection.prepareStatement(
                "SELECT post.date FROM post, author " +
                        "WHERE post.author_id=author.id " +
                        "AND author.site_id=? " +
                        "ORDER BY post.date " +
                        "DESC LIMIT 1"
            ).apply {
                setInt(1, site.i)
            }

            preparedStatement.use { statement ->
                statement.executeQuery().use { resultSet ->
                    resultSet
                        .takeIf { it.next() }
                        ?.getTimestamp(1)?.toInstant()
                        .also {
                            logger.debug {
                                "got_last_post_date: " +
                                        "site=$site, " +
                                        "date=$it"
                            }
                        }
                }
            }
        }
    }
}