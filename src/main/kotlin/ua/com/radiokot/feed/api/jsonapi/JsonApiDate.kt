package ua.com.radiokot.feed.api.jsonapi

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import java.time.Instant

object JsonApiDate {
    private val instantSerializer = object : JsonSerializer<Instant>() {
        override fun serialize(
            value: Instant?,
            gen: JsonGenerator,
            serializers: SerializerProvider
        ) {
            if (value != null) {
                gen.writeString(value.toString())
            } else {
                gen.writeNull()
            }
        }
    }

    val jacksonModule = SimpleModule()
        .apply {
            addSerializer(Instant::class.java, instantSerializer)
        }

    val javalinValidator = { value: String ->
        Instant.parse(value)
    }
}