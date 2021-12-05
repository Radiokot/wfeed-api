package ua.com.radiokot.feed.categories.service

import mu.KotlinLogging
import ua.com.radiokot.feed.categories.model.FeedCategory
import javax.sql.DataSource

class RealFeedCategoriesService(
    private val dataSource: DataSource
) : FeedCategoriesService {
    private val logger = KotlinLogging.logger("RealFeedCategoriesService")

    override fun getCategories(ids: Set<String>?): Collection<FeedCategory> {
        logger.debug {
            "get_categories: " +
                    "ids=$ids"
        }

        return dataSource.connection.use { connection ->
            val preparedStatement = connection.prepareStatement(
                if (ids != null)
                    "SELECT * FROM `category` WHERE FIND_IN_SET(`id`, ?) <> 0;"
                else
                    "SELECT * FROM `category`"
            ).apply {
                if (ids != null) {
                    setString(1, ids.joinToString(","))
                }
            }

            preparedStatement.use { statement ->
                statement.executeQuery().use { resultSet ->
                    generateSequence {
                        resultSet
                            .takeIf { it.next() }
                            ?.let(::FeedCategory)
                    }.toList().also {
                        logger.debug {
                            "got_categories: " +
                                    "ids=$ids, " +
                                    "categories=${it.size}"
                        }
                    }
                }
            }
        }
    }
}