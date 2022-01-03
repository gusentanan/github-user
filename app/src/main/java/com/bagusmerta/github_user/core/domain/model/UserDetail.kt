package com.bagusmerta.github_user.core.domain.model

import com.google.gson.annotations.SerializedName

data class UserDetail(
    val avatarUrl: String?,
    val company: String?,
    val followers: Int?,
    val following: Int?,
    val id: Int?,
    val location: String?,
    val login: String?,
    val name: String?,
    val publicRepos: Int?,
)
