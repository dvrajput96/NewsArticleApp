package com.demo.newsapp.data.remote

import android.util.Log
import com.demo.newsapp.data.model.NewsArticleResponse
import com.demo.newsapp.data.utils.NetworkUtils
import com.google.gson.Gson
import com.google.gson.JsonParseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import javax.inject.Inject

class NewsArticleApi @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val connection: HttpURLConnection,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    lateinit var bufferedReader: BufferedReader
    lateinit var inputStreamReader: InputStreamReader

    fun getAllNewsArticles(): Flow<NewsArticleResponse?> {
        return flow {
            try {
                if (connection.responseCode == 200) {
                    // Success
                    inputStreamReader = InputStreamReader(connection.inputStream)
                    inputStreamReader.use { input ->
                        val response = StringBuilder()
                        bufferedReader = BufferedReader(input)
                        bufferedReader.forEachLine {
                            response.append(it.trim())
                        }
                        val newsArticleResponse =
                            Gson().fromJson(response.toString(), NewsArticleResponse::class.java)
                        emit(newsArticleResponse)
                        Log.d(NetworkUtils.TAG, "getAllNewsArticles: ${response.toString()}")
                    }
                    coroutineScope.launch {
                        bufferedReader.close()
                        inputStreamReader.close()
                    }
                }
            } catch (e: JsonParseException) {
                emit(null)
            } catch (e: IOException) {
                emit(null)
            } finally {
                coroutineScope.launch {
                    bufferedReader.close()
                    inputStreamReader.close()
                    connection.disconnect()
                }
            }
        }.flowOn(coroutineDispatcher)
    }

}