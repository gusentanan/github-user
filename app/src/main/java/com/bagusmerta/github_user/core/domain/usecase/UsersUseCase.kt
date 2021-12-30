package com.bagusmerta.github_user.core.domain.usecase

import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface UsersUseCase {

    suspend fun getUsersByUsername(username: String): Flow<ResultState<List<UsersItemSearch>>>

}