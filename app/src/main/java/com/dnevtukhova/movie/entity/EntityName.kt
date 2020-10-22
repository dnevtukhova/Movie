package com.dnevtukhova.movie.entity

class EntityName(val name: String): RowType {
    override fun getItemViewType(): Int {
        return ENTITY_NAME_ROW_TYPE
    }
}