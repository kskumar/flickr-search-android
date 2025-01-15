package com.example.flickrsearch.model

import com.google.gson.annotations.SerializedName

data class ImageSearchResponse(
    @SerializedName("title") val title: String,
    @SerializedName("link") val resultListUrl: String,
    @SerializedName("description") val description: String,
    @SerializedName("modified") val modifiedTimestamp: String,
    @SerializedName("generator") val generator: String,
    @SerializedName("items") val resultList: List<FlickrImage>,
)
