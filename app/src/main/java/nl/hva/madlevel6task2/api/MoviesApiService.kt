package nl.hva.madlevel6task2.api

import nl.hva.madlevel6task2.Constants
import nl.hva.madlevel6task2.model.Discover
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApiService {
    @GET(Constants.apiKey + Constants.year + "{year}")
    suspend fun getMovies(@Path("year") year: String): Discover
}