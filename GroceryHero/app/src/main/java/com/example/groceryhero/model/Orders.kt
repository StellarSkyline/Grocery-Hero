package com.example.groceryhero.model

data class Orders(
    val __v: Int? = null,
    val _id: String? = null,
    val date: String? = null,
    val orderStatus: String? = null,
    val orderSummary: Summary,
    val payment: Payment? = null,
    val products: ArrayList<ProductsDB>,
    val shippingAddress: ShippingAddress,
    val user: Users,
    val userId: String
)

data class Summary(
    var _id: String? = null,
    var deliveryCharges: Int,
    var discount: Double,
    var orderAmount: Int,
    var ourPrice: Double,
    var totalAmount:Int? = null
)

data class Payment(
    val _id: String,
    val paymentMode: String,
    val paymentStatus: String
)

data class Product(
    val _id: String,
    val image: String,
    val mrp: Double,
    val price: Double,
    val quantity: Int
)

data class ShippingAddress(
    var city: String,
    var houseNo: String,
    var pincode: Int,
    var streetName: String
)

data class User(
    val _id: String,
    val email: String,
    val mobile: String,
    val name: String
)