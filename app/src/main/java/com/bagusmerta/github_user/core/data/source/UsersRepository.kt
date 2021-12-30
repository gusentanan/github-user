package com.bagusmerta.github_user.core.data.source

import com.bagusmerta.github_user.core.data.source.remote.network.ApiServices
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.domain.repository.IUsersRepository
import com.bagusmerta.github_user.core.utils.DataMapper
import com.bagusmerta.github_user.core.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class UsersRepository(private val apiServices: ApiServices): IUsersRepository {

    override suspend fun getUsersByUsername(username: String): Flow<ResultState<List<UsersItemSearch>>> {
        return flow {
            try {
                val res = apiServices.getUsersBySearch(username)
                val dataMap = res.items?.let { data ->
                    DataMapper.mapUserSearchResponseToDomain(data)
                }
                emit(ResultState.Success(dataMap))
            } catch (e: Exception){
                emit(ResultState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}