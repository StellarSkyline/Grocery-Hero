package com.example.groceryhero.model

data class OrderSummary(
    var totalPrice:Double,
    var totalMRP:Double,
    var totalDiscount:Double,
    var totalQuantity:Int,
    var checkDeliver:Boolean
)