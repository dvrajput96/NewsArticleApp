package com.demo.newsapp.di

import android.content.Context
import android.net.ConnectivityManager
import com.demo.newsapp.data.remote.NewsArticleApi
import com.demo.newsapp.data.utils.NetworkChecker
import com.demo.newsapp.data.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsAppModule {

    @Singleton
    @Provides
    fun provideCoroutineScope(
        coroutineDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    @Singleton
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideNetworkChecker(@ApplicationContext context: Context): NetworkChecker =
        NetworkChecker(context.getSystemService(ConnectivityManager::class.java))

    @Singleton
    @Provides
    fun providesNewsArticleApi(
        coroutineScope: CoroutineScope,
        connection: HttpURLConnection,
        coroutineDispatcher: CoroutineDispatcher,
    ): NewsArticleApi = NewsArticleApi(
        coroutineScope,
        connection,
        coroutineDispatcher
    )

    @Singleton
    @Provides
    fun provideHttpUrlConnection(): HttpURLConnection {
        val connection = URL(NetworkUtils.URL).openConnection() as HttpURLConnection
        connection.apply {
            requestMethod = "GET"
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
            connectTimeout = 5000
            readTimeout = 5000
            doInput = true
        }
        return connection
    }

}