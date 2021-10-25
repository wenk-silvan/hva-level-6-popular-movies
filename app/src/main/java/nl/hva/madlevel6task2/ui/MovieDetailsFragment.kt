package nl.hva.madlevel6task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import nl.hva.madlevel6task2.R
import nl.hva.madlevel6task2.databinding.FragmentMovieDetailsBinding
import nl.hva.madlevel6task2.model.Movie
import nl.hva.madlevel6task2.vm.MoviesViewModel

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesViewModel by activityViewModels()
    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = requireArguments().getInt(BUNDLE_MOVIE_ID, 0)
        movie = viewModel.getMovie(movieId)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        binding.tvTitle.text = movie.title
        binding.tvReleaseDate.text = movie.release_date
        binding.tvRating.text = movie.vote_average.toString()
        binding.tvOverview.text = movie.overview
        Glide.with(this).load(movie.getBackdropUrl()).into(binding.ivBackdrop)
        Glide.with(this).load(movie.getPosterUrl()).into(binding.ivPoster)
    }
}