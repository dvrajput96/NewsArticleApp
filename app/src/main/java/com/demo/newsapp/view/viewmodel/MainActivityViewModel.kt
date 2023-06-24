package com.demo.newsapp.view.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.newsapp.R
import com.demo.newsapp.data.model.NewsArticleResponse
import com.demo.newsapp.data.repo.NewsArticleRepository
import com.demo.newsapp.data.utils.NetworkChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: NewsArticleRepository,
    private val networkChecker: NetworkChecker,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _newsArticles = mutableStateOf(NewsArticleResponse())
    val newsArticles: State<NewsArticleResponse?> get() = _newsArticles

    private val _networkErrorMessage = mutableStateOf("")
    val networkErrorMessage: State<String?> get() = _networkErrorMessage

    private val _showProgress = mutableStateOf(false)
    val showProgress: State<Boolean> get() = _showProgress

    fun getAllNewsArticles() {
        viewModelScope.launch {
            _showProgress.value = true
            if (networkChecker.hasInternetConnection()) {
                repository.getAllNewsArticles().collect {
                    it?.let { articles ->
                        _showProgress.value = false
                        _newsArticles.value = articles
                    }
                }
            } else {
                _showProgress.value = false
                _networkErrorMessage.value = context.getString(R.string.no_internet_connection)
            }
        }
    }

}