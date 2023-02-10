package com.example.myapplication1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication1.databinding.MainRecyclerItemBinding
import com.example.myapplication1.domain.Book

class MainRecyclerAdapter(diffCallback: DiffUtil.ItemCallback<Book>) :
    PagingDataAdapter<Book, MainRecyclerAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(val binding: MainRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(MainRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val item = getItem(position)

        Glide.with(binding.imageView)
            .load(item?.image)
            .into(binding.imageView)

        binding.textView.text = item?.title
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}