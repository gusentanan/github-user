package com.bagusmerta.github_user.core.di


import androidx.room.Room
import com.bagusmerta.github_user.BuildConfig
import com.bagusmerta.github_user.core.data.source.UsersRepository
import com.bagusmerta.github_user.core.data.source.local.GithubUserDatabase
import com.bagusmerta.github_user.core.data.source.remote.network.ApiServices
import com.bagusmerta.github_user.core.domain.repository.IUsersRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<GithubUserDatabase>().favoriteDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            GithubUserDatabase::class.java, "Github.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor{ chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", BuildConfig.API_KEY)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiServices::class.java)
    }
}

val repositoryModule = module {
    single<IUsersRepository> { UsersRepository(get(), get()) }
}