package com.bagusmerta.github_user.core.domain.repository

import com.bagusmerta.github_user.core.domain.model.FavoriteUser
import com.bagusmerta.github_user.core.domain.model.UserDetail
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface IUsersRepository {
    // remote
    suspend fun getUsersByUsername(username: String): Flow<ResultState<List<UsersItemSearch>>>
    suspend fun getDetailUser(username: String): Flow<ResultState<UserDetail>>
    suspend fun getUsersFollowers(username: String): Flow<ResultState<List<UsersItemSearch>>>
    suspend fun getUsersFollowing(username: String): Flow<ResultState<List<UsersItemSearch>>>

    // local
    fun getAllFavoriteUsers(): Flow<List<FavoriteUser>>
    suspend fun addFavoriteUser(entity: FavoriteUser)
    suspend fun deleteFavoriteUser(entity: FavoriteUser)
    suspend fun getFavoriteUserByUsername(username: String): FavoriteUser?

}