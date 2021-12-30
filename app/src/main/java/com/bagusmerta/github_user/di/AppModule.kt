package com.bagusmerta.github_user.di

import com.bagusmerta.github_user.core.domain.usecase.UsersInteractor
import com.bagusmerta.github_user.core.domain.usecase.UsersUseCase
import com.bagusmerta.github_user.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<UsersUseCase>  { UsersInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}
