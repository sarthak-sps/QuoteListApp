package com.example.quotelistapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.quotelistapp.api.RetrofitService
import com.example.quotelistapp.models.QuoteList
import java.io.IOException
import java.lang.Exception


private const val STARTING_PAGE_INDEX = 1


class QuotePagingSource(private val apiService: RetrofitService): PagingSource<Int, com.example.quotelistapp.models.Result>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.example.quotelistapp.models.Result> {

        return try {
            val position = params.key ?: 1
            val response = apiService.getQuote(position)
            LoadResult.Page(data = response.body()!!.results, prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, com.example.quotelistapp.models.Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
