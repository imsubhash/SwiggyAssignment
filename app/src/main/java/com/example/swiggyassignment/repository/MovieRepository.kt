package com.example.swiggyassignment.repository

import com.example.swiggyassignment.api.ApiService
import com.example.swiggyassignment.util.Constants.API_KEY
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getMovies(searchTerm: String, page: Int) = apiService.getMovieList(API_KEY, searchTerm, page)


    suspend fun getMovieDetails(id: String) = apiService.getMovieDetails(id, API_KEY)

}