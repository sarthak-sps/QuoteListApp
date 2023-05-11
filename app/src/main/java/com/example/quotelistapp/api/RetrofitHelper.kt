package com.example.quotelistapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val base_Url="http://quotable.io/"

    fun getInstance():Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}