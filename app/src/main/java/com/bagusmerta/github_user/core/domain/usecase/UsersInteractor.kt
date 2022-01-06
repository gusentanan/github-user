package com.bagusmerta.github_user.core.domain.usecase

import com.bagusmerta.github_user.core.domain.model.UserDetail
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.domain.repository.IUsersRepository
import com.bagusmerta.github_user.core.utils.ResultState
import kotlinx.coroutines.flow.Flow

class UsersInteractor(private val userRepository: IUsersRepository): UsersUseCase {

    override suspend fun getUsersByUsername(username: String): Flow<ResultState<List<UsersItemSearch>>> {
        return userRepository.getUsersByUsername(username)
    }

    override suspend fun getDetailUser(username: String): Flow<ResultState<UserDetail>> {
        return userRepository.getDetailUser(username)
    }

    override suspend fun getUsersFollowers(username: String): Flow<ResultState<List<UsersItemSearch>>> {
        return userRepository.getUsersFollowers(username)
    }

    override suspend fun getUsersFollowing(username: String): Flow<ResultState<List<UsersItemSearch>>> {
        return userRepository.getUsersFollowing(username)
    }

}