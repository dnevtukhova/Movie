package com.dnevtukhova.movie.api

import com.google.gson.annotations.SerializedName

data class FilmsObject (
     @SerializedName("films") val filmsList: MutableList<FilmsItem>
)
