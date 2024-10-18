package com.example.swiggyassignment.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.swiggyassignment.R
import com.example.swiggyassignment.databinding.ActivityMovieDetailsBinding
import com.example.swiggyassignment.util.Constants.MOVIE_ID
import com.example.swiggyassignment.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getStringExtra(MOVIE_ID)?.let { movieDetailsViewModel.getMovieDetails(it) }
        setupObserver()
    }

    private fun setupObserver() {
        movieDetailsViewModel.isLoading.observe(this) {
            binding.progressbar.isVisible = it
        }
        movieDetailsViewModel.movieDetails.observe(this) { movieDetails ->
            binding.apply {
                Glide.with(this@MovieDetailsActivity)
                    .load(movieDetails.Poster)
                    .placeholder(R.drawable.placeholder_loading)
                    .error(R.drawable.broken_image)
                    .into(poster)

                title.text = movieDetails.Title
                description.text = movieDetails.Plot
                imdb.text = "IMDB: ${movieDetails.imdbRating}"
            }

        }
    }
}