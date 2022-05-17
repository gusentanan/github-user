package com.bagusmerta.github_user.core.domain.usecase


import com.bagusmerta.github_user.core.data.source.UsersRepository
import com.bagusmerta.github_user.core.domain.model.FavoriteUser
import com.bagusmerta.github_user.core.domain.model.UserDetail
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface UsersUseCase {

    suspend fun getUsersByUsername(username: String): Flow<ResultState<List<UserDetail>>>
    suspend fun getDetailUser(username: String): Flow<ResultState<UserDetail>>
    suspend fun getUsersFollowers(username: String): Flow<ResultState<List<UserDetail>>>
    suspend fun getUsersFollowing(username: String): Flow<ResultState<List<UserDetail>>>

    fun getAllFavoriteUsers(): Flow<List<FavoriteUser>>
    suspend fun addFavoriteUser(entity: FavoriteUser)
    suspend fun deleteFavoriteUser(entity: FavoriteUser)
    fun getFavoriteUserByUsername(username: String): Flow<FavoriteUser?>
}


class UsersUseCaseImpl(private val userRepository: UsersRepository): UsersUseCase {

    override suspend fun getUsersByUsername(username: String): Flow<ResultState<List<UserDetail>>> {
        return userRepository.getUsersByUsername(username)
    }

    override suspend fun getDetailUser(username: String): Flow<ResultState<UserDetail>> {
        return userRepository.getDetailUser(username)
    }

    override suspend fun getUsersFollowers(username: String): Flow<ResultState<List<UserDetail>>> {
        return userRepository.getUsersFollowers(username)
    }

    override suspend fun getUsersFollowing(username: String): Flow<ResultState<List<UserDetail>>> {
        return userRepository.getUsersFollowing(username)
    }

    override fun getAllFavoriteUsers(): Flow<List<FavoriteUser>> {
        return userRepository.getAllFavoriteUsers()
    }

    override suspend fun addFavoriteUser(entity: FavoriteUser) {
        try {
            userRepository.addFavoriteUser(entity)
        }catch (e:Exception){
            throw Exception(e)
        }
    }

    override suspend fun deleteFavoriteUser(entity: FavoriteUser) {
        try {
            userRepository.deleteFavoriteUser(entity)
        }catch (e:Exception){
            throw Exception(e)
        }
    }

    override fun getFavoriteUserByUsername(username: String): Flow<FavoriteUser?>{
        return userRepository.getFavoriteUserByUsername(username)
    }

}

