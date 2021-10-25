package nl.hva.madlevel6task2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import nl.hva.madlevel6task2.R
import nl.hva.madlevel6task2.adapter.MovieAdapter
import nl.hva.madlevel6task2.databinding.FragmentMoviesBinding
import nl.hva.madlevel6task2.model.Movie
import nl.hva.madlevel6task2.vm.MoviesViewModel
import kotlin.math.floor

class MoviesFragment : Fragment() {
    val viewModel: MoviesViewModel by activityViewModels()
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val movies: ArrayList<Movie> = arrayListOf()
    private val movieAdapter = MovieAdapter(movies, ::onMovieClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        binding.btnSubmit.setOnClickListener {
            viewModel.getPopularMovies(binding.tilYear.editText?.text.toString())
        }
        observeMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onMovieClick(movie: Movie) {
        Snackbar.make(
            binding.rvMovies,
            "You clicked the movie: ${movie.title}",
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    private fun initRecyclerViews() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        binding.rvMovies.layoutManager = gridLayoutManager
        binding.rvMovies.adapter = movieAdapter
//        binding.rvMovies.viewTreeObserver.addOnGlobalLayoutListener(object :
//            ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                binding.rvMovies.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                gridLayoutManager.spanCount = calculateSpanCount()
//                gridLayoutManager.requestLayout()
//            }
//        })
    }

    private fun calculateSpanCount(): Int {
        val viewWidth = binding.rvMovies.measuredWidth
        val cardViewWidth = resources.getDimension(R.dimen.poster_width)
        val spanCount = floor((viewWidth / (cardViewWidth)).toDouble()).toInt()
        return if (spanCount >= 1) spanCount else 1
    }

    private fun observeMovies() {
        viewModel.movies.observe(viewLifecycleOwner, {
            Snackbar.make(binding.root, "Fetched movies", Snackbar.LENGTH_SHORT).show()
            Log.i("Movies", it.totalResults.toString())
            movies.clear()
            movies.addAll(it.results)
            movieAdapter.notifyDataSetChanged()
        })

        viewModel.errorText.observe(viewLifecycleOwner, {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })
    }
}