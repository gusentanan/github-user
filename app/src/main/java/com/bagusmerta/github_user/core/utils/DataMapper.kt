package com.bagusmerta.github_user.core.utils

import com.bagusmerta.github_user.core.data.source.local.entity.FavoriteEntity
import com.bagusmerta.github_user.core.data.source.remote.response.UserDetailResponse
import com.bagusmerta.github_user.core.data.source.remote.response.UserItemResponse
import com.bagusmerta.github_user.core.domain.model.FavoriteUser
import com.bagusmerta.github_user.core.domain.model.UserDetail
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch

object DataMapper {

    fun mapUserSearchResponseToDomain(data: List<UserItemResponse>): List<UsersItemSearch> =
        data.map {
            UsersItemSearch(
                id = it.id,
                login = it.login,
                avatar = it.avatar,
            )
        }

    fun mapUserDetailResponseToDomain(data: UserDetailResponse): UserDetail =
        UserDetail(
            avatarUrl = data.avatarUrl.toString(),
            name = data.name.toString(),
            login = data.login.toString(),
            followers = data.followers,
            following = data.following,
            publicRepos = data.publicRepos,
            id = data.id,
            location = data.location,
            company = data.company
        )

    fun mapFavoriteUserToDomain(data: List<FavoriteEntity>): List<FavoriteUser> =
        data.map {
            FavoriteUser(
                username = it.username,
                name = it.name,
                location = it.location,
                company = it.company,
                publicRepos = it.publicRepos,
                followers = it.followers,
                following = it.following,
                avatarUrl = it.avatarUrl
            )
        }

    fun mapFavoriteUserDomainToEntity(data: FavoriteUser): FavoriteEntity =
        FavoriteEntity(
            username = data.username,
            name = data.name,
            location = data.location,
            company = data.company,
            publicRepos = data.publicRepos,
            followers = data.followers,
            following = data.following,
            avatarUrl = data.avatarUrl
        )

    fun mapFavoriteUserEntityToDomain(data: FavoriteEntity?): FavoriteUser ?=
        data?.let {
            FavoriteUser(
                username = it.username,
                name = data.name,
                location = data.location,
                company = data.company,
                publicRepos = data.publicRepos,
                followers = data.followers,
                following = data.following,
                avatarUrl = data.avatarUrl
            )
        }

    fun mapDetailUserToFavoriteUser(data: UserDetail): FavoriteUser =
        FavoriteUser(
            username = data.login.toString(),
            name = data.name,
            location = data.location,
            company = data.company,
            publicRepos = data.publicRepos,
            following = data.following,
            followers = data.followers,
            avatarUrl = data.avatarUrl
        )

}