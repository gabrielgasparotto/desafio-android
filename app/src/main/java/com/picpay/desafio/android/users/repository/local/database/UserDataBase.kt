package com.picpay.desafio.android.users.repository.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.users.repository.local.constants.DataBaseConstants
import com.picpay.desafio.android.users.repository.local.entity.UserEntity
import com.picpay.desafio.android.users.repository.local.dao.UserDAO

@Database(
    version = DataBaseConstants.DATABASE_VERSION,
    entities = [UserEntity::class],
    exportSchema = false
)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
}