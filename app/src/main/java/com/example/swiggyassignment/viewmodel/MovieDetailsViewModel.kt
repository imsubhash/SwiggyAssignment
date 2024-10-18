package com.example.swiggyassignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiggyassignment.data.MovieDetails
import com.example.swiggyassignment.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> = _movieDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    movieRepository.getMovieDetails(movieId)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
            response?.let {
                _isLoading.value = false
                if (it.isSuccessful) {
                    val details = it.body()
                    _movieDetails.value = details!!
                } else {
                    val errorCode = it.code()
                    val errorMessage = it.message()
                    Log.d("Subhash ", "errorCode $errorCode, errorMessage $errorMessage")
                }
            }

        }
    }
}