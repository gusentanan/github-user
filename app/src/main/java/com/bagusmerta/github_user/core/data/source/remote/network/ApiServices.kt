package com.bagusmerta.github_user.core.data.source.remote.network

import com.bagusmerta.github_user.core.data.source.remote.response.SearchResponse
import com.bagusmerta.github_user.core.data.source.remote.response.UserDetailResponse
import com.bagusmerta.github_user.core.data.source.remote.response.UsersFollowingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("search/users?")
    suspend fun getUsersBySearch(@Query("q") q: String) : SearchResponse

    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username: String): UserDetailResponse

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String): UsersFollowingResponse

    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): UsersFollowingResponse

}