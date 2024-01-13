package com.tiooooo.mymovie.pages.detail.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tiooooo.mymovie.databinding.ItemGenreDetailBinding

class GenreDetailAdapter(private val genres: List<String>) :
    RecyclerView.Adapter<GenreDetailAdapter.GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            ItemGenreDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindItem(genres[position])
    }

    override fun getItemCount(): Int = genres.size


    class GenreViewHolder(val binding: ItemGenreDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(genre: String) {
            binding.tvItemName.text = genre
        }
    }

}
