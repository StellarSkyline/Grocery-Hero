package com.example.groceryhero.model

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("firstName")
    var name:String,
    var email:String,
    var password:String,
    var mobile:String
) {
    companion object{
        const val KEY_NAME = "name"
        const val KEY_EMAIL = "email"
        const val KEY_PASSWORD = "password"
        const val KEY_Mobile = "mobile"
    }
}