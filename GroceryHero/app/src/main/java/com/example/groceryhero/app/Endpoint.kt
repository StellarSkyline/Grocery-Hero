package com.example.groceryhero.app

class Endpoint {

    companion object{

        fun getCategory(): String {
            val URL_CATEGORY = "category"

            return "${Config.BASE_URL}${URL_CATEGORY}"
        }

        fun getSubCategory():String {
            val URL_SUB_CATEGORY = "subcategory/"

            return "${Config.BASE_URL + URL_SUB_CATEGORY}"
        }

        fun getProducts():String {
            val URL_PRODUCTS = "products/sub/"

            return "${Config.BASE_URL + URL_PRODUCTS}"
        }

        fun getRegister():String {
            val URL_REGISTER = "auth/register"
            return "${Config.LOGIN_URL + URL_REGISTER}"
        }

        fun getLogin():String {
            val URL_LOGIN = "auth/login"
            return "${Config.LOGIN_URL + URL_LOGIN}"
        }
    }
}