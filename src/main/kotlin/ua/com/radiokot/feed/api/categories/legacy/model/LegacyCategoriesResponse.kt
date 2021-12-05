package ua.com.radiokot.feed.api.categories.legacy.model

import com.fasterxml.jackson.annotation.JsonProperty

class LegacyCategoriesResponse(
    @JsonProperty("categories")
    val categories: List<LegacyCategory>
)