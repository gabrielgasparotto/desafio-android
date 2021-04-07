package com.picpay.desafio.android.users.di

import androidx.room.Room.databaseBuilder
import com.picpay.desafio.android.service.WebService.URL_PICPAY
import com.picpay.desafio.android.service.WebService.service
import com.picpay.desafio.android.users.repository.UserRepository
import com.picpay.desafio.android.users.repository.UserRepositoryImpl
import com.picpay.desafio.android.users.repository.local.constants.DataBaseConstants.DATABASE_NAME
import com.picpay.desafio.android.users.repository.local.data.UserLocalDataService
import com.picpay.desafio.android.users.repository.local.database.UserDataBase
import com.picpay.desafio.android.users.repository.remote.data.UserRemoteDataService
import com.picpay.desafio.android.users.repository.remote.service.UserService
import com.picpay.desafio.android.users.usecase.UserUseCase
import com.picpay.desafio.android.users.usecase.UserUseCaseImpl
import com.picpay.desafio.android.users.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object UserDI {

    private val userModule = module {
        viewModel { UserViewModel(useCase = get()) }
        factory { UserUseCaseImpl(repository = get()) as UserUseCase }
        factory {
            UserRepositoryImpl(
                remoteService = get(),
                localDataSource = get()
            ) as UserRepository
        }
        single { service<UserService>(URL_PICPAY) as UserRemoteDataService }
        single { databaseBuilder(get(), UserDataBase::class.java, DATABASE_NAME).build() }
        single { get<UserDataBase>().userDAO() as UserLocalDataService }
    }

    fun init() = loadKoinModules(userModule)
}