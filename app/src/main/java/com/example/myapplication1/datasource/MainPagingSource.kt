package com.example.myapplication1.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication1.BuildConfig
import com.example.myapplication1.domain.Book
import com.example.myapplication1.network.BookNetworkService
import com.example.myapplication1.network.NetworkBookContainer
import com.example.myapplication1.network.asDomainModel
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
                nextPageNumber * 1,
                query
            ).awaitResponse().body()?.asDomainModel() ?: listOf()

        Log.d("gk", response.toString())

        return LoadResult.Page(
            data = response,
            prevKey = null,
            nextKey = if (response.size > 90) null else nextPageNumber.plus(1)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        /*return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }*/
        return null
    }
}