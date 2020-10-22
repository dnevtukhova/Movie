package com.dnevtukhova.movie.interactor

import com.dnevtukhova.movie.api.FilmsItem
import com.dnevtukhova.movie.api.FilmsObject
import com.dnevtukhova.movie.api.ServerApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class FilmsListInteractorImpl @Inject
constructor(private val api: ServerApi) : FilmsListInteractor {

    override fun getFilms(callBack: GetFilmsListCallBack) {
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