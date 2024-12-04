package com.example.equipouno.ServicesWeb

class ApiUtils {
    companion object{
        fun getApiService():ServiceAPI{
            return RetrofitManagement.getRetrofit().create(ServiceAPI::class.java)
        }
    }
}