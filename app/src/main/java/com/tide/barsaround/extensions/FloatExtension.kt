package com.tide.barsaround.extensions

fun Int.readableDistanceFromMeters(): String {
    return if (this < 1000) {
        "${this}m"
    } else {
        "${this / 1000}km"
    }
}
