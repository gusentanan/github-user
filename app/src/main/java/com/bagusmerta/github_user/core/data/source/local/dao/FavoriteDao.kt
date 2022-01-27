package com.bagusmerta.github_user.core.data.source.local.dao

import androidx.room.*
import com.bagusmerta.github_user.core.data.source.local.entity.FavoriteEntity
import com.bagusmerta.github_user.core.utils.Constants.GET_ALL_FAVORITE_USER
import com.bagusmerta.github_user.core.utils.Constants.GET_FAVORITE_USER_BY_USERNAME
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query(GET_ALL_FAVORITE_USER)
    fun getAllFavoriteUsers(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteUser(entity: FavoriteEntity)

    @Delete
    suspend fun deleteFavoriteUser(entity: FavoriteEntity)

    @Query(GET_FAVORITE_USER_BY_USERNAME)
    fun getFavoriteUserUsernameByUsername(username: String): Flow<FavoriteEntity>

}