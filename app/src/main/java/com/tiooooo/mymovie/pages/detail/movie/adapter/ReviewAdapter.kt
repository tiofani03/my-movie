package com.tiooooo.mymovie.pages.detail.movie.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.tiooooo.data.movie.api.model.review.MovieReview
import com.tiooooo.mymovie.databinding.ItemReviewBinding

class ReviewAdapter(private val listCast: List<MovieReview>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bindItem(listCast[position])
    }

    override fun getItemCount(): Int = listCast.size


    class ReviewViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(review: MovieReview) {
            binding.apply {
                tvName.text = review.author
                tvReview.text = review.content
                binding.btnReadFull.isVisible = review.content.length >= 300
            }
        }
    }
}
