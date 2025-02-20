package com.example.myvktestxml.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myvktestxml.databinding.VideoItemBinding
import com.example.myvktestxml.domain.models.VideoEntity

class MainAdapter(private val onClickItem: (item: VideoEntity) -> Unit) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var videoList: MutableList<VideoEntity> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MainViewHolder {
        val binding = VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MainViewHolder, position: Int
    ) {
        val item = videoList[position]
        holder.bind(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<VideoEntity>) {
        val diffCallback = VideoDiffCallback(videoList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        videoList.clear()
        videoList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = videoList.size

    inner class MainViewHolder(val binding: VideoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun bind(item: VideoEntity) {
            binding.apply {
                videoItemViewName.text = item.title
                Glide.with(itemView.context).load(item.thumbnail).circleCrop()
                    .into(videoItemViewImage)
                videoItemViewDuration.text = item.description

                root.setOnClickListener { onClickItem(item) }
            }
        }
    }

    class VideoDiffCallback(
        private val oldList: List<VideoEntity>, private val newList: List<VideoEntity>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(
            oldItemPosition: Int, newItemPosition: Int
        ): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(
            oldItemPosition: Int, newItemPosition: Int
        ): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}