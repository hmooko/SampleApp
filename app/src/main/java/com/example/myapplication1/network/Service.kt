package com.example.myapplication1.network

import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BookNetworkService {
    @GET("search/book.json")
    fun getSearchBookList(
        @Header("X-Naver-Client-Id") id: String,
        @Header("X-Naver-Client-Secret") pw: String,
        @Query("display") display: Int,
        @Query("query") query: String
    ): Call<NetworkBookContainer>
}