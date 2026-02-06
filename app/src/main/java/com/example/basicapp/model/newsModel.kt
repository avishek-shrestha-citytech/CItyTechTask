package com.example.basicapp.model

data class Source(
    val name: String
)

data class Article(
    val title: String,
    val description: String?,
    val url: String,
    val image: String?,
    val publishedAt: String,
    val source: Source
)

data class NewsApiResponse(
    val totalArticles: Int,
    val articles: List<Article>
)
