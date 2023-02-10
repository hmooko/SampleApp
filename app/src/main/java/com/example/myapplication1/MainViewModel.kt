package com.example.myapplication1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication1.domain.Book
import com.example.myapplication1.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    var pagingData: PagingData<Book>? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    val bookRepository: BookRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun searchBooks(query: String) {
        viewModelScope.launch {
            bookRepository.getSearchBookList(query)
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _uiState.update {
                        it.copy(pagingData = pagingData)
                    }
                }
        }
    }
}