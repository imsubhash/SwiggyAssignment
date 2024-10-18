package com.example.swiggyassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.swiggyassignment.paging.MoviePagingSource
import com.example.swiggyassignment.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val _searchTerm = MutableLiveData<String>()
    private val searchTerm: LiveData<String> = _searchTerm

    fun setSearchTerm(term: String) {
        _searchTerm.value = term
    }

    val searchResults = searchTerm.switchMap { query ->
        Pager(
            config = PagingConfig(pageSize = 30, maxSize = 100),
            pagingSourceFactory = { MoviePagingSource(movieRepository, query) }
        ).liveData.cachedIn(viewModelScope)
    }
}