package com.bagusmerta.github_user.core.domain.usecase

import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.domain.repository.IUsersRepository
import com.bagusmerta.github_user.core.utils.ResultState
import kotlinx.coroutines.flow.Flow

class UsersInteractor(private val userRepository: IUsersRepository): UsersUseCase {

    override suspend fun getUsersByUsername(username: String): Flow<ResultState<List<UsersItemSearch>>> {
        return userRepository.getUsersByUsername(username)
    }

}