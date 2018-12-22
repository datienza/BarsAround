package com.tide.barsaround.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NearByResponse(
    @Json(name = "results") val results: List<Result>? = null
)
