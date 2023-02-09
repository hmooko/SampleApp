package com.example.myapplication1.datasource

import android.util.Log
import com.example.myapplication1.BuildConfig
import com.example.myapplication1.domain.Book
import com.example.myapplication1.network.BookNetworkService
import com.example.myapplication1.network.NetworkBookContainer
import com.example.myapplication1.network.asDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class BookRemoteDataSource @Inject constructor(
    private val bookNetworkService: BookNetworkService,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getSearchResult(searchText: String): List<Book> {
        var bookList: List<Book> = listOf()

        withContext(ioDispatcher) {
            bookNetworkService.getSearchBookList(
                BuildConfig.NAVER_CLIENT_ID,
                BuildConfig.NAVER_CLIENT_SECRET,
                100,
                searchText
            ).enqueue( object : Callback<NetworkBookContainer> {
                override fun onResponse(
                    call: Call<NetworkBookContainer>,
                    response: Response<NetworkBookContainer>
                ) {
                    bookList = response.body()!!.asDomainModel()
                    Log.d("BookRemoteDataSource", bookList.toString())
                }

                override fun onFailure(call: Call<NetworkBookContainer>, t: Throwable) {
                    Log.d("BookRemoteDataSource", "fail")
                }
            })

        }
        Log.d("BookRemoteDataSource", bookList.toString())
        return bookList
    }
}