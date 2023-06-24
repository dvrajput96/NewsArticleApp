package com.demo.newsapp.data.model

data class NewsArticleResponse(
    val articles: List<Article?>? = listOf(),
    val status: String? = ""
) {
    data class Article(
        val author: String? = "",
        val content: String? = "",
        val description: String? = "",
        val publishedAt: String? = "",
        val source: Source? = Source(),
        val title: String? = "",
        val url: String? = "",
        val urlToImage: String? = ""
    ) {
        data class Source(
            val id: String? = "",
            val name: String? = ""
        )
    }
}