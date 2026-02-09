package com.example.basicapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basicapp.R
import com.example.basicapp.model.Article
import com.example.basicapp.ui.news_detail.NewsAdapter
import com.example.basicapp.remote.NewsCallback
import com.example.basicapp.remote.NewsModelImpl
import com.example.basicapp.ui.news_detail.NewsDetailFragment

class NewsFeedFragment : Fragment(), NewsCallback {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val newsModel = NewsModelImpl()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.newsRecycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        newsAdapter = NewsAdapter(emptyList()) { article ->
            openArticleDetail(article)
        }
        recyclerView.adapter = newsAdapter

        recyclerView.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {
                view.isFocusable = true
                view.isFocusableInTouchMode = true
            }
            override fun onChildViewDetachedFromWindow(view: View) = Unit
        })

        val detector = GestureDetector(requireContext(), object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                recyclerView.findChildViewUnder(e.x, e.y)?.requestFocus()
                return false
            }
            override fun onLongPress(e: MotionEvent) {
                recyclerView.findChildViewUnder(e.x, e.y)?.requestFocus()
            }
        })

        recyclerView.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                detector.onTouchEvent(e)
                return false
            }
        })

        newsModel.fetchNews(this)
    }

    private fun openArticleDetail(article: Article) {
        val detailFragment = NewsDetailFragment.newInstance(
            title = article.title,
            description = article.description,
            image = article.image,
            url = article.url,
            source = article.source.name,
            date = article.publishedAt
        )

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onNewsLoaded(articles: List<Article>) {
        newsAdapter.updateData(articles)
    }

    override fun onError(error: String) {
        Log.e("NEWS_ERROR", error)
    }

    fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }
}
