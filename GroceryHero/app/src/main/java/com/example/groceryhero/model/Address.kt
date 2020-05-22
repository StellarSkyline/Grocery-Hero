package com.example.groceryhero.model

import com.example.groceryhero.helper.SessionManager
import com.google.gson.annotations.SerializedName

var item = SessionManager()

data class Address(
    val city: String,
    val houseNo: String,
    @SerializedName("location")
    val country: String,
    val mobile:String = item.getMobile(),
    val name: String = item.getName(),
    @SerializedName("pincode")
    val zipcode: String,
    val streetName: String,
    val type: String,
    val userId:String = item.getId()
)