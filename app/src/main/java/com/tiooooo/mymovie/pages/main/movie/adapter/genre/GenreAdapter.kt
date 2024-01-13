package com.tiooooo.mymovie.pages.main.movie.adapter.genre

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.tiooooo.data.movie.api.model.Genre
import com.tiooooo.mymovie.databinding.ItemGenreBinding
import com.tiooooo.mymovie.pages.main.movie.listener.GenreListener

class GenreAdapter(
    private val handleGenreListener: GenreListener,
) : Adapter<GenreAdapter.ViewHolder>() {
    private val list: MutableList<Genre> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Genre>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
        holder.itemView.setOnClickListener {
            val genres: ArrayList<Genre> = arrayListOf()
            genres.addAll(list)
            handleGenreListener.onClick(genres, position)
        }
    }

    class ViewHolder(
        private val binding: ItemGenreBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(genre: Genre) {
            binding.tvGenre.text = genre.name
        }
    }
}
