package com.dnevtukhova.movie.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnevtukhova.movie.R
import com.dnevtukhova.movie.api.FilmsItem
import com.dnevtukhova.movie.entity.EntityName
import com.dnevtukhova.movie.entity.Genre
import com.dnevtukhova.movie.entity.RowType
import kotlinx.android.synthetic.main.fragment_entity_item.view.*
import kotlinx.android.synthetic.main.fragment_films_item.view.*
import kotlinx.android.synthetic.main.fragment_genre_item.view.*

class FilmsListAdapter(
    private val inflater: LayoutInflater,
    private val listener: OnFilmsClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<RowType>()

    fun clearList() {
        items.clear()
    }

    fun setItems(films: List<RowType>) {

        val indexes = mutableListOf<FilmsItem>()
        for (n in items) {
            if (n is FilmsItem) {
                indexes.add(n)

            }
        }

        for (n in indexes) {
            items.remove(n)
        }

        items.addAll(films)
        notifyDataSetChanged()
    }

    fun setGenre(genre: Genre) {
        for (n in items) {
            if (n is Genre) {
                n.isClicked = false
            }
            if (n is Genre && n.genre == (genre.genre)) {
                n.isClicked = true
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getItemViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            0 -> return FilmsViewHolder(
                inflater
                    .inflate(R.layout.fragment_films_item, parent, false)
            )
            1 -> return GenreViewHolder(
                inflater
                    .inflate(R.layout.fragment_genre_item, parent, false)
            )
            2 -> return EntityNameViewHolder(
                inflater
                    .inflate(R.layout.fragment_entity_item, parent, false)
            )
        }
        return FilmsViewHolder(
            inflater
                .inflate(R.layout.fragment_films_item, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FilmsViewHolder) {
            val item = items[position]
            holder.bind(item as FilmsItem)
            holder.itemView.setOnClickListener { listener.onFilmsClick(item, position) }
        }
        if (holder is GenreViewHolder) {
            val item = items[position]
            holder.bind(item as Genre)
            holder.itemView.setOnClickListener { listener.onGenreClick(item, position) }
        }
        if (holder is EntityNameViewHolder) {
            val item = items[position]
            holder.bind(item as EntityName)
        }
    }

    interface OnFilmsClickListener {
        fun onFilmsClick(filmsItem: RowType, position: Int)
        fun onGenreClick(genre: RowType, position: Int)
    }


    class FilmsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val filmsTitle: TextView = view.film_title
        private val filmImage: ImageView = view.film_image

        fun bind(film: FilmsItem) {
            filmsTitle.text = film.localizedName
            Glide.with(itemView.context)
                .load(film.imageUrl)
                .placeholder(R.color.colorBlackTintButton)
                .error(R.drawable.no_image)
                .centerCrop()
                .into(filmImage)
        }
    }

    class GenreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val genreText: TextView = view.textGenre
        private val container = view.container

        fun bind(genre: Genre) {
            genreText.text = genre.genre
            if (genre.isClicked) {
                container.setBackgroundResource(R.drawable.blue_rectangle)
            } else {
                container.setBackgroundResource(R.drawable.gray_rectangle)
            }
        }
    }

    class EntityNameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameText: TextView = view.textEntity
        fun bind(name: EntityName) {
            nameText.text = name.name
        }
    }
}
