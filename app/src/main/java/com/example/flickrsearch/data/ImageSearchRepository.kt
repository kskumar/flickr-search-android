package com.example.flickrsearch.data

import com.example.flickrsearch.model.ImageSearchResponse
import retrofit2.Response

interface ImageSearchRepository {
    suspend fun search(userQuery: String): Response<ImageSearchResponse>
}