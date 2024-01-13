package com.tiooooo.mymovie.pages.main.movie.adapter.poster

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import coil.load
import com.tiooooo.data.movie.api.model.MovieResult
import com.tiooooo.mymovie.databinding.ItemPosterBinding
import com.tiooooo.mymovie.pages.main.movie.listener.PosterListener

class PosterAdapter(
    private val handlePosterListener: PosterListener,
) : Adapter<PosterAdapter.ViewHolder>() {
    private val list: MutableList<MovieResult> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<MovieResult>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
        holder.itemView.setOnClickListener {
            handlePosterListener.onClickDetail(list[position].id)
        }
    }

    class ViewHolder(
        private val binding: ItemPosterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(movieResult: MovieResult) {
            binding.apply {
                ivPoster.load("https://image.tmdb.org/t/p/w500/" + movieResult.posterPath)
                tvTitle.text = movieResult.title
                tvRating.text = movieResult.voteAverage.toString()
            }
        }
    }
}
