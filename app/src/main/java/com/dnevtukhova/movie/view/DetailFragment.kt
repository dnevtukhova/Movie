package com.dnevtukhova.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dnevtukhova.movie.R
import com.dnevtukhova.movie.api.FilmsItem
import com.dnevtukhova.movie.presenter.DetailPresenter
import com.dnevtukhova.movie.view.view.FilmsDetailView
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_film_detail.*

class DetailFragment: Fragment(), FilmsDetailView {
    companion object {
        const val TAG = "DetailFragment"
        const val KEY = "KeyFilmsItem"
    }
    val presenterDetail = DetailPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            val filmsitem: FilmsItem? = bundle.getParcelable(KEY)
            presenterDetail.getDetailFilmInfo(filmsitem!!)
        }
    }

    override fun setDescription(filmsItem: FilmsItem) {
        description.text = filmsItem.description
    }

    override fun setImage(filmsItem: FilmsItem) {
        Glide.with(image_detail_film.context)
            .load(filmsItem.imageUrl)
            .placeholder(R.color.colorBlackTintButton)
            .error(R.drawable.no_image)
            .into(image_detail_film)
    }

    override fun setGenre(genres: String) {
        genres_text.text = genres
    }

    override fun setRating(rating: String) {
        ratingText.text = rating
    }

    override fun setYear(year: String) {
        year_text.text = year
    }

    override fun setTitle(title: String) {
        val collapsingToolbarLayout =
            requireView().findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbarLayout.title = title
        collapsingToolbarLayout.setExpandedTitleColor(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.transparent
            )
        )
    }
}