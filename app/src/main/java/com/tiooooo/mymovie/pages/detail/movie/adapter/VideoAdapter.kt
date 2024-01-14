package com.tiooooo.mymovie.pages.detail.movie.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tiooooo.data.movie.api.model.video.MovieVideo
import com.tiooooo.mymovie.R
import com.tiooooo.mymovie.databinding.ItemVideoBinding

class VideoAdapter(private val listCast: List<MovieVideo>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    var onItemClick: ((url: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bindItem(listCast[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(listCast[position].key)
        }
    }

    override fun getItemCount(): Int = listCast.size

    class VideoViewHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(movieVideo: MovieVideo) {
            binding.ivThumbnail.load("https://img.youtube.com/vi/${movieVideo.key}/0.jpg") {
                placeholder(R.drawable.ic_image)
                error(R.drawable.ic_image)
                crossfade(true)
            }

        }
    }

}
