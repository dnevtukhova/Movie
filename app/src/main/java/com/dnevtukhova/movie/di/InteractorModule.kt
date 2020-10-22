package com.dnevtukhova.movie.di

import com.dnevtukhova.movie.interactor.FilmsListInteractor
import com.dnevtukhova.movie.interactor.FilmsListInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class InteractorModule {
    @Binds
    abstract fun provideDailyPictureInteractor(
        implementation: FilmsListInteractorImpl
    ): FilmsListInteractor
}