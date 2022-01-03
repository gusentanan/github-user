package com.bagusmerta.github_user.core.utils

import com.bagusmerta.github_user.core.data.source.remote.response.UserDetailResponse
import com.bagusmerta.github_user.core.data.source.remote.response.UserItemResponse
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

}