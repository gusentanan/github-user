package com.bagusmerta.github_user.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bagusmerta.github_user.core.domain.usecase.UsersUseCase

class FavoriteViewModel(private val usersUseCase: UsersUseCase): ViewModel() {

    fun getAllFavoriteUsers() = usersUseCase.getAllFavoriteUsers().asLiveData()

}