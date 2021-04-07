package com.picpay.desafio.android.users.mock

import com.picpay.desafio.android.users.model.User
import com.picpay.desafio.android.users.repository.local.entity.UserEntity
import com.picpay.desafio.android.users.repository.remote.response.UserResponse

object UserMock {

    fun mockUserResponse() = listOf(
        UserResponse(
            1,
            "teste.com",
            "Gabriel Gasparotto",
            "gaspa"
        )
    )

    fun mockUserEntity() = listOf(
        UserEntity(
            1,
            "teste.com",
            "Gabriel Gasparotto",
            "gaspa"
        )
    )

    const val ERROR_EMPTY_LIST = "lista vazia"
    fun mockUserSuccessEmpty(): Result<List<User>> = Result.success(emptyList())
    fun mockUserSuccess(): Result<List<User>> = Result.success(mockUserEntity())
    fun mockUserFailure(exception: Throwable): Result<List<User>> =
        Result.failure(exception)
}