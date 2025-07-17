package com.amosh.mediaapp.core.data.dataSource.api

import com.amosh.mediaapp.core.domain.model.HomeSectionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApiService {
    @GET("https://api-v2-b2sit6oh3a-uc.a.run.app/home_sections")
    suspend fun getHomeSections(
        @Query("page") page : Int
    ): Response<HomeSectionsResponse>

    @GET("https://mock.apidog.com/m1/735111-711675-default/search")
    suspend fun getHomeSearchSections(
        @Query("query") query : String
    ): Response<HomeSectionsResponse>
}