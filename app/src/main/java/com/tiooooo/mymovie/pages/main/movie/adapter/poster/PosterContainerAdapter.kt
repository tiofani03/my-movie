package com.tiooooo.mymovie.pages.main.movie.adapter.poster

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.tiooooo.data.movie.api.model.list.MovieResult
import com.tiooooo.mymovie.databinding.ContainerPosterBinding
import com.tiooooo.mymovie.pages.main.movie.listener.PosterListener

class PosterContainerAdapter(
    private val title: String,
    private val type: String,
    private val handlePosterListener: PosterListener,
) : Adapter<PosterContainerAdapter.ViewHolder>() {
    private val list: MutableList<MovieResult> = mutableListOf()
    private var isLoading: Boolean = false

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<MovieResult>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setLoading(isLoading: Boolean = false){
        this.isLoading = isLoading
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContainerPosterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list, title, type, handlePosterListener, isLoading)
    }

    class ViewHolder(
        private val binding: ContainerPosterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(
            list: MutableList<MovieResult>,
            title: String,
            type: String,
            handlePosterListener: PosterListener,
            isLoading: Boolean,
        ) {
            binding.ivDetail.setOnClickListener {
                handlePosterListener.onClickSeeAll(title = title, type = type)
            }

            binding.tvTitle.text = title
            binding.rvPoster.isVisible = !isLoading
            binding.shimmerPoster.isVisible = isLoading
            if (list.isNotEmpty()) {
                binding.rvPoster.apply {
                    layoutManager =
                        LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = PosterAdapter(handlePosterListener).apply {
                        setData(list)
                    }
                }
            }
        }
    }
}
