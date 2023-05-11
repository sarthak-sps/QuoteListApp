package com.example.quotelistapp.api

import com.example.quotelistapp.models.QuoteList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("/quotes")
    suspend fun getQuote(@Query("page") page:Int):Response<QuoteList>

    // base url + /quotes+ ?page=1


}