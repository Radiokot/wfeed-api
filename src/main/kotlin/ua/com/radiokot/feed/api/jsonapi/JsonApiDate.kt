package ua.com.radiokot.feed.api.jsonapi

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.text.SimpleDateFormat
import java.util.*

object JsonApiDate {
    private val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        .apply { timeZone = TimeZone.getTimeZone("UTC") }

    val serializer = object : JsonSerializer<Date>() {
        override fun serialize(
            value: Date?,
            gen: JsonGenerator,
            serializers: SerializerProvider
        ) {
            if (value == null) {
                gen.writeNull()
            } else {
                synchronized(this@JsonApiDate) {
                    gen.writeString(format.format(value))
                }
            }
        }
    }

    val fromStringConverter = { value: String ->
        synchronized(this@JsonApiDate) {
            format.parse(value)
        }
    }
}