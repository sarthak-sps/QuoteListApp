package com.example.quotelistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.quotelistapp.api.RetrofitService
import com.example.quotelistapp.api.RetrofitHelper
import com.example.quotelistapp.repository.MainRepository
import com.example.quotelistapp.viewModels.MainViewModel

import com.example.quotelistapp.viewModels.MyViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var progressbar:ProgressBar
    var adapter = QuotePagingAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofitService = RetrofitHelper.getInstance().create(RetrofitService::class.java)
        var recyclerView: RecyclerView = findViewById(R.id.recycler)
        progressbar=findViewById(R.id.progressbar)
        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        val mainRepository = MainRepository(retrofitService)
        mainViewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        ).get(MainViewModel::class.java)
        recyclerView.adapter = adapter
adapter.addLoadStateListener { loadState ->

    if (loadState.refresh is LoadState.Loading ||
        loadState.append is LoadState.Loading)
        progressbar.isVisible = true
    else {
        progressbar.isVisible = false
        // If we have an error, show a toast
        val errorState = when {
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            else -> null
        }
        errorState?.let {
            Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
        }

    }

}
        lifecycleScope.launch {
            mainViewModel.getQuoteList().observe(this@MainActivity) {
                adapter.submitData(lifecycle, it)
                Log.d("MyQuotes", it.toString())
            }
        }
    }
}
