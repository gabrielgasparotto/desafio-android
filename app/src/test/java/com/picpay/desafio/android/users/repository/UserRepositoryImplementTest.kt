package com.picpay.desafio.android.users.repository

import com.picpay.desafio.android.users.mock.UserMock
import com.picpay.desafio.android.users.mock.UserMock.mockUserEntity
import com.picpay.desafio.android.users.repository.local.data.UserLocalDataService
import com.picpay.desafio.android.users.repository.remote.exception.UserServiceException
import com.picpay.desafio.android.users.repository.remote.service.UserService
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.io.IOException
import java.net.UnknownHostException

class UserRepositoryImplementTest {

    @Test(expected = UserServiceException::class)
    fun `getUsers servico com erro deve jogar uma UserServiceException`() {
        val serviceError: UserService = mockk()
        val mockException = UnknownHostException("mockError")
        coEvery { serviceError.getUsers() } throws mockException
        val repository = UserRepositoryImpl(serviceError, mockk())
        runBlocking {
            repository.getUsers()
        }
    }

    @Test
    fun `getUsersCache com database ok deve retorar result Success`() {
        val local: UserLocalDataService = mockk()
        coEvery { local.getAllUsers() } returns mockUserEntity()
        coEvery { local.deleteAndInsert(mockUserEntity()) } just runs
        val repository: UserRepository = UserRepositoryImpl(mockk(), local)

        runBlocking {
            val result = repository.getUsersCache()
            assertTrue(result.isSuccess)
            assertEquals(mockUserEntity(), result.getOrNull())
        }
    }

    @Test
    fun `getUsersCache database com error deve retorar result Failure`() {
        val local: UserLocalDataService = mockk()
        val exception = IOException("mockError")
        coEvery { local.getAllUsers() } throws exception
        coEvery { local.deleteAndInsert(mockUserEntity()) } just runs
        val repository: UserRepository = UserRepositoryImpl(mockk(), local)

        runBlocking {
            val result = repository.getUsersCache()
            Assert.assertTrue(result.isFailure)
            Assert.assertEquals(exception.cause, result.exceptionOrNull()?.cause)
        }
    }

    @Test
    fun `getUsers servico ok deve retornar user list`() {
        val service: UserService = mockk()
        val local: UserLocalDataService = mockk()
        coEvery { service.getUsers() } returns UserMock.mockUserResponse()
        coEvery { local.getAllUsers() } returns mockUserEntity()
        coEvery { local.deleteAndInsert(mockUserEntity()) } just runs

        val repository: UserRepository = UserRepositoryImpl(service, local)
        runBlocking {
            val result = repository.getUsers()
            Assert.assertTrue(result.isSuccess)
            assertEquals(mockUserEntity(), result.getOrNull())
        }
    }
}