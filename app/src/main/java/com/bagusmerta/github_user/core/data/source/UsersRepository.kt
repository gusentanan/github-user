package com.bagusmerta.github_user.core.data.source

import com.bagusmerta.github_user.core.data.source.local.dao.FavoriteDao
import com.bagusmerta.github_user.core.data.source.remote.network.ApiServices
import com.bagusmerta.github_user.core.domain.model.FavoriteUser
import com.bagusmerta.github_user.core.domain.model.UserDetail
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.utils.DataMapper
import com.bagusmerta.github_user.core.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

interface UsersRepository {
    // remote
    suspend fun getUsersByUsername(username: String): Flow<ResultState<List<UserDetail>>>
    suspend fun getDetailUser(username: String): Flow<ResultState<UserDetail>>
    suspend fun getUsersFollowers(username: String): Flow<ResultState<List<UserDetail>>>
    suspend fun getUsersFollowing(username: String): Flow<ResultState<List<UserDetail>>>

    // local
    fun getAllFavoriteUsers(): Flow<List<FavoriteUser>>
    suspend fun addFavoriteUser(entity: FavoriteUser)
    suspend fun deleteFavoriteUser(entity: FavoriteUser)
    fun getFavoriteUserByUsername(username: String): Flow<FavoriteUser?>

}

class UsersRepositoryImpl(private val apiServices: ApiServices, private val favoriteDao: FavoriteDao): UsersRepository {

    override suspend fun getUsersByUsername(username: String): Flow<ResultState<List<UserDetail>>> {
        return flow {
            val listUsers = arrayListOf<UserDetail>()
            try {
                val res = apiServices.getUsersBySearch(username)
                if(res.items != null){
                    res.items.forEach {
                        val data = apiServices.getDetailUser(it.login)
                        val final = DataMapper.mapUserDetailResponseToDomain(data)
                        listUsers.add(final)
                    }
                }
                emit(ResultState.Success(listUsers))
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

    override suspend fun getUsersFollowers(username: String): Flow<ResultState<List<UserDetail>>> {
        return flow {
            val listFollowers = arrayListOf<UserDetail>()
            try {
                val res = apiServices.getUserFollowers(username)
                res.forEach {
                    val data = apiServices.getDetailUser(it.login)
                    val final = DataMapper.mapUserDetailResponseToDomain(data)
                    listFollowers.add(final)
                }
                emit(ResultState.Success(listFollowers))
            } catch (e: Exception){
                emit(ResultState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUsersFollowing(username: String): Flow<ResultState<List<UserDetail>>> {
        return flow {
            val listFollowing = arrayListOf<UserDetail>()
            try {
                val res = apiServices.getUserFollowing(username)
                res.forEach {
                    val data = apiServices.getDetailUser(it.login)
                    val final = DataMapper.mapUserDetailResponseToDomain(data)
                    listFollowing.add(final)
                }
                emit(ResultState.Success(listFollowing))
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
        val data = DataMapper.mapFavoriteUserDomainToEntity(entity)
        return favoriteDao.addFavoriteUser(data)
    }

    override suspend fun deleteFavoriteUser(entity: FavoriteUser) {
        val data = DataMapper.mapFavoriteUserDomainToEntity(entity)
        return favoriteDao.deleteFavoriteUser(data)
    }

    override fun getFavoriteUserByUsername(username: String): Flow<FavoriteUser?>{
        return favoriteDao.getFavoriteUserUsernameByUsername(username).map {
            DataMapper.mapFavoriteUserEntityToDomain(it)
        }
    }
}