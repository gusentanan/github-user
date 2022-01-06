package com.bagusmerta.github_user.presentation.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.domain.usecase.UsersUseCase
import com.bagusmerta.github_user.core.utils.LoadingState
import com.bagusmerta.github_user.core.utils.ResultState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FollowingViewModel(private val usersUseCase: UsersUseCase): ViewModel() {
    private val _state = MutableLiveData<LoadingState>()
    private val _error = MutableLiveData<String?>()
    private val _resFollowing = MutableLiveData<List<UsersItemSearch>?>()

    val state: LiveData<LoadingState>
        get() = _state

    val resFollowing: LiveData<List<UsersItemSearch>?>
        get() = _resFollowing


    fun getUsersFollowing(username: String){
        viewModelScope.launch {
            _state.value = LoadingState.ShowLoading
            usersUseCase.getUsersFollowing(username).collect {
                when(it){
                    is ResultState.Success -> {
                        _resFollowing.postValue(it.data)
                        _state.value = LoadingState.HideLoading
                    }
                    is ResultState.Error -> _error.postValue(it.errMessage)
                    is ResultState.Empty -> _resFollowing.postValue(null)
                }
            }
        }
    }

}