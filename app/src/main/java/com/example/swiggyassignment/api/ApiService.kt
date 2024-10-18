package com.example.swiggyassignment.api

import com.example.swiggyassignment.data.MovieDetails
import com.example.swiggyassignment.data.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("?")
    suspend fun getMovieList(
        @Query("apikey") apikey: String,
        @Query("s") searchTerm: String,
        @Query("page") page: Int
    ): Response<MovieList>

    @GET("?")
    suspend fun getMovieDetails(
        @Query("i") id: String,
        @Query("apikey") apikey: String,
    ): Response<MovieDetails>

}