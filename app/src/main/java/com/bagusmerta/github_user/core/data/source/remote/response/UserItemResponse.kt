package com.bagusmerta.github_user.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserItemResponse(
    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("login")
    var login: String?,

    @field:SerializedName("avatar_url")
    var avatar: String?
)
