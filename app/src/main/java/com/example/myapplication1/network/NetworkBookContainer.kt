package com.example.myapplication1.network

import com.example.myapplication1.domain.Book

data class NetworkBook(
    val title: String,
    val image: String
)

data class NetworkBookContainer(val items: List<NetworkBook>)

fun NetworkBookContainer.asDomainModel(): List<Book> {
    return items.map {
        Book(
            title = it.title,
            image = it.image
        )
    }
}
