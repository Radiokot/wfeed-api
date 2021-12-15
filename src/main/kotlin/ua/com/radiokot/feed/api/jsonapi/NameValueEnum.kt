package ua.com.radiokot.feed.api.jsonapi

import com.fasterxml.jackson.annotation.JsonProperty

class NameValueEnum(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("value")
    val value: Int
)