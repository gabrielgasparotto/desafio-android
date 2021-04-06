package com.picpay.desafio.android.application

import android.app.Application
import com.picpay.desafio.android.users.di.UserDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PicPayApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PicPayApplication)
        }
        UserDI.init()
    }
}