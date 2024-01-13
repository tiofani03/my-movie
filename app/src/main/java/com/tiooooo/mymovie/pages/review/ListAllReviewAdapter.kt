package com.tiooooo.mymovie.pages.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tiooooo.data.movie.api.model.review.MovieReview
import com.tiooooo.mymovie.databinding.ItemReviewBinding

class ListAllReviewAdapter :
    PagingDataAdapter<MovieReview, ListAllReviewAdapter.ViewHolder>(Companion) {
    companion object : DiffUtil.ItemCallback<MovieReview>() {
        override fun areItemsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
            return oldItem == newItem
        }
    }

    var onItemClick: ((url: String) -> Unit)? = null

    override
    fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindItem(it)
            holder.itemBinding.btnReadFull.setOnClickListener {
                onItemClick?.invoke(getItem(position)?.url.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    class ViewHolder(
        private val binding: ItemReviewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        val itemBinding = binding
        fun bindItem(review: MovieReview) {
            binding.apply {
                tvName.text = review.author
                tvReview.text = review.content
                binding.btnReadFull.isVisible = review.content.length >= 300
            }
        }
    }
}
