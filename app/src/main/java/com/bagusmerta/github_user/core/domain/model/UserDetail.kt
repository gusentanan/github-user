package com.bagusmerta.github_user.core.domain.model


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
