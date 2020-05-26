package com.example.groceryhero.app

import java.net.URL

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

        fun addAddress():String {
            val URL_GET_ADDRESS = "address"
            return "${Config.BASE_URL + URL_GET_ADDRESS}"
        }
        fun getAddress():String {
            val URL_GET_ADDRESS = "address/"
            return "${Config.BASE_URL + URL_GET_ADDRESS}"
        }

        fun postOrder():String {
            val URL_POST_ORDER = "orders"

            return "${Config.BASE_URL + URL_POST_ORDER}"
        }

        fun getOrder():String {
            val URL_GET_ORDER = "orders/"
            return "${Config.BASE_URL+URL_GET_ORDER}"
        }

        fun getSearch():String {
            val URL_GET_SEARCH = "products/search/"
            return "${Config.BASE_URL+URL_GET_SEARCH}"
        }
    }
}