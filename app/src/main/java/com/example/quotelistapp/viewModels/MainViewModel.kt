package com.example.quotelistapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.quotelistapp.repository.MainRepository

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {


    fun getQuoteList(): LiveData<PagingData<com.example.quotelistapp.models.Result>> {
        return mainRepository.getAllQuotes().cachedIn(viewModelScope)
    }

}