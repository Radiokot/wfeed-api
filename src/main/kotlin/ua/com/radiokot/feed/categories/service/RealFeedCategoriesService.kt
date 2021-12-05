package ua.com.radiokot.feed.categories.service

import mu.KotlinLogging
import ua.com.radiokot.feed.categories.model.FeedCategory
import ua.com.radiokot.feed.categories.model.FeedCategoryWithAuthorCount
import javax.sql.DataSource

class RealFeedCategoriesService(
    private val dataSource: DataSource
) : FeedCategoriesService {
    private val logger = KotlinLogging.logger("RealFeedCategoriesService")

    override fun getCategories(ids: Set<String>?): List<FeedCategory> {
        logger.debug {
            "get_categories: " +
                    "ids=$ids"
        }

        return dataSource.connection.use { connection ->
            val preparedStatement = connection.prepareStatement(
                if (ids != null)
                    "SELECT * FROM `category` " +
                            "WHERE display_order IS NOT NULL" +
                            "AND FIND_IN_SET(`id`, ?) <> 0 " +
                            "ORDER BY display_order"
                else
                    "SELECT * FROM `category` " +
                            "WHERE display_order IS NOT NULL" +
                            "ORDER BY display_order"
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

    override fun getCategoriesWithAuthorCount(ids: Set<String>?): List<FeedCategoryWithAuthorCount> {
        logger.debug {
            "get_categories_with_author_count: " +
                    "ids=$ids"
        }

        return dataSource.connection.use { connection ->
            val preparedStatement = connection.prepareStatement(
                if (ids != null)
                    "SELECT category.id, display_order, " +
                            "description_ru, description_uk, description_en, thumb, " +
                            "COUNT(author.id) as authors_count " +
                            "FROM category, author " +
                            "WHERE author.category_id=category.id " +
                            "AND FIND_IN_SET(category.id, ?) <> 0 " +
                            "AND display_order IS NOT NULL " +
                            "GROUP BY category.id " +
                            "ORDER BY display_order"
                else
                    "SELECT category.id, display_order, " +
                            "description_ru, description_uk, description_en, thumb, " +
                            "COUNT(author.id) as authors_count " +
                            "FROM category, author " +
                            "WHERE author.category_id=category.id " +
                            "AND display_order IS NOT NULL " +
                            "GROUP BY category.id " +
                            "ORDER BY display_order"
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
                            ?.let(::FeedCategoryWithAuthorCount)
                    }.toList().also {
                        logger.debug {
                            "got_categories_with_author_count: " +
                                    "ids=$ids, " +
                                    "categories=${it.size}"
                        }
                    }
                }
            }
        }
    }
}