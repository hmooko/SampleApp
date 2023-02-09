package com.example.myapplication1

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.domain.Book
import com.example.myapplication1.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    var bookList: List<Book> = listOf()
)

@HiltViewModel
class MainViewModel @Inject constructor(
    val bookRepository: BookRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun searchBooks(searchText: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(bookList = bookRepository.getSearchBookList(searchText))
            }
        }
    }
}