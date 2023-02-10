package com.example.myapplication1

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication1.databinding.ActivityMainBinding
import com.example.myapplication1.domain.Book
import dagger.hilt.android.AndroidEntryPoint
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

    companion object {
        object BookComparator : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }
}