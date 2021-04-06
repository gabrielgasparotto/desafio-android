package com.picpay.desafio.android.users.repository.remote.data

import com.picpay.desafio.android.users.repository.remote.response.UserResponse

interface UserRemoteDataService {
    suspend fun getUsers(): List<UserResponse>
}