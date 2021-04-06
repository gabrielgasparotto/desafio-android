package com.picpay.desafio.android.users.repository.local.data

import com.picpay.desafio.android.users.repository.local.entity.UserEntity

interface UserLocalDataService {
    suspend fun getAllUsers(): List<UserEntity>
    suspend fun deleteAndInsert(users: List<UserEntity>)
}