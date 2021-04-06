package com.picpay.desafio.android.users.repository

import com.picpay.desafio.android.users.model.User
import com.picpay.desafio.android.users.repository.local.exception.UserDatabaseException
import com.picpay.desafio.android.users.repository.local.entity.UserEntity
import com.picpay.desafio.android.users.repository.local.data.UserLocalDataService
import com.picpay.desafio.android.users.repository.remote.data.UserRemoteDataService
import com.picpay.desafio.android.users.repository.remote.exception.UserServiceException
import com.picpay.desafio.android.users.repository.remote.response.UserResponse
import com.picpay.desafio.android.users.repository.remote.response.mapResponseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val remoteService: UserRemoteDataService,
    private val localDataSource: UserLocalDataService
) : UserRepository {

    override suspend fun getUsers(): Result<List<User>> {
        insertUserCache(getUserRemote().mapResponseEntity())
        return getUsersLocal()
    }

    override suspend fun getUsersCache() = getUsersLocal()

    private suspend fun getUserRemote() = withContext(Dispatchers.IO) {
        try {
            remoteService.getUsers()
        } catch (exception: Exception) {
            throw UserServiceException(exception)
        }
    }

    private suspend fun getUsersLocal(): Result<List<User>> = withContext(Dispatchers.IO) {
        try {
            Result.success(localDataSource.getAllUsers())
        } catch (exception: Exception) {
            Result.failure<List<UserResponse>>(UserDatabaseException(exception))
        }
    }

    private suspend fun insertUserCache(users: List<UserEntity>) = withContext(Dispatchers.IO) {
        localDataSource.deleteAndInsert(users)
    }
}