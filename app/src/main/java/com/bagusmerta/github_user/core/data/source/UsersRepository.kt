package com.bagusmerta.github_user.core.data.source

import android.util.Log
import com.bagusmerta.github_user.core.data.source.local.dao.FavoriteDao
import com.bagusmerta.github_user.core.data.source.remote.network.ApiServices
import com.bagusmerta.github_user.core.domain.model.FavoriteUser
import com.bagusmerta.github_user.core.domain.model.UserDetail
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.domain.repository.IUsersRepository
import com.bagusmerta.github_user.core.utils.DataMapper
import com.bagusmerta.github_user.core.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.lang.Exception

class UsersRepository(private val apiServices: ApiServices, private val favoriteDao: FavoriteDao): IUsersRepository {

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

    override suspend fun getDetailUser(username: String): Flow<ResultState<UserDetail>> {
        return flow {
            try {
                val res = apiServices.getDetailUser(username)
                val dataMap = DataMapper.mapUserDetailResponseToDomain(res)
                emit(ResultState.Success(dataMap))
            } catch (e: Exception){
                emit(ResultState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUsersFollowers(username: String): Flow<ResultState<List<UsersItemSearch>>> {
        return flow {
            try {
                val res = apiServices.getUserFollowers(username)
                val dataMap = DataMapper.mapUserSearchResponseToDomain(res) // gonna fix this later
                emit(ResultState.Success(dataMap))
            } catch (e: Exception){
                emit(ResultState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUsersFollowing(username: String): Flow<ResultState<List<UsersItemSearch>>> {
        return flow {
            try {
                val res = apiServices.getUserFollowing(username)
                val dataMap = DataMapper.mapUserSearchResponseToDomain(res) // this one too !
                emit(ResultState.Success(dataMap))
            } catch (e: Exception){
                emit(ResultState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getAllFavoriteUsers(): Flow<List<FavoriteUser>> {
        return favoriteDao.getAllFavoriteUsers().map {
            DataMapper.mapFavoriteUserToDomain(it)
        }
    }

    override suspend fun addFavoriteUser(entity: FavoriteUser) {
        val data = DataMapper.mapFavoriteUserToEntity(entity)
        Log.d("HELLO", "========================================================= $data")
        return favoriteDao.addFavoriteUser(data)
    }

    override suspend fun deleteFavoriteUser(entity: FavoriteUser) {
        val data = DataMapper.mapFavoriteUserToEntity(entity)
        return favoriteDao.deleteFavoriteUser(data)
    }
}