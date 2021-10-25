package nl.hva.madlevel6task2.vm

import android.app.Application
import android.app.ProgressDialog
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.hva.madlevel6task2.model.Movie
import nl.hva.madlevel6task2.repository.MoviesRepository
import java.lang.IllegalArgumentException

class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val moviesRepository = MoviesRepository()
    val movies = moviesRepository.discover

    private val _errorText: MutableLiveData<String> = MutableLiveData()
    val errorText: LiveData<String>
        get() = _errorText

    fun getMovie(id: Int): Movie {
        if (movies.value == null) throw IllegalArgumentException("No movies available.")
        return movies.value!!.results.find { it.id == id }
            ?: throw IllegalArgumentException("No order found for id '$id'.")
    }

    fun getPopularMovies(year: String) {
        viewModelScope.launch {
            try {
                moviesRepository.getPopularMovies(year)
            } catch (error: MoviesRepository.MoviesRefreshError) {
                _errorText.value = error.message
                Log.e("Movies error", error.cause.toString())
            }
        }
    }
}