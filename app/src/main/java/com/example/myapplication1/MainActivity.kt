package com.example.myapplication1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication1.databinding.ActivityMainBinding
import com.example.myapplication1.databinding.MainRecyclerItemBinding
import com.example.myapplication1.domain.Book
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val viewModel: MainViewModel by viewModels()

        binding.mainRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.mainRecycler.adapter = MainRecyclerAdapter(BookComparator)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { currentUiState ->
                    currentUiState.pagingData?.let {
                        (binding.mainRecycler.adapter!! as MainRecyclerAdapter).submitData(it)
                    }
                }
            }
        }

        binding.imageButton.setOnClickListener {
            viewModel.searchBooks(binding.searchBar.text.toString())
        }

        setContentView(binding.root)
    }
}

class MainRecyclerAdapter(diffCallback: DiffUtil.ItemCallback<Book>) :
    PagingDataAdapter<Book, MainRecyclerAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(val binding: MainRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder(MainRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val item = getItem(position)
        Log.d(position.toString(), item?.title.toString())

        Glide.with(binding.imageView)
            .load(item?.image)
            .into(binding.imageView)

        binding.textView.text = item?.title
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}

object BookComparator : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }
}