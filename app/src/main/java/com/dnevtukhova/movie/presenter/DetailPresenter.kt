package com.dnevtukhova.movie.presenter

import com.dnevtukhova.movie.api.FilmsItem
import com.dnevtukhova.movie.view.view.FilmsDetailView
import moxy.MvpPresenter
import javax.inject.Inject

class DetailPresenter @Inject
constructor(): MvpPresenter<FilmsDetailView>() {

    fun getDetailFilmInfo(filmsDetail: FilmsItem) {
        viewState.setImage(filmsDetail)
        viewState.setDescription(filmsDetail)
        viewState.setTitle(filmsDetail.localizedName)
        viewState.setYear("Год выпуска: ${filmsDetail.year}г.")
        viewState.setRating("Рейтинг: ${filmsDetail.rating}")
        var genres = ""
        for (n in filmsDetail.genres) {
            genres = if (genres.equals("")) {
                n
            } else {
                "$genres, $n"
            }
        }
        viewState.setGenre(genres)
    }
}