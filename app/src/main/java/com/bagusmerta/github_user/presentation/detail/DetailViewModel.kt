package com.bagusmerta.github_user.presentation.detail

import androidx.lifecycle.*
import com.bagusmerta.github_user.core.domain.model.FavoriteUser
import com.bagusmerta.github_user.core.domain.model.UserDetail
import com.bagusmerta.github_user.core.domain.usecase.UsersUseCase
import com.bagusmerta.github_user.core.utils.LoadingState
import com.bagusmerta.github_user.core.utils.ResultState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel(private val usersUseCase: UsersUseCase): ViewModel() {
    private val _state = MutableLiveData<LoadingState>()
    private val _result = MutableLiveData<UserDetail?>()
    private val _error = MutableLiveData<String?>()
    private val _insertState = MutableLiveData<Boolean>()
    private val _deleteState = MutableLiveData<Boolean>()


    val state: LiveData<LoadingState>
        get() = _state

    val result: LiveData<UserDetail?>
        get() = _result

    val insertState: LiveData<Boolean>
        get() = _insertState

    val deleteState: LiveData<Boolean>
        get() = _deleteState


    fun getDetailUser(username: String){
        _state.value = LoadingState.ShowLoading
        viewModelScope.launch {
            usersUseCase.getDetailUser(username).collect {
                when(it){
                    is ResultState.Success -> {
                        _result.postValue(it.data)
                        _state.value = LoadingState.HideLoading
                    }
                    is ResultState.Error -> {
                        _error.postValue(it.errMessage)
                    }
                    is ResultState.Empty -> {
                        _result.postValue(null)
                    }
                }
            }
        }
    }

    fun addFavoriteUser(user: FavoriteUser){
        viewModelScope.launch{
            try {
                usersUseCase.addFavoriteUser(user)
                _insertState.postValue(true)
            } catch (e: Exception){
                _error.postValue(e.message)
                _insertState.postValue(false)
            }
        }
    }

    fun deleteFavoriteUser(user: FavoriteUser){
        viewModelScope.launch {
            try {
                usersUseCase.deleteFavoriteUser(user)
                _deleteState.postValue(true)
            } catch (e: Exception){
                _error.postValue(e.message)
                _deleteState.postValue(false)
            }
        }
    }

    fun getFavoriteUserByUsername(username: String) = usersUseCase.getFavoriteUserByUsername(username).asLiveData()

}