package com.tiooooo.mymovie.pages.main.movie.adapter.poster

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import coil.load
import com.tiooooo.core.constant.Constant
import com.tiooooo.data.movie.api.model.list.MovieResult
import com.tiooooo.mymovie.R
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
                ivPoster.load(Constant.BASE_IMAGE_500 + movieResult.posterPath) {
                    placeholder(R.drawable.ic_image)
                    error(R.drawable.ic_image)
                    crossfade(true)
                }
                tvTitle.text = movieResult.title
                tvRating.text = movieResult.voteAverage.toString()
            }
        }
    }
}
