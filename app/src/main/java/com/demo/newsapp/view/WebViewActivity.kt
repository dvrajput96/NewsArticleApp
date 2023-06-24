package com.demo.newsapp.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.newsapp.ui.theme.NewsAppTheme
import com.demo.newsapp.utils.AppConstants
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import dagger.hilt.android.AndroidEntryPoint

/**
 * Separate activity for webview
 */
@AndroidEntryPoint
class WebViewActivity : ComponentActivity() {

    private var initialUrl = "https://google.com"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialUrl = intent.extras?.getString(AppConstants.BUN_URL) ?: ""
        setContent {
            NewsAppTheme {
                val state = rememberWebViewState(url = initialUrl)
                val navigator = rememberWebViewNavigator()

                Column {
                    // A custom WebViewClient and WebChromeClient can be provided via subclassing
                    val webClient = remember {
                        object : AccompanistWebViewClient() {
                            override fun onPageStarted(
                                view: WebView,
                                url: String?,
                                favicon: Bitmap?
                            ) {
                                super.onPageStarted(view, url, favicon)
                                Log.d("Accompanist WebView", "Page started loading for $url")
                            }
                        }
                    }

                    WebView(
                        state = state,
                        modifier = Modifier
                            .weight(1f),
                        navigator = navigator,
                        onCreated = { webView ->
                            webView.settings.javaScriptEnabled = true
                            webView.settings.domStorageEnabled = true
                        },
                        client = webClient
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun WebViewPreview() {
    NewsAppTheme {
        Column {
            Text("Preview should still load but WebView will be grey box.")
            WebView(
                state = rememberWebViewState(url = "localhost"),
                modifier = Modifier.height(100.dp)
            )
        }
    }
}