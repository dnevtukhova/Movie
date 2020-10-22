package com.dnevtukhova.movie.view.view

import android.provider.MediaStore
import com.dnevtukhova.movie.api.FilmsItem
import com.dnevtukhova.movie.entity.EntityName
import com.dnevtukhova.movie.entity.Genre
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface FilmsListView: MvpView {
    @AddToEndSingle
    fun showProgress()

    @AddToEndSingle
    fun hideProgress()

    @AddToEndSingle
    fun getFilms(items: MutableList<FilmsItem>)

    @OneExecution
    fun getError(error: String)

    @AddToEndSingle
    fun getGenres(items: List<Genre>)

    @AddToEndSingle
    fun getFilmsName(items: EntityName)

    @AddToEndSingle
    fun getGenresName(items: EntityName)

   @OneExecution
    fun onFilmsClick(item: FilmsItem)

   @AddToEndSingle
    fun onGenreClick(genre: Genre, items: MutableList<FilmsItem>)

    @AddToEndSingle
    fun clearList()
}