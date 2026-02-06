package com.example.basicapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basicapp.model.NewsModelImpl
import com.example.basicapp.model.NewsCallback
import com.example.basicapp.model.Article
import com.example.basicapp.ui.NewsAdapter

class MainActivity : AppCompatActivity(), NewsCallback {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val newsModel = NewsModelImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.newsRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        newsAdapter = NewsAdapter(emptyList())
        recyclerView.adapter = newsAdapter

        newsModel.fetchNews(this)
    }

    override fun onNewsLoaded(articles: List<Article>) {
        newsAdapter.updateData(articles)
    }

    override fun onError(error: String) {
        android.util.Log.e("NEWS_ERROR", error)
    }
}