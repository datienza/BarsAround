package com.tide.barsaround.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "lat") val lat: String,
    @Json(name = "long") val long: String
)
