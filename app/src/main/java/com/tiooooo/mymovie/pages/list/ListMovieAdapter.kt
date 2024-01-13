package com.tiooooo.mymovie.pages.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tiooooo.core.constant.Constant
import com.tiooooo.data.movie.api.model.list.MovieResult
import com.tiooooo.mymovie.databinding.ItemPosterFullBinding

class ListMovieAdapter : PagingDataAdapter<MovieResult, ListMovieAdapter.ViewHolder>(Companion) {
    companion object : DiffUtil.ItemCallback<MovieResult>() {
        override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            return oldItem == newItem
        }
    }

    var onItemClick: ((movieId: String) -> Unit)? = null

    override
    fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindItem(it)
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(getItem(position)?.id.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPosterFullBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    class ViewHolder(
        private val binding: ItemPosterFullBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(movieResult: MovieResult) {
            binding.apply {
                ivPoster.load(Constant.BASE_IMAGE_500 + movieResult.posterPath)
                tvTitle.text = movieResult.title
                tvRating.text = movieResult.voteAverage.toString()
            }
        }
    }
}
