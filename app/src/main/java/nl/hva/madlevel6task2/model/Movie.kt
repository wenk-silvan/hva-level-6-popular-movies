package nl.hva.madlevel6task2.model

import com.google.gson.annotations.SerializedName
import nl.hva.madlevel6task2.Constants

data class Movie(
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val vote_average: Double,
) {
    fun getPosterUrl() = Constants.baseUrlImg + Constants.imgSizeS + poster_path
    fun getBackdropUrl() = Constants.baseUrlImg + Constants.imgSizeL + backdrop_path
}