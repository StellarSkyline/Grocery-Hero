package com.example.groceryhero.model

import com.example.groceryhero.helper.SessionManager

var mSession = SessionManager()

data class AddressData(
    val count: Int = 0,
    val `data`: ArrayList<AddData> = ArrayList(),
    val error: Boolean = false
)

data class AddData(
    val __v: Int = 0,
    val _id: String = "0",
    val city: String,
    val houseNo: String,
    val location: String,
    val mobile: String = mSession.getMobile(),
    val name: String = mSession.getName(),
    val pincode: Int,
    val streetName: String,
    val type: String,
    val userId: String = mSession.getId()
)