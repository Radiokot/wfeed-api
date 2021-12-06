package ua.com.radiokot.feed.api.util

import io.javalin.http.Context
import io.javalin.http.RequestLogger
import mu.KotlinLogging

class JavalinResponseStatusLogger(
    name: String = "API"
) : RequestLogger {
    private val logger = KotlinLogging.logger(name)

    override fun handle(ctx: Context, executionTimeMs: Float) {
        if (executionTimeMs < 1000) {
            logger.info {
                "response: " +
                        "method=${ctx.method()}, " +
                        "uri=${ctx.req.requestURI}, " +
                        "query=${ctx.req.queryString}, " +
                        "status=${ctx.res.status}, " +
                        "timeMs=$executionTimeMs"
            }
        } else {
            logger.warn {
                "slow_response: " +
                        "method=${ctx.method()}, " +
                        "uri=${ctx.req.requestURI}, " +
                        "query=${ctx.req.queryString}, " +
                        "status=${ctx.res.status}, " +
                        "timeMs=$executionTimeMs"
            }
        }
    }
}