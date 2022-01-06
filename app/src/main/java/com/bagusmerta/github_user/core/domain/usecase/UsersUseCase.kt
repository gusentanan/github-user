package com.bagusmerta.github_user.core.domain.usecase

import com.bagusmerta.github_user.core.domain.model.UserDetail
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface UsersUseCase {

    suspend fun getUsersByUsername(username: String): Flow<ResultState<List<UsersItemSearch>>>
    suspend fun getDetailUser(username: String): Flow<ResultState<UserDetail>>
    suspend fun getUsersFollowers(username: String): Flow<ResultState<List<UsersItemSearch>>>
    suspend fun getUsersFollowing(username: String): Flow<ResultState<List<UsersItemSearch>>>
}