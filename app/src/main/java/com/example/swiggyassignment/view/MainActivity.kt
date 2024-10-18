package com.example.swiggyassignment.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swiggyassignment.adapter.MoviePagingAdapter
import com.example.swiggyassignment.databinding.ActivityMainBinding
import com.example.swiggyassignment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var moviePagingAdapter: MoviePagingAdapter
    private lateinit var mainViewModel: MainViewModel
    private var searchJob: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupAdapter()
        setupLiveData()
        setupSearch()
    }

    private fun setupSearch() {
        binding.input.addTextChangedListener { editable ->
            binding.progressbar.isVisible = true
            binding.recyclerView.isVisible = false
            if (searchJob?.isActive == true) {
                searchJob?.cancel()
            }
            searchJob = lifecycleScope.launch {
                delay(1000)
                val query = editable.toString().trim()
                binding.recyclerView.isVisible = query.isNotBlank()
                mainViewModel.setSearchTerm(query)
            }
        }
    }

    private fun setupAdapter() {
        moviePagingAdapter = MoviePagingAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = moviePagingAdapter
        }
    }

    private fun setupLiveData() {
        mainViewModel.searchResults.observe(this) { pagingData ->
            lifecycleScope.launch {
                binding.progressbar.isVisible = false
                moviePagingAdapter.submitData(pagingData)
            }
        }
    }
}