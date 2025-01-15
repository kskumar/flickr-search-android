package com.example.flickrsearch.data

import com.example.flickrsearch.model.ImageSearchResponse
import com.example.flickrsearch.service.ImageSearchService
import retrofit2.Response
import javax.inject.Inject

class ImageSearchRepositoryImpl @Inject constructor(
    private val service: ImageSearchService
) : ImageSearchRepository {

    override suspend fun search(userQuery: String): Response<ImageSearchResponse> =
        service.search(userQuery = userQuery)

}