package com.picpay.desafio.android.users.usecase

import com.picpay.desafio.android.users.model.User

interface UserUseCase {

    suspend fun getUsers(): Result<List<User>>
}