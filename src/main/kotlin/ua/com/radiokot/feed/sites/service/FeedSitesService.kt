package ua.com.radiokot.feed.sites.service

import ua.com.radiokot.feed.sites.model.FeedSite
import java.time.Instant

interface FeedSitesService {
    fun getSites(): List<FeedSite>

    fun getLastPostDates(): Map<FeedSite, Instant>
}