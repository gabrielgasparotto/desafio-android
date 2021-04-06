package com.picpay.desafio.android.users.repository.local.dao

import androidx.room.*
import com.picpay.desafio.android.users.repository.local.entity.UserEntity
import com.picpay.desafio.android.users.repository.local.data.UserLocalDataService

@Dao
interface UserDAO : UserLocalDataService {

    @Query("SELECT * FROM User")
    override suspend fun getAllUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<UserEntity>)

    @Query("DELETE FROM User")
    suspend fun deleteAllUsers()

    @Transaction
    override suspend fun deleteAndInsert(users: List<UserEntity>) {
        deleteAllUsers()
        insertAllUsers(users)
    }
}