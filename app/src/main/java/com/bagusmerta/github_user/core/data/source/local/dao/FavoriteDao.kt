package com.bagusmerta.github_user.core.data.source.local.dao

import androidx.room.*
import com.bagusmerta.github_user.core.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_table")
    fun getAllFavoriteUsers(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteUser(entity: FavoriteEntity)

    @Delete
    suspend fun deleteFavoriteUser(entity: FavoriteEntity)

}