package com.example.myapplication1.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.myapplication1.datasource.MainPagingSource
import com.example.myapplication1.network.BookNetworkService
import javax.inject.Inject

class BookRepository @Inject constructor(
    val bookNetworkService: BookNetworkService
) {
    fun getSearchBookList(query: String) = Pager(PagingConfig(pageSize = 1)) {
        MainPagingSource(bookNetworkService, query)
    }.flow
}