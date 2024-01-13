package com.tiooooo.mymovie.pages.main.movie.listener

interface PosterListener {
    fun onClickSeeAll(title: String, type: String)
    fun onClickDetail(movieId: Long)
}
