package nl.hva.madlevel6task2.api

import nl.hva.madlevel6task2.Constants
import nl.hva.madlevel6task2.model.Discover
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET(Constants.apiKey)
    suspend fun getMovies(@Query(Constants.year) year: String): Discover
}