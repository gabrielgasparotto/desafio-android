package com.picpay.desafio.android.users.repository

import com.picpay.desafio.android.users.model.User

interface UserRepository {
    suspend fun getUsers(): Result<List<User>>
    suspend fun getUsersCache(): Result<List<User>>
}