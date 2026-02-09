package com.example.basicapp.ui.news_detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
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

    private val expandedPositions = mutableSetOf<Int>()

    class NewsViewHolder(val binding: NewsformatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsformatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        val isExpanded = expandedPositions.contains(position)

        holder.binding.mainText.text = article.title
        holder.binding.fullDescription.text = article.description ?: "No description available"

        holder.binding.expandedSection.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.binding.expandButton.setImageResource(
            if (isExpanded) R.drawable.ic_expand_less else R.drawable.ic_expand_more
        )

        // Image with Coil
        holder.binding.newsImage.load(article.image) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)
        }

        // Expand Button Listener
        holder.binding.expandButton.setOnClickListener {
            if (isExpanded) {
                expandedPositions.remove(position)
            } else {
                expandedPositions.add(position)
            }
            notifyItemChanged(position)
        }

        // Click listener for details page
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
