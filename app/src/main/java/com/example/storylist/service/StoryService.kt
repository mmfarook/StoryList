package com.example.storylist.service

import com.example.storylist.model.CollectionStories
import com.example.storylist.model.StoryResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface StoryService {
    @GET("/{slug}")
    fun getAllStories(@Path("slug") slug: String): Observable<StoryResponse>

    @GET("/{slug}")
    fun getStories(@Path("slug") slug: String): Observable<CollectionStories>

}