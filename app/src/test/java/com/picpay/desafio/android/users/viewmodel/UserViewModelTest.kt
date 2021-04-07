package com.picpay.desafio.android.users.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.users.CoroutineTestRule
import com.picpay.desafio.android.users.mock.UserMock.mockUserEntity
import com.picpay.desafio.android.users.mock.UserMock.mockUserFailure
import com.picpay.desafio.android.users.mock.UserMock.mockUserSuccess
import com.picpay.desafio.android.users.repository.local.exception.UserDatabaseException
import com.picpay.desafio.android.users.usecase.UserUseCase
import com.picpay.desafio.android.users.usecase.exception.UserEmptyException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class UserViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val scope = CoroutineTestRule()

    @Test
    fun `UserViewStateSucess ao init viewmodel com getUsers ok e com lista preenchida`() {
        val useCase: UserUseCase = mockk()
        coEvery { useCase.getUsers() } returns mockUserSuccess()
        val viewModel = UserViewModel(useCase)
        runBlocking {
            viewModel.init()
            assertEquals(UserViewState.UserSuccess(mockUserEntity()), viewModel.state.value)
        }
    }

    @Test
    fun `UserViewStateEmptyError ao init viewmodel com getUsers ok e com lista vazia`() {
        val useCase: UserUseCase = mockk()
        val exception = UserEmptyException()
        coEvery { useCase.getUsers() } returns mockUserFailure(exception)
        val viewModel = UserViewModel(useCase)
        runBlocking {
            viewModel.init()
            assertEquals(UserViewState.UserEmptyError(exception), viewModel.state.value)
        }
    }

    @Test
    fun `UserViewState com error ao init viewmodel com getUsers com alguma exception tratada`() {
        val useCase: UserUseCase = mockk()
        val exception = UserDatabaseException(IOException("mock"))
        coEvery { useCase.getUsers() } returns mockUserFailure(exception)
        val viewModel = UserViewModel(useCase)
        runBlocking {
            viewModel.init()
            assertEquals(UserViewState.UserDatabaseError(exception), viewModel.state.value)
        }
    }

    @Test
    fun `UserError ao init viewmodel com getUsers exception desconhecida`() {
        val useCase: UserUseCase = mockk()
        val exception = NullPointerException("mock")
        coEvery { useCase.getUsers() } returns mockUserFailure(exception)
        val viewModel = UserViewModel(useCase)
        runBlocking {
            viewModel.init()
            assertEquals(UserViewState.UserError(exception), viewModel.state.value)
        }
    }
}