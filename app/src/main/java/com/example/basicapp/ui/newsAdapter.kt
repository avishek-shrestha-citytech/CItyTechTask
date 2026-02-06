package com.example.basicapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import com.example.basicapp.R
import com.example.basicapp.model.Article

class NewsAdapter(private var articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.mainText)
        val description: TextView = view.findViewById(R.id.secText)
        val image: ImageView = view.findViewById(R.id.newsImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.newsformat, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]

        // Text
        holder.title.text = article.title
        holder.description.text = article.description

        // Coil
        holder.image.load(article.image) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)
        }
    }

    override fun getItemCount(): Int = articles.size

    // Helper to update data when API returns results
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newArticles: List<Article>) {
        this.articles = newArticles
        notifyDataSetChanged() // Tells the list to refresh
    }
}