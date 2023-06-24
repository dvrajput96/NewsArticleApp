package com.demo.newsapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.demo.newsapp.data.model.NewsArticleResponse
import com.demo.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsItemUI(
    article: NewsArticleResponse.Article,
) {
    Column {
        Spacer(Modifier.height(NewsAppTheme.dimensions.twenty_two_dp))
        val imageModifier = Modifier
            .heightIn(min = NewsAppTheme.dimensions.image_min_height)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)

        // Coil to load the image from url
        AsyncImage(
            model = article.urlToImage.toString(),
            modifier = imageModifier,
            contentDescription = article.description.toString(),
            contentScale = ContentScale.Crop,
        )
        // For vertical spacing
        Spacer(Modifier.height(NewsAppTheme.dimensions.sixteen_dp))
        Text(
            article.title.toString(),
            style = MaterialTheme.typography.headlineSmall
        )
        if (!article.author.isNullOrBlank()) {
            Spacer(Modifier.height(NewsAppTheme.dimensions.eight_dp))
            Text(
                article.author,
                style = MaterialTheme.typography.bodyMedium
            )
            // For vertical spacing
            Spacer(Modifier.height(NewsAppTheme.dimensions.twenty_two_dp))
        } else {
            // For vertical spacing
            Spacer(Modifier.height(NewsAppTheme.dimensions.twenty_two_dp))
        }
        // To show the vertical divider
        Divider(color = Color.Gray, thickness = NewsAppTheme.dimensions.line_height)
    }
}