package com.tiooooo.mymovie.pages.main.movie.adapter.genre

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tiooooo.data.movie.api.model.Genre
import com.tiooooo.mymovie.databinding.ContainerGenreBinding
import com.tiooooo.mymovie.pages.main.movie.listener.GenreListener

class GenreContainerAdapter(
    private val handleGenreListener: GenreListener,
) : Adapter<GenreContainerAdapter.ViewHolder>() {
    private val list: MutableList<Genre> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Genre>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContainerGenreBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list, handleGenreListener)
    }

    class ViewHolder(
        private val binding: ContainerGenreBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(list: MutableList<Genre>, handleGenreListener: GenreListener) {
            if (list.isNotEmpty()) {
                val spanCount = 3
                val staggeredGridLayoutManager =
                    StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL)

                binding.rvGenre.apply {
                    layoutManager = staggeredGridLayoutManager
                    adapter = GenreAdapter(handleGenreListener).apply {
                        setData(list)
                    }
                }
            }
        }
    }
}
