package com.bagusmerta.github_user.presentation.favorite

import androidx.lifecycle.*
import com.bagusmerta.github_user.core.domain.usecase.UsersUseCase

class FavoriteViewModel(private val usersUseCase: UsersUseCase): ViewModel() {

    fun getAllFavoriteUsers() = usersUseCase.getAllFavoriteUsers().asLiveData()

}