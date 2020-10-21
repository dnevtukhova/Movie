package com.dnevtukhova.movie.view.view

import android.provider.MediaStore
import com.dnevtukhova.movie.api.FilmsItem
import com.dnevtukhova.movie.entity.Genre

interface FilmsListView {
    fun showProgress()
    fun hideProgress()
    fun getFilms(items: MutableList<FilmsItem>)
    fun getError(error: String)
    fun getGenres(items: List<Genre>)
    fun onFilmsClick(item: FilmsItem)
    fun onGenreClick(genre: Genre, items: MutableList<FilmsItem>)
}