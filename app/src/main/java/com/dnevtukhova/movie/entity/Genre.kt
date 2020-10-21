package com.dnevtukhova.movie.entity

data class Genre(val genre: String, var isClicked: Boolean) : RowType {
    override fun getItemViewType(): Int {
        return GENRE_ROW_TYPE
    }
}