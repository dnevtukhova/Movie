package com.dnevtukhova.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dnevtukhova.movie.R
import com.dnevtukhova.movie.api.FilmsItem
import com.dnevtukhova.movie.entity.EntityName
import com.dnevtukhova.movie.entity.Genre
import com.dnevtukhova.movie.entity.RowType
import com.dnevtukhova.movie.presenter.FilmsListPresenter
import com.dnevtukhova.movie.view.adapter.FilmsListAdapter
import com.dnevtukhova.movie.view.view.FilmsListView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.films_list_fragment.*
import kotlinx.android.synthetic.main.films_list_fragment.view.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class FilmsListFragment : MvpAppCompatFragment(), FilmsListView {
    companion object {
        const val KEY = "KeyFilmsItem"
    }

    @Inject
    lateinit var presenterProvider: javax.inject.Provider<FilmsListPresenter>

    private lateinit var recycler: RecyclerView
    private lateinit var adapterFilms: FilmsListAdapter
    private lateinit var layoutManager: GridLayoutManager
    private val filmListPresenter by moxyPresenter { presenterProvider.get() }

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
        //     filmListPresenter.getFilms()
        //  filmListPresenter.isInRestoreState(this)
        swipeRefreshLayout.setOnRefreshListener {
            filmListPresenter.clearList()
            filmListPresenter.getFilms()
            swipeRefreshLayout.isRefreshing = false
        }
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
        layoutManager.also {
            it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position > items.size + 1)
                        1
                    else
                        2
                }
            }
        }
        adapterFilms.setItems(items)
    }

    override fun getFilmsName(items: EntityName) {
        val list = mutableListOf<EntityName>()
        list.add(items)
        adapterFilms.setItems(list)
    }

    override fun getGenresName(items: EntityName) {
        val list = mutableListOf<EntityName>()
        list.add(items)
        adapterFilms.setItems(list)
    }

    override fun onFilmsClick(filmsItemInFragment: FilmsItem) {
        val bundle = Bundle()
        bundle.putParcelable(KEY, filmsItemInFragment)
        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                detailFragment,
                DetailFragment.TAG
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onGenreClick(genre: Genre, items: MutableList<FilmsItem>) {
        adapterFilms.setItems(items)
        adapterFilms.setGenre(genre)
        adapterFilms.notifyDataSetChanged()
    }

    override fun clearList() {
        adapterFilms.clearList()
    }

    override fun onDestroy() {
        super.onDestroy()
        filmListPresenter.onDestroy()
    }

    private fun initRecycler(view: View) {
        recycler = view.filmsList
        layoutManager = GridLayoutManager(context, 2)
        recycler.layoutManager = layoutManager
        adapterFilms = FilmsListAdapter(
            LayoutInflater.from(context),
            object : FilmsListAdapter.OnFilmsClickListener {

                override fun onFilmsClick(filmsItem: RowType, position: Int) {
                    filmListPresenter.onClickFilm(filmsItem as FilmsItem)
                }

                override fun onGenreClick(genre: RowType, position: Int) {
                    filmListPresenter.onGenreClick(genre as Genre)
                    adapterFilms.notifyDataSetChanged()
                }
            })
        recycler.adapter = adapterFilms
        adapterFilms.notifyDataSetChanged()
    }
}