package com.bagusmerta.github_user.core.utils

import com.bagusmerta.github_user.core.data.source.remote.response.UserItemResponse
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

}