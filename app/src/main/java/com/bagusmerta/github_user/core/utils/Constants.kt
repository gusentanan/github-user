package com.bagusmerta.github_user.core.utils

object Constants {
    const val EXTRA_USERNAME = "username"
    const val GET_ALL_FAVORITE_USER = "SELECT * FROM favorite_table"
    const val GET_FAVORITE_USER_BY_USERNAME = "SELECT * FROM favorite_table WHERE username = :username"
}