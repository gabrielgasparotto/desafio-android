package com.picpay.desafio.android.users.viewmodel

import com.picpay.desafio.android.users.model.User

sealed class UserViewState {

    data class UserSuccess(val users: List<User>) : UserViewState()
    data class UserError(val error: Throwable) : UserViewState()
    data class UserEmptyError(val error: Throwable) : UserViewState()
    data class UserDatabaseError(val error: Throwable) : UserViewState()
}