package com.example.flickrsearch.vm

sealed class ViewEvent {
    data class NavigateToImageDetails(
        val imageUrl: String,
        val title: String,
        val description: String,
        val author: String,
        val date: String
    ): ViewEvent()
}