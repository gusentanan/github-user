package com.bagusmerta.github_user

import android.app.Application
import com.bagusmerta.github_user.core.di.databaseModule
import com.bagusmerta.github_user.core.di.networkModule
import com.bagusmerta.github_user.core.di.repositoryModule
import com.bagusmerta.github_user.di.useCaseModule
import com.bagusmerta.github_user.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}