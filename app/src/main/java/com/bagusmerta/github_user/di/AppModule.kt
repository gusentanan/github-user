package com.bagusmerta.github_user.di

import com.bagusmerta.github_user.core.domain.usecase.UsersUseCase
import com.bagusmerta.github_user.core.domain.usecase.UsersUseCaseImpl
import com.bagusmerta.github_user.presentation.detail.DetailViewModel
import com.bagusmerta.github_user.presentation.favorite.FavoriteViewModel
import com.bagusmerta.github_user.presentation.followers.FollowersViewModel
import com.bagusmerta.github_user.presentation.following.FollowingViewModel
import com.bagusmerta.github_user.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<UsersUseCase>  { UsersUseCaseImpl(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FollowersViewModel(get())}
    viewModel { FollowingViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}

