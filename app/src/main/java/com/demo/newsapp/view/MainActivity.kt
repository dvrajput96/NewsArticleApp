package com.demo.newsapp.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.demo.newsapp.R
import com.demo.newsapp.ui.theme.NewsAppTheme
import com.demo.newsapp.utils.AppConstants
import com.demo.newsapp.view.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ask for post notification permission
        askNotificationPermission()
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    init()
                }
            }
        }
    }

    @Composable
    private fun init() {
        val webViewUrl =
            remember { mutableStateOf("") } // To store the url which we will load in webview
        val shouldOpenWebView =
            remember { mutableStateOf(false) } // To open or close the webview

        // UI of the Home screen
        MainContent(viewModel, webViewUrl, shouldOpenWebView)

        // Open the web view activity
        if (shouldOpenWebView.value) {
            val intent = Intent(this@MainActivity, WebViewActivity::class.java)
            val bundle = Bundle().apply {
                putString(
                    AppConstants.BUN_URL,
                    webViewUrl.value
                ) // Pass the url to webview activity
            }
            intent.putExtras(bundle)
            startActivity(intent)
            // After opening the web view activity reset the values
            shouldOpenWebView.value = false
            webViewUrl.value = ""
        }
    }

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {

            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    viewModel: MainActivityViewModel,
    webViewUrl: MutableState<String>,
    shouldOpenWebView: MutableState<Boolean>
) {

    LaunchedEffect(Unit) {
        // Call the api inside launch effect so it would be called only once
        viewModel.getAllNewsArticles()
    }

    Scaffold(
        topBar = {
            // Toolbar
            TopAppBar(
                title = {
                    Row {
                        Text(stringResource(R.string.news_article))
                    }
                })
        },
        content = { padding ->

            // To show to progress indicator while calling the api
            if (viewModel.showProgress.value) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }

            val networkError = viewModel.networkErrorMessage.value
            if (networkError?.isEmpty() == true) {
                Column(
                    modifier = Modifier.padding(
                        start = NewsAppTheme.dimensions.sixteen_dp,
                        end = NewsAppTheme.dimensions.sixteen_dp,
                        bottom = NewsAppTheme.dimensions.sixteen_dp
                    )
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(padding)
                    ) {
                        // Display the news list item
                        viewModel.newsArticles.value?.articles?.let { list ->
                            items(list) { article ->
                                if (article != null) {
                                    Surface(modifier = Modifier.clickable {
                                        if (!article.url.isNullOrBlank()) {
                                            webViewUrl.value = article.url
                                            shouldOpenWebView.value = true
                                        }
                                    }) {
                                        NewsItemUI(article)
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Display the error UI
                OfflineUI(padding, networkError)
            }
        }
    )
}




