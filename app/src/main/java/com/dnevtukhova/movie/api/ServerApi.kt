package com.dnevtukhova.movie.api

import retrofit2.Call
import retrofit2.http.GET

interface ServerApi {
    @GET("films.json")
    fun getFilms(): Call<FilmsObject>
}