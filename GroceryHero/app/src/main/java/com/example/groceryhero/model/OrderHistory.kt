data class OrderHistory(
    val count: Int = 0,
    val `data`: ArrayList<getData> = ArrayList(),
    val error: Boolean = true
)

data class getData(
    val __v: Int,
    val _id: String,
    val date: String,
    val orderSummary: getOrderSummary,
    val products: ArrayList<getProduct>,
    val shippingAddress: getShippingAddress,
    val user: getUser,
    val userId: String
)

data class getOrderSummary(
    val _id: String,
    val deliveryCharges: Int,
    val discount: Int,
    val orderAmount: Int,
    val ourPrice: Int
)

data class getProduct(
    val _id: String,
    val image: String,
    val mrp: Int,
    val price: Int,
    val quantity: Int
)

data class getShippingAddress(
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String
)

data class getUser(
    val _id: String,
    val email: String,
    val mobile: String
)