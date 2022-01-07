package com.bagusmerta.github_user.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoriteEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?,
    @ColumnInfo(name = "company") val
    company: String?,
    @ColumnInfo(name = "public_repos")
    val publicRepos: Int?,
    @ColumnInfo(name = "followers")
    val followers: Int?,
    @ColumnInfo(name = "following")
    val following: Int?,
    @ColumnInfo(name = "location")
    val location: String?

)
