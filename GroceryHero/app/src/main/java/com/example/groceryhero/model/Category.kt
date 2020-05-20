package com.example.groceryhero.model

import java.io.Serializable

data class Category(
    val count: Int = 0,
    val `data`: List<Data> = emptyList(),
    val error: Boolean = true
)

data class Data(
    val __v: Int,
    val _id: String,
    val catDescription: String,
    val catId: Int,
    val catImage: String,
    val catName: String,
    val position: Int,
    val slug: String,
    val status: Boolean
):Serializable