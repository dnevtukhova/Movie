package com.dnevtukhova.movie.interactor

import androidx.constraintlayout.solver.LinearSystem
import com.dnevtukhova.movie.api.FilmsItem
import com.dnevtukhova.movie.api.FilmsObject
import com.dnevtukhova.movie.api.NetworkConstants
import com.dnevtukhova.movie.api.ServerApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FilmsListInteractor {
    var api: ServerApi
    init {
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
        api = retrofit.create(ServerApi::class.java)
    }

    fun getFilms(callBack: GetFilmsListCallBack) {
        api.getFilms().enqueue(object : Callback<FilmsObject> {
            override fun onResponse(call: Call<FilmsObject>, response: Response<FilmsObject>) {
                if (response.isSuccessful) {
                    callBack.onSuccess(response.body()!!.filmsList)
                } else {
                    callBack.onError("!!! произошла ошибка ${response.code()}")
                }
            }

            override fun onFailure(call: Call<FilmsObject>, t: Throwable) {
                callBack.onError("!!! произошла ошибка $t")
            }
        })
    }


    interface GetFilmsListCallBack {
        fun onSuccess(filmsList: MutableList<FilmsItem>)
        fun onError(error: String)
    }
}