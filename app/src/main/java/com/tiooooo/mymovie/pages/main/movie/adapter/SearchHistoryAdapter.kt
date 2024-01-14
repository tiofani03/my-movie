package com.tiooooo.mymovie.pages.main.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tiooooo.data.movie.implementation.local.entity.SearchHistoryEntity
import com.tiooooo.mymovie.databinding.ItemSearchHistoryBinding

class SearchHistoryAdapter : RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder>() {

    private val historyList: MutableList<SearchHistoryEntity> = mutableListOf()

    var onClicked: ((SearchHistoryEntity) -> Unit)? = null
    var onDeleteClicked: ((SearchHistoryEntity) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<SearchHistoryEntity>) {
        historyList.clear()
        historyList.addAll(list)
        notifyDataSetChanged()
    }


    class ViewHolder(binding: ItemSearchHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val viewBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val search = historyList[position]
        holder.viewBinding.tvSearchHistory.text = search.query
        holder.itemView.setOnClickListener {
            onClicked?.invoke(search)
        }
        holder.viewBinding.ivClear.setOnClickListener {
            onDeleteClicked?.invoke(search)
        }
    }
}
