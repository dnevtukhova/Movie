package com.dnevtukhova.movie.api

import android.os.Parcelable
import com.dnevtukhova.movie.entity.RowType
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmsItem (
 @SerializedName("id") val id: Int,
 @SerializedName("localized_name") val localizedName: String,
 @SerializedName("name") val name: String,
 @SerializedName("year") val year: Int,
 @SerializedName("rating") val rating: Float,
 @SerializedName("image_url") val imageUrl: String,
 @SerializedName("description") val description: String,
 @SerializedName("genres") val genres: MutableList<String>
) : Parcelable, RowType {
 override fun getItemViewType(): Int {
  return FILMS_ROW_TYPE
 }
}


