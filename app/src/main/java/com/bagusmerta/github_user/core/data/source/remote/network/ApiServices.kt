package com.bagusmerta.github_user.core.data.source.remote.network

import com.bagusmerta.github_user.core.data.source.remote.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("search/users?")
    suspend fun getUsersBySearch(@Query("q") q: String) : SearchResponse

}