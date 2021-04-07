package com.picpay.desafio.android.users.usecase

import com.picpay.desafio.android.users.mock.UserMock
import com.picpay.desafio.android.users.mock.UserMock.ERROR_EMPTY_LIST
import com.picpay.desafio.android.users.mock.UserMock.mockUserSuccess
import com.picpay.desafio.android.users.repository.UserRepository
import com.picpay.desafio.android.users.repository.remote.exception.UserServiceException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class UserUseCaseImplTest {

    @Test
    fun `getUsers com repository ok deve retornar sucesso`() {
        val repository: UserRepository = mockk()
        coEvery { repository.getUsers() } returns mockUserSuccess()
        val useCase: UserUseCase = UserUseCaseImpl(repository)
        runBlocking {
            val result = useCase.getUsers()
            assertTrue(result.isSuccess)
            assertEquals(UserMock.mockUserEntity(), result.getOrNull())
        }
    }

    @Test
    fun `getUsers com repository off mas com cache deve retornar sucesso`() {
        val repository: UserRepository = mockk()
        val exception =
            UserServiceException(
                IOException("mockError")
            )
        coEvery { repository.getUsers() } throws exception
        coEvery { repository.getUsersCache() } returns mockUserSuccess()
        val useCase: UserUseCase = UserUseCaseImpl(repository)
        runBlocking {
            val result = useCase.getUsers()
            assertTrue(result.isSuccess)
            assertEquals(UserMock.mockUserEntity(), result.getOrNull())
        }
    }

    @Test
    fun `getUsers com repository ok mas retornar lista vazia ou nula deve retornar falha`() {
        val repository: UserRepository = mockk()
        coEvery { repository.getUsers() } returns UserMock.mockUserSuccessEmpty()
        val useCase: UserUseCase = UserUseCaseImpl(repository)
        runBlocking {
            val result = useCase.getUsers()
            assertTrue(result.isFailure)
            assertEquals(ERROR_EMPTY_LIST, result.exceptionOrNull()?.message)
        }
    }

    @Test
    fun `getUsers com repository off e sem cache deve retornar falha`() {
        val repository: UserRepository = mockk()
        val exception =
            UserServiceException(
                IOException("mockError")
            )
        coEvery { repository.getUsers() } throws exception
        coEvery { repository.getUsersCache() } returns UserMock.mockUserSuccessEmpty()
        val useCase: UserUseCase = UserUseCaseImpl(repository)
        runBlocking {
            val result = useCase.getUsers()
            assertTrue(result.isFailure)
            assertEquals(ERROR_EMPTY_LIST, result.exceptionOrNull()?.message)
        }
    }

    @Test
    fun `getUsers repository e base off deve retornar falha`() {
        val repository: UserRepository = mockk()
        val exception =
            UserServiceException(
                IOException("mockError")
            )
        coEvery { repository.getUsers() } throws exception
        coEvery { repository.getUsersCache() } returns UserMock.mockUserFailure(exception)
        val useCase: UserUseCase = UserUseCaseImpl(repository)
        runBlocking {
            val result = useCase.getUsers()
            assertTrue(result.isFailure)
            assertEquals(exception, result.exceptionOrNull())
        }
    }
}