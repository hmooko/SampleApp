package com.example.myapplication1.repository

import android.util.Log
import com.example.myapplication1.datasource.BookRemoteDataSource
import com.example.myapplication1.domain.Book
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookRemoteDataSource: BookRemoteDataSource
) {
    suspend fun getSearchBookList(
        searchText: String
    ): List<Book> {
        val bookList = bookRemoteDataSource.getSearchResult(searchText)
        Log.d("BookRepository", bookList.toString())
        return bookList
    }
}