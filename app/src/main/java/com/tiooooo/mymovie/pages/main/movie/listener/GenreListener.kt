package com.tiooooo.mymovie.pages.main.movie.listener

import com.tiooooo.data.movie.api.model.Genre

interface GenreListener {
    fun onClick(list: ArrayList<Genre>, position: Int)
}
