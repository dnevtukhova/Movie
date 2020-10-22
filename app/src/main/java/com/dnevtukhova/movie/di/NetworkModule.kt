package com.dnevtukhova.movie.di

import androidx.constraintlayout.solver.LinearSystem
import com.dnevtukhova.movie.api.NetworkConstants
import com.dnevtukhova.movie.api.ServerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun initRetrofit(): ServerApi {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                return@addInterceptor chain.proceed(
                    chain
                        .request()
                        .newBuilder()
                        .build()
                )
            }
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        if (LinearSystem.DEBUG) {
                            level = HttpLoggingInterceptor.Level.BASIC
                        }
                    })
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ServerApi::class.java)
    }
}