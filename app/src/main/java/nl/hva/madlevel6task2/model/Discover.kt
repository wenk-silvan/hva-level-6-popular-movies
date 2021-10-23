package nl.hva.madlevel6task2.model

import com.google.gson.annotations.SerializedName
import nl.hva.madlevel6task2.Constants

data class Discover(
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("results") val results: List<Movie> = arrayListOf()
)