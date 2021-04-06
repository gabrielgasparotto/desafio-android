package com.picpay.desafio.android.users.repository.remote.service

import com.picpay.desafio.android.users.repository.remote.data.UserRemoteDataService
import com.picpay.desafio.android.users.repository.remote.response.UserResponse
import retrofit2.http.GET

interface UserService : UserRemoteDataService {
    @GET("users")
    override suspend fun getUsers(): List<UserResponse>
}