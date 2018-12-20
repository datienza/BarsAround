package com.tide.barsaround.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "name") val name: String,
    @Json(name = "geometry") val geometry: Geometry
)
