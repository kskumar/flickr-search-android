package com.example.flickrsearch.model

import com.google.gson.annotations.SerializedName

data class FlickrMedia(
    @SerializedName("m") val mediumImageUrl: String,
)
