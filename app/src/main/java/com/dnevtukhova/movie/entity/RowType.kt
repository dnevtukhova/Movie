package com.dnevtukhova.movie.entity

interface RowType {
    val FILMS_ROW_TYPE: Int
        get() = 0
    val GENRE_ROW_TYPE: Int
        get() = 1
    val ENTITY_NAME_ROW_TYPE: Int
        get() = 2

    fun getItemViewType(): Int

}