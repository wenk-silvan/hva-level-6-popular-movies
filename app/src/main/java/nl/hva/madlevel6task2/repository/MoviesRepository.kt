package nl.hva.madlevel6task2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.withTimeout
import nl.hva.madlevel6task2.Constants
import nl.hva.madlevel6task2.api.MoviesApi
import nl.hva.madlevel6task2.api.MoviesApiService
import nl.hva.madlevel6task2.model.Discover

class MoviesRepository {
    private val moviesApiService: MoviesApiService = MoviesApi.createApi()
    private val _discover: MutableLiveData<Discover> = MutableLiveData()

    val discover: LiveData<Discover>
        get() = _discover

    suspend fun getPopularMovies(year: String) {
        try {
            val result = withTimeout(Constants.apiTimeout) {
                moviesApiService.getMovies(year)
            }

            _discover.value = result
        } catch (error: Throwable) {
            throw MoviesRefreshError("Unable to fetch movies", error)
        }
    }

    class MoviesRefreshError(message: String, cause: Throwable) : Throwable(message, cause)
}