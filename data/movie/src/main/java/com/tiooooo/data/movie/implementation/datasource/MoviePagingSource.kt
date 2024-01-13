package com.tiooooo.data.movie.implementation.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tiooooo.data.movie.api.model.list.MovieResult
import com.tiooooo.data.movie.implementation.remote.api.MovieApi
import com.tiooooo.data.movie.implementation.remote.response.list.mapToMovieResult

class MoviePagingSource(
    private val movieApi: MovieApi,
    private val type: String,
) : PagingSource<Int, MovieResult>() {
    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val res = movieApi.getMovies(type, position)
            LoadResult.Page(
                data = res.data!!.map {
                    it.mapToMovieResult()
                },
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (res.data.isNullOrEmpty()) null else position + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResult>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
