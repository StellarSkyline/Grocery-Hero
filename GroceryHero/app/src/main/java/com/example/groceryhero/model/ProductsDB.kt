package com.example.groceryhero.model

import java.io.Serializable

data class ProductsDB(
    var name:String,
    var price:String,
    var image:String,
    var quantity:Int,
    var mrp:Double
):Serializable