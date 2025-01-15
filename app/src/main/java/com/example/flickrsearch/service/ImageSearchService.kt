package com.example.flickrsearch.service

import com.example.flickrsearch.model.ImageSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageSearchService {

    @GET("photos_public.gne")
    suspend fun search(
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: String = "1",
        @Query("tags") userQuery: String,
    ): Response<ImageSearchResponse>

}