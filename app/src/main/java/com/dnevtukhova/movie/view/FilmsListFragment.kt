package com.dnevtukhova.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dnevtukhova.movie.R
import com.dnevtukhova.movie.api.FilmsItem
import com.dnevtukhova.movie.entity.Genre
import com.dnevtukhova.movie.entity.RowType
import com.dnevtukhova.movie.interactor.FilmsListInteractor
import com.dnevtukhova.movie.presenter.FilmsListPresenter
import com.dnevtukhova.movie.view.adapter.FilmsListAdapter
import com.dnevtukhova.movie.view.view.FilmsListView
import kotlinx.android.synthetic.main.films_list_fragment.*
import kotlinx.android.synthetic.main.films_list_fragment.view.*

class FilmsListFragment: Fragment(), FilmsListView {
    companion object {
        const val TAG = "FilmsListFragment"
        const val KEY = "KeyFilmsItem"
    }

    private lateinit var recycler: RecyclerView
    private lateinit var adapterFilms: FilmsListAdapter
    private val filmListPresenter = FilmsListPresenter(FilmsListInteractor(), this)
    lateinit var layoutManager: GridLayoutManager
    lateinit var list: List<Genre>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.films_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler(view)
        filmListPresenter.getFilms()
    }

    override fun showProgress() {
        progressbar.visibility = View.VISIBLE
        filmsList.visibility = View.GONE
    }

    override fun hideProgress() {
        progressbar.visibility = View.GONE
        filmsList.visibility = View.VISIBLE
    }

    override fun getFilms(items: MutableList<FilmsItem>) {
        adapterFilms.setItems(items)
      //  layoutManager.spanCount = 2
    }

    override fun getError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun getGenres(items: List<Genre>) {
        //for(n in items){println("$n, ")}
        layoutManager.also { it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position > items.size-1)
                    1
                else
                    2
            }
        } }
        adapterFilms.setItems(items)
    }

    override fun onFilmsClick(filmsItem: FilmsItem) {
        val bundle = Bundle()
        bundle.putParcelable(KEY,filmsItem)
        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                detailFragment,
                DetailFragment.TAG)
            .addToBackStack(DetailFragment.TAG)
            .commit()
    }

    override fun onGenreClick(genre: Genre, items: MutableList<FilmsItem>) {
        adapterFilms.setItems(items)
        adapterFilms.setGenre(genre)
        adapterFilms.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        filmListPresenter.onDestroy()
    }

    private fun initRecycler (view: View) {
        recycler = view.filmsList
        layoutManager = GridLayoutManager(context,2)
//            .also {
//            it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//                override fun getSpanSize(position: Int): Int {
//                    return if (position > 12) //номер позиции захардкожен!!!! нужно динамически получать!!!!
//                        1
//                    else
//                        2
//                }
//            }
//        }

        recycler.layoutManager = layoutManager
        adapterFilms = FilmsListAdapter(LayoutInflater.from(context), object: FilmsListAdapter.OnFilmsClickListener {


            override fun onFilmsClick(filmsItem: RowType, position: Int) {
                filmListPresenter.onClickFilm(filmsItem as FilmsItem)
            }

            override fun onGenreClick(genre: RowType, position: Int) {

                filmListPresenter.onGenreClick(genre as Genre, position)
                adapterFilms.notifyDataSetChanged()
            }
        })
        recycler.adapter= adapterFilms
        adapterFilms.notifyDataSetChanged()
    }
}