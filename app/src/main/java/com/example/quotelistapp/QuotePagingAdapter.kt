package com.example.quotelistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class QuotePagingAdapter: PagingDataAdapter<com.example.quotelistapp.models.Result, QuotePagingAdapter.MovieViewHolder>(MovieComparator) {
    override fun onBindViewHolder(holder: QuotePagingAdapter.MovieViewHolder, position: Int) {
        val quote= getItem(position)
        holder.quoteText.text = quote?.content
        holder.author.text=quote?.author

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotePagingAdapter.MovieViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.quotesviewlist,parent,false)
        return MovieViewHolder(view)
    }

    class MovieViewHolder(val itemview: View): RecyclerView.ViewHolder(itemview) {
        val quoteText: TextView = itemView.findViewById(R.id.quote)
        val author: TextView = itemView.findViewById(R.id.author)

    }

    object MovieComparator: DiffUtil.ItemCallback<com.example.quotelistapp.models.Result>() {
        override fun areItemsTheSame(oldItem: com.example.quotelistapp.models.Result, newItem: com.example.quotelistapp.models.Result): Boolean {
            // Id is unique.
            return oldItem.content == newItem.content
        }

        override fun areContentsTheSame(oldItem: com.example.quotelistapp.models.Result, newItem: com.example.quotelistapp.models.Result): Boolean {
            return oldItem == newItem
        }
    }
}