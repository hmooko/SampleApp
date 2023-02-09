package com.example.myapplication1.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.myapplication1.datasource.BookRemoteDataSource
import com.example.myapplication1.datasource.MainPagingSource
import com.example.myapplication1.domain.Book
import com.example.myapplication1.network.BookNetworkService
import javax.inject.Inject

class BookRepository @Inject constructor(
    val bookNetworkService: BookNetworkService
    //private val bookRemoteDataSource: BookRemoteDataSource
) {
    /*fun getSearchBookList(searchText: String) =
        bookRemoteDataSource.getSearchResult(searchText)*/
    fun getSearchBookList(query: String) = Pager(PagingConfig(pageSize = 1)) {
        MainPagingSource(bookNetworkService, query)
    }.flow
}