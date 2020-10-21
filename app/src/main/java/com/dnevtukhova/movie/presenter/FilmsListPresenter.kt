package com.dnevtukhova.movie.presenter

import com.dnevtukhova.movie.api.FilmsItem
import com.dnevtukhova.movie.entity.Genre
import com.dnevtukhova.movie.interactor.FilmsListInteractor
import com.dnevtukhova.movie.view.FilmsListFragment

class FilmsListPresenter(
    private val filmsInteractor: FilmsListInteractor,
    var filmsListFragment: FilmsListFragment?
) {

    var filmsListLocal: MutableList<FilmsItem> = mutableListOf()


    fun getFilms() {
        filmsListFragment!!.showProgress()
        filmsInteractor.getFilms(object : FilmsListInteractor.GetFilmsListCallBack {
            override fun onSuccess(filmsList: MutableList<FilmsItem>) {
                filmsList.sortWith(compareBy { it.localizedName })
                filmsListLocal = filmsList
                filmsListFragment!!.hideProgress()
                filmsListFragment!!.getGenres(getGenres(filmsList))
                filmsListFragment!!.getFilms(filmsList)
            }

            override fun onError(error: String) {
                filmsListFragment!!.hideProgress()
                filmsListFragment!!.getError(error)
            }
        })
    }

    fun onDestroy() {
        filmsListFragment = null
    }

    fun onClickFilm(filmsItem: FilmsItem) {
        filmsListFragment!!.onFilmsClick(filmsItem)
    }

    fun onGenreClick(genre: Genre, position: Int) {
        if (filmsListLocal.isNotEmpty()) {
            genre.isClicked = true
            filmsListFragment!!.onGenreClick(genre, filmInGenre(genre.genre, filmsListLocal))
        } else {
            filmsListFragment!!.showProgress()
            filmsInteractor.getFilms(object : FilmsListInteractor.GetFilmsListCallBack {
                override fun onSuccess(filmsList: MutableList<FilmsItem>) {
                    filmsList.sortWith(compareBy { it.localizedName })
                    filmsListLocal = filmsList
                    filmsListFragment!!.hideProgress()
                    filmsListFragment!!.onGenreClick(
                        genre,
                        filmInGenre(genre.genre, filmsListLocal)
                    )
                }

                override fun onError(error: String) {
                    filmsListFragment!!.hideProgress()
                    filmsListFragment!!.getError(error)
                }
            })
        }

    }

    fun getGenres(filmsList: MutableList<FilmsItem>): List<Genre> {
        var set: MutableSet<Genre> = mutableSetOf()
        for (n in filmsList) {
            if (n.genres.size != 0) {
                for (n in n.genres) {
                    set.add(stringToGenre(n))
                }
            }
        }
        return set.toList()
    }

    private fun stringToGenre(genre: String): Genre {
        return Genre(genre, false)
    }

    fun filmInGenre(genre: String, filmsList: MutableList<FilmsItem>): MutableList<FilmsItem> {
        var listInGenre: MutableList<FilmsItem> = mutableListOf()
        for (n in filmsList) {
            if (n.genres.contains(genre)) {
                listInGenre.add(n)
            }
        }
        return listInGenre
    }

}