package com.dnevtukhova.movie.view.view

import com.dnevtukhova.movie.api.FilmsItem
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface FilmsDetailView: MvpView {
    @AddToEndSingle
    fun setDescription (filmsItem: FilmsItem)

    @AddToEndSingle
    fun setImage (filmsItem: FilmsItem)

    @AddToEndSingle
    fun setGenre (genres: String)

    @AddToEndSingle
    fun setRating (rating: String)

    @AddToEndSingle
    fun setYear (year: String)

    @AddToEndSingle
    fun setTitle (title: String)
}