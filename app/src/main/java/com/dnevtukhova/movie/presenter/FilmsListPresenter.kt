package com.dnevtukhova.movie.presenter

import com.dnevtukhova.movie.api.FilmsItem
import com.dnevtukhova.movie.entity.EntityName
import com.dnevtukhova.movie.entity.Genre
import com.dnevtukhova.movie.interactor.FilmsListInteractorImpl
import com.dnevtukhova.movie.view.view.FilmsListView
import moxy.MvpPresenter
import javax.inject.Inject

class FilmsListPresenter @Inject
constructor(
    private val filmsInteractor: FilmsListInteractorImpl
) : MvpPresenter<FilmsListView>() {

    var filmsListLocal: MutableList<FilmsItem> = mutableListOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getFilms()
    }

    fun clearList() {
        viewState.clearList()
    }

    fun getFilms() {
        viewState.showProgress()
        filmsInteractor.getFilms(object : FilmsListInteractorImpl.GetFilmsListCallBack {
            override fun onSuccess(filmsList: MutableList<FilmsItem>) {
                filmsList.sortWith(compareBy { it.localizedName })
                filmsListLocal = filmsList
                viewState.hideProgress()
                viewState.getFilmsName(EntityName("Жанры"))
                viewState.getGenres(getGenres(filmsList))
                viewState.getGenresName(EntityName("Фильмы"))
                viewState.getFilms(filmsList)
            }

            override fun onError(error: String) {
                viewState.hideProgress()
                viewState.getError(error)
            }
        })
    }

    fun onClickFilm(filmsItem: FilmsItem) {
        viewState.onFilmsClick(filmsItem)
    }

    fun onGenreClick(genre: Genre) {
        if (filmsListLocal.isNotEmpty()) {
            genre.isClicked = true
            viewState.onGenreClick(genre, filmInGenre(genre.genre, filmsListLocal))
        } else {
            viewState.showProgress()
            filmsInteractor.getFilms(object : FilmsListInteractorImpl.GetFilmsListCallBack {
                override fun onSuccess(filmsList: MutableList<FilmsItem>) {
                    filmsList.sortWith(compareBy { it.localizedName })
                    filmsListLocal = filmsList
                    viewState.hideProgress()
                    viewState.onGenreClick(
                        genre,
                        filmInGenre(genre.genre, filmsListLocal)
                    )
                }

                override fun onError(error: String) {
                    viewState.hideProgress()
                    viewState.getError(error)
                }
            })
        }

    }

    fun getGenres(filmsList: MutableList<FilmsItem>): List<Genre> {
        val set: MutableSet<Genre> = mutableSetOf()
        for (n in filmsList) {
            if (n.genres.size != 0) {
                for (i in n.genres) {
                    set.add(stringToGenre(i))
                }
            }
        }
        return set.toList()
    }

    private fun stringToGenre(genre: String): Genre {
        return Genre(genre, false)
    }

    fun filmInGenre(genre: String, filmsList: MutableList<FilmsItem>): MutableList<FilmsItem> {
        val listInGenre: MutableList<FilmsItem> = mutableListOf()
        for (n in filmsList) {
            if (n.genres.contains(genre)) {
                listInGenre.add(n)
            }
        }
        return listInGenre
    }

}