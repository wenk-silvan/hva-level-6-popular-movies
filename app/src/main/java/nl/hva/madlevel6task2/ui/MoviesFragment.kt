package nl.hva.madlevel6task2.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.os.bundleOf
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

const val BUNDLE_MOVIE_ID = "bundle_movie_id"

class MoviesFragment : Fragment() {
    val viewModel: MoviesViewModel by activityViewModels()
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val movies: ArrayList<Movie> = arrayListOf()
    private val movieAdapter = MovieAdapter(movies, ::onMovieClicked)
    private var gridLayoutManager: GridLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Snackbar.make(binding.root, "Landscape Mode", Snackbar.LENGTH_SHORT).show()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Snackbar.make(binding.root, "Portrait Mode", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        binding.btnSubmit.setOnClickListener { onSubmitClicked() }
        observeMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onMovieClicked(movie: Movie) {
        findNavController().navigate(
            R.id.action_MoviesFragment_to_MovieDetailsFragment,
            bundleOf(Pair(BUNDLE_MOVIE_ID, movie.id))
        )
    }

    private fun initRecyclerViews() {
        gridLayoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvMovies.layoutManager = gridLayoutManager
        binding.rvMovies.adapter = movieAdapter
        binding.rvMovies.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.rvMovies.viewTreeObserver.removeOnGlobalLayoutListener(this)
                gridLayoutManager!!.spanCount = calculateSpanCount()
                gridLayoutManager!!.requestLayout()
            }
        })
    }

    private fun calculateSpanCount(): Int {
        val viewWidth = binding.rvMovies.measuredWidth
        val cardViewWidth = resources.getDimension(R.dimen.poster_width)
        val spanCount = floor((viewWidth / (cardViewWidth)).toDouble()).toInt()
        return if (spanCount >= 1) spanCount else 1
    }

    private fun observeMovies() {
        viewModel.movies.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = View.GONE
            movies.clear()
            movies.addAll(it.results)
            movieAdapter.notifyDataSetChanged()
        })

        viewModel.errorText.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = View.GONE
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun onSubmitClicked() {
        val input = binding.tilYear.editText?.text
        if (input.isNullOrBlank() || input.toString().toInt() < 1950 || input.toString()
                .toInt() > 2021
        ) {
            Snackbar.make(
                binding.root,
                "Can't load movies, invalid date picked.",
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getPopularMovies(binding.tilYear.editText?.text.toString())
    }
}