package com.demo.newsapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.demo.newsapp.R
import com.demo.newsapp.ui.theme.NewsAppTheme

@Composable
fun OfflineUI(
    padding: PaddingValues,
    networkError: String?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .padding(NewsAppTheme.dimensions.twenty_two_dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_offline),
            contentDescription = stringResource(id = R.string.offline_image)
        )
        Spacer(modifier = Modifier.padding(top = NewsAppTheme.dimensions.sixteen_dp))
        Text(
            text = networkError.toString(),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.padding(top = NewsAppTheme.dimensions.sixteen_dp))
        Text(
            text = stringResource(R.string.please_try_again),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}