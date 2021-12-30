package com.bagusmerta.github_user.core.utils

sealed class LoadingState {
    object ShowLoading: LoadingState()
    object HideLoading: LoadingState()
}