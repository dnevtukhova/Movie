package com.dnevtukhova.movie.interactor

interface FilmsListInteractor {
    fun getFilms(callBack: FilmsListInteractorImpl.GetFilmsListCallBack)
}