package com.example.flickrsearch.model

import com.google.gson.annotations.SerializedName

data class FlickrImage(
    @SerializedName("title") val title: String,
    @SerializedName("link") val link: String,
    @SerializedName("media") val media: FlickrMedia,
    @SerializedName("date_taken") val takenTimestamp: String,
    @SerializedName("description") val description: String,
    @SerializedName("published") val publishedTimestamp: String,
    @SerializedName("author") val authorName: String,
    @SerializedName("author_id") val authorId: String,
    @SerializedName("tags") val tags: String,
)
