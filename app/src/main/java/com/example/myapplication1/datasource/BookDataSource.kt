package com.example.myapplication1.datasource

import android.util.Log
import com.example.myapplication1.BuildConfig
import com.example.myapplication1.domain.Book
import com.example.myapplication1.network.BookNetworkService
import com.example.myapplication1.network.NetworkBookContainer
import com.example.myapplication1.network.asDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class BookRemoteDataSource @Inject constructor(
    private val bookNetworkService: BookNetworkService,
    private val ioDispatcher: CoroutineDispatcher
) {
    fun getSearchResult(searchText: String, page: Int): Flow<List<Book>> = callbackFlow {
        bookNetworkService.getSearchBookList(
            BuildConfig.NAVER_CLIENT_ID,
            BuildConfig.NAVER_CLIENT_SECRET,
            page * 10,
            searchText
        ).enqueue( object : Callback<NetworkBookContainer> {
            override fun onResponse(
                call: Call<NetworkBookContainer>,
                response: Response<NetworkBookContainer>
            ) {
                Log.d("djgopw", "jg")
                if (response.isSuccessful) {
                    response.body()?.asDomainModel()?.let { trySend(it).isSuccess }
                }
            }

            override fun onFailure(call: Call<NetworkBookContainer>, t: Throwable) {
                Log.d("djgopw", "jgrg")
            }
        })
        awaitClose()
    }.flowOn(ioDispatcher)
}