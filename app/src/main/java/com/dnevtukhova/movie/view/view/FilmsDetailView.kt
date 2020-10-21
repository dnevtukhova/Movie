package com.dnevtukhova.movie.view.view

import com.dnevtukhova.movie.api.FilmsItem

interface FilmsDetailView {
    fun setDescription (filmsItem: FilmsItem)
    fun setImage (filmsItem: FilmsItem)
    fun setGenre (genres: String)
    fun setRating (rating: String)
    fun setYear (year: String)
    fun setTitle (title: String)
}