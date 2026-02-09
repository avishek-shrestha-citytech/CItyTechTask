package com.example.basicapp.ui.news_detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import com.example.basicapp.R
import com.example.basicapp.databinding.NewsformatBinding
import com.example.basicapp.model.Article

class NewsAdapter(
    private var articles: List<Article>,
    private val onArticleClick: ((Article) -> Unit)? = null
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val binding: NewsformatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsformatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]

        // Text
        holder.binding.mainText.text = article.title
        holder.binding.secText.text = article.description

        // Image with Coil
        holder.binding.newsImage.load(article.image) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)
        }

        // Click listener
        holder.itemView.setOnClickListener {
            onArticleClick?.invoke(article)
        }
    }

    override fun getItemCount(): Int = articles.size

    // Helper function to update list
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newArticles: List<Article>) {
        this.articles = newArticles
        notifyDataSetChanged() // Refresh RecyclerView
    }
}
