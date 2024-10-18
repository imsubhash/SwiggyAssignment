package com.example.swiggyassignment.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.swiggyassignment.data.Search
import com.example.swiggyassignment.repository.MovieRepository
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val movieRepository: MovieRepository,
    private val searchTerm: String
) : PagingSource<Int, Search>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        return try {
            val position = params.key ?: 1
            val response = movieRepository.getMovies(searchTerm, position)
            LoadResult.Page(
                data = response.body()?.Search!!,
                prevKey = if (position == 1) null else position.minus(1),
                nextKey = if (position == response.body()?.totalResults?.toInt()) null else position.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}