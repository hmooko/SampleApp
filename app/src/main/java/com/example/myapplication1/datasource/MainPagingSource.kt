package com.example.myapplication1.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication1.BuildConfig
import com.example.myapplication1.domain.Book
import com.example.myapplication1.network.BookNetworkService
import com.example.myapplication1.network.asDomainModel
import retrofit2.awaitResponse
import javax.inject.Inject

class MainPagingSource @Inject constructor(
    val bookNetworkService: BookNetworkService,
    val query: String
) : PagingSource<Int, Book>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val nextPageNumber = params.key ?: 1

        val response: List<Book> = bookNetworkService.getSearchBookList(
                BuildConfig.NAVER_CLIENT_ID,
                BuildConfig.NAVER_CLIENT_SECRET,
                nextPageNumber * 10 - 9,
                query
            ).awaitResponse().body()?.asDomainModel() ?: listOf()

        return LoadResult.Page(
            data = response,
            prevKey = null,
            nextKey = if (nextPageNumber > 99) null else nextPageNumber.plus(1)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? = null
}