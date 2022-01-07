package com.bagusmerta.github_user.core.domain.model

data class FavoriteUser(
    val username: String,
    val name: String?,
    val avatarUrl: String?,
    val company: String?,
    val publicRepos: Int?,
    val followers: Int?,
    val following: Int?,
    val location: String?
)
