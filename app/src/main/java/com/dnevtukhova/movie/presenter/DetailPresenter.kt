package com.dnevtukhova.movie.presenter

import com.dnevtukhova.movie.api.FilmsItem
import com.dnevtukhova.movie.view.DetailFragment

class DetailPresenter(private val fragment: DetailFragment) {

    fun getDetailFilmInfo(filmsDetail: FilmsItem) {
        fragment.setImage(filmsDetail)
        fragment.setDescription(filmsDetail)
        fragment.setTitle(filmsDetail.localizedName)
        fragment.setYear("Год выпуска: ${filmsDetail.year}г.")
        fragment.setRating("Рейтинг: ${filmsDetail.rating}")
        var genres = ""
        for (n in filmsDetail.genres) {
            genres = if (genres.equals("")) {
                n
            } else {
                "$genres, $n"
            }
        }
        fragment.setGenre(genres)
    }
}