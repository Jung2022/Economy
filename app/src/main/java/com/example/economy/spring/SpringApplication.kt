package com.example.economy.spring

import android.app.Application
import com.example.economy.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SpringApplication:Application(){
    var networkService:INetworkService
    val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl("http://192.168.0.2:8085/") //ip주소
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    init {
        networkService = retrofit.create(INetworkService::class.java)
    }
}