package com.tiooooo.mymovie.pages.detail.movie.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.tiooooo.core.constant.Constant
import com.tiooooo.data.movie.api.model.casts.Cast
import com.tiooooo.mymovie.R
import com.tiooooo.mymovie.databinding.ItemCastsBinding

class CastAdapter(private val listCast: List<Cast>) :
    RecyclerView.Adapter<CastAdapter.CastsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastsViewHolder {
        return CastsViewHolder(
            ItemCastsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CastsViewHolder, position: Int) {
        holder.bindItem(listCast[position])
    }

    override fun getItemCount(): Int = listCast.size

    class CastsViewHolder(val binding: ItemCastsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(cast: Cast) {
            binding.tvItemName.text = cast.name
            if (cast.profilePath.isEmpty()) {
                when (cast.gender) {
                    1 -> binding.imgItemPhoto.load(R.drawable.ava_1)
                    2 -> binding.imgItemPhoto.load(R.drawable.ava_0)
                    else -> binding.imgItemPhoto.load(R.drawable.ava_0)
                }
            } else {
                binding.imgItemPhoto.load(Constant.BASE_IMAGE_500 + cast.profilePath) {
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_image)
                    error(R.drawable.ic_image)
                    crossfade(true)
                }
            }
        }
    }

}
