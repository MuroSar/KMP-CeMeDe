package com.cemede.cemede.data.data_base.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NestedMapConverter {
    @TypeConverter
    fun fromString(value: String): Map<String, Map<String, Int>> {
        return try {
            // Especificamos el tipo complejo aquí
            Json.decodeFromString<Map<String, Map<String, Int>>>(value)
        } catch (e: Exception) {
            emptyMap()
        }
    }

    @TypeConverter
    fun fromMap(map: Map<String, Map<String, Int>>): String {
        return Json.encodeToString(map)
    }
}
