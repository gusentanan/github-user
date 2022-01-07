package com.bagusmerta.github_user.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bagusmerta.github_user.core.data.source.local.dao.FavoriteDao
import com.bagusmerta.github_user.core.data.source.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class GithubUserDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}