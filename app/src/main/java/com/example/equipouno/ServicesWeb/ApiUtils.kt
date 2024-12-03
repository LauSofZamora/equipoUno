package com.example.equipouno.ServicesWeb

import com.example.equipouno.ServicesWeb.ServiceAPI

class ApiUtils {
    companion object{
        fun getApiService():ServiceAPI{
            return RetrofitManagement.getRetrofit().create(ServiceAPI::class.java)
        }
    }
}