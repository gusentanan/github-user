package com.bagusmerta.github_user.presentation.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.domain.usecase.UsersUseCase
import com.bagusmerta.github_user.core.utils.LoadingState
import com.bagusmerta.github_user.core.utils.ResultState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val usersUseCase: UsersUseCase): ViewModel() {

    private val _state = MutableLiveData<LoadingState>()
    private val _error = MutableLiveData<String?>()
    private val _result = MutableLiveData<List<UsersItemSearch>?>()
    private val _splashState = MutableStateFlow(true)

    val state: LiveData<LoadingState>
        get() = _state

    val result: LiveData<List<UsersItemSearch>?>
        get() = _result

    val splashState
        get() = _splashState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            _splashState.value = false
        }
    }

    fun getUsersByUsername(q: String) {
        _state.value = LoadingState.ShowLoading
        viewModelScope.launch {
            usersUseCase.getUsersByUsername(q).collect {
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

}