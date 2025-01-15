package com.example.flickrsearch.vm

import com.example.flickrsearch.model.FlickrImage

data class ViewState(
    val userQueryTextValue: String = "",
    val isLoading: Boolean = false,
    val results: List<FlickrImage> = emptyList(),
)