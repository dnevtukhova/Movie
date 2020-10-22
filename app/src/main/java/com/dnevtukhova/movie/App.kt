package com.dnevtukhova.movie

import android.app.Application
import androidx.constraintlayout.solver.LinearSystem.DEBUG

import com.dnevtukhova.movie.api.NetworkConstants.BASE_URL
import com.dnevtukhova.movie.api.ServerApi
import com.dnevtukhova.movie.interactor.FilmsListInteractorImpl
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class App : Application()