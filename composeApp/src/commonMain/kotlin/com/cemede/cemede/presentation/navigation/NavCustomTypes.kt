package com.cemede.cemede.presentation.navigation

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import com.cemede.cemede.domain.model.Partner
import kotlinx.serialization.json.Json

object NavCustomTypes {
    val PartnerType =
        object : NavType<Partner>(isNullableAllowed = false) {
            override fun get(
                bundle: SavedState,
                key: String,
            ): Partner? = bundle.read { getString(key) }?.let { Json.decodeFromString(it) }

            override fun parseValue(value: String): Partner = Json.decodeFromString(value)

            override fun put(
                bundle: SavedState,
                key: String,
                value: Partner,
            ) {
                bundle.write { putString(key, Json.encodeToString(value)) }
            }

            override fun serializeAsValue(value: Partner): String = Json.encodeToString(value)
        }
}
