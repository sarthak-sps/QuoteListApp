package com.example.quotelistapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.quotelistapp.api.RetrofitService
import com.example.quotelistapp.models.NETWORK_PAGE_SIZE
import com.example.quotelistapp.models.QuoteList

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllQuotes(): LiveData<PagingData<com.example.quotelistapp.models.Result>> {

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                QuotePagingSource(retrofitService)
            }
            , initialKey = 1
        ).liveData
    }

}