package com.picpay.desafio.android.users.di

import com.picpay.desafio.android.service.WebService.URL_PICPAY
import com.picpay.desafio.android.service.WebService.service
import com.picpay.desafio.android.users.repository.UserRepository
import com.picpay.desafio.android.users.repository.UserRepositoryImpl
import com.picpay.desafio.android.users.repository.remote.data.UserRemoteDataService
import com.picpay.desafio.android.users.repository.remote.service.UserService
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object UserDI {

    private val userModule = module {
        factory {
            UserRepositoryImpl(
                remoteService = get(),
                localDataSource = get()
            ) as UserRepository
        }
        single { service<UserService>(URL_PICPAY) as UserRemoteDataService }
    }

    fun init() = loadKoinModules(userModule)
}