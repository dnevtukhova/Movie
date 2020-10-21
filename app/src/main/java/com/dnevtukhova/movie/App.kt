package com.dnevtukhova.movie

import android.app.Application
import androidx.constraintlayout.solver.LinearSystem.DEBUG

import com.dnevtukhova.movie.api.NetworkConstants.BASE_URL
import com.dnevtukhova.movie.api.ServerApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    lateinit var api: ServerApi

    override fun onCreate() {
        super.onCreate()
        instance = this
        initRetrofit()
    }

    private fun initRetrofit() {
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
                        if (DEBUG) {
                            level = HttpLoggingInterceptor.Level.BASIC
                        }
                    })
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        api = retrofit.create(ServerApi::class.java)
    }

}