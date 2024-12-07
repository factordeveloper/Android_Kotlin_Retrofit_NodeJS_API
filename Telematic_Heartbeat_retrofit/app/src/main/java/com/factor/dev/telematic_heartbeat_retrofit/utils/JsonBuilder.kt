package com.factor.dev.telematic_heartbeat_retrofit.utils

import okhttp3.RequestBody
import org.json.JSONObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray


fun buildTelematicsJson(): RequestBody {
    // Construcción del JSON
    val relativePosition = JSONObject().apply {
        put("distance", "4")
        put("unit_of_measure", "mi")
        put("direction", "ENE")
        put("city", "La Jolla")
        put("state_code", "CA")
        put("country_code", "US")
    }

    val location = JSONObject().apply {
        put("latitude", 32.875)
        put("longitude", -117.206869)
        put("description", "4 mi ENE of La Jolla, CA")
        put("country_code", "US")
        put("state_code", "CA")
        put("relative_position", relativePosition)
    }

    val gps = JSONObject().apply {
        put("distance_diff", 4.86)
        put("total_distance", 518.53)
    }

    val idlePeriod = JSONArray().apply {
        put(JSONObject().apply {
            put("duration", "20.73")
            put("latitude", 32.935)
            put("longitude", -117.206879)
            put("start_time", "2021-01-03T22:12:27.000Z")
        })
    }

    val attributes = JSONObject().apply {
        put("event", "periodic_update")
        put("logged_at", "2021-01-03T23:32:27.000Z")
        put("heartbeat_id", "920144768000507")
        put("speed", 24.5)
        put("odometer", 328310.93)
        put("rpm", 600)
        put("engine_hours", 10399.35)
        put("wheels_in_motion", true)
        put("gps", gps)
        put("location", location)
        put("idle_periods", idlePeriod)
    }

    val data = JSONObject().apply {
        put("type", "telematics_heartbeat")
        put("id", "920144768000507")
        put("attributes", attributes)
    }

    val meta = JSONObject().apply {
        put("message_id", "014bf7b0-f4f9-11e8-baee-cddefa7c4011")
        put("consumer_version", "1.1.0")
        put("origin_version", "beta-1.0.0")
        put("timestamp", "2021-01-03T23:32:27.000Z")
    }

    val json = JSONObject().apply {
        put("data", data)
        put("meta", meta)
    }

    // Convertir el JSON a RequestBody
    return json.toString().toRequestBody("application/json".toMediaType())
}
