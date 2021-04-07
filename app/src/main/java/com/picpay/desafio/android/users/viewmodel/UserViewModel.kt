package com.picpay.desafio.android.users.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.users.model.User
import com.picpay.desafio.android.users.repository.local.exception.UserDatabaseException
import com.picpay.desafio.android.users.usecase.UserUseCase
import com.picpay.desafio.android.users.usecase.exception.UserEmptyException
import kotlinx.coroutines.launch

class UserViewModel(private val useCase: UserUseCase) : ViewModel() {

    private val viewState: MutableLiveData<UserViewState> = MutableLiveData()
    val state: LiveData<UserViewState> = viewState

    fun init() {
        viewModelScope.launch {
            interpret(useCase.getUsers())
        }
    }

    private fun interpret(result: Result<List<User>>) {
        if (result.isSuccess) {
            result.getOrNull()?.let {
                viewState.postValue(UserViewState.UserSuccess(it))
            }
        } else {
            result.exceptionOrNull()?.let {
                interpretException(it)
            }
        }
    }

    private fun interpretException(error: Throwable) {
        when (error) {
            is UserEmptyException -> viewState.postValue(UserViewState.UserEmptyError(error))
            is UserDatabaseException -> viewState.postValue(UserViewState.UserDatabaseError(error))
            else -> viewState.postValue(UserViewState.UserError(error))
        }
    }
}