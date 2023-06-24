package com.demo.newsapp.data.repo

import com.demo.newsapp.data.remote.NewsArticleApi
import javax.inject.Inject

class NewsArticleRepository @Inject constructor(
    private val newsArticleApi: NewsArticleApi,
) {

    fun getAllNewsArticles() = newsArticleApi.getAllNewsArticles()

}