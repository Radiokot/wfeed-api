package ua.com.radiokot.feed.sites.service

import mu.KotlinLogging
import ua.com.radiokot.feed.sites.model.FeedSite
import java.time.Instant
import javax.sql.DataSource

class RealFeedSitesService(
    private val dataSource: DataSource
) : FeedSitesService {
    private val logger = KotlinLogging.logger("RealFeedSitesService")

    override fun getSites(): List<FeedSite> {
        logger.debug { "get_sites" }

        return dataSource.connection.use { connection ->
            val preparedStatement = connection
                .prepareStatement("SELECT site.id, site.name FROM site")

            preparedStatement.use { statement ->
                statement.executeQuery().use { resultSet ->
                    generateSequence {
                        resultSet
                            .takeIf { it.next() }
                            ?.let(::FeedSite)
                    }.toList().also {
                        logger.debug {
                            "got_sites: " +
                                    "sites=${it.size}"
                        }
                    }
                }
            }
        }
    }

    override fun getLastPostDates(): Map<FeedSite, Instant> {
        logger.debug { "get_last_post_dates" }

        return dataSource.connection.use { connection ->
            val preparedStatement = connection.prepareStatement(
                "SELECT site.id AS 'site.id', site.name AS 'site.name', MAX(post.date) AS 'site.last_post_date' " +
                        "FROM post, site, author " +
                        "WHERE post.author_id = author.id " +
                        "AND author.site_id = site.id " +
                        "GROUP BY site.id, site.name"
            )

            preparedStatement.use { statement ->
                statement.executeQuery().use { resultSet ->
                    generateSequence {
                        resultSet
                            .takeIf { it.next() }
                            ?.let {
                                FeedSite(it) to it.getTimestamp("site.last_post_date").toInstant()
                            }
                    }.toMap().also {
                        logger.debug {
                            "got_last_post_dates: " +
                                    "dates: $it"
                        }
                    }
                }
            }
        }
    }
}