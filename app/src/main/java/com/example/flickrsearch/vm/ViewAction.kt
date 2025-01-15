package com.example.flickrsearch.vm

sealed class ViewAction {
    data class ImageClick(
        val imageUrl: String,
        val title: String,
        val description: String,
        val authorName: String,
        val publishedDate: String
    ) : ViewAction()
}