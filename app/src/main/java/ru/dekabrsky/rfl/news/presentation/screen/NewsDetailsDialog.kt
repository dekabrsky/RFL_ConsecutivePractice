package ru.dekabrsky.rfl.news.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ru.dekabrsky.rfl.News
import ru.dekabrsky.rfl.navigation.Route
import ru.dekabrsky.rfl.navigation.TopLevelBackStack
import ru.dekabrsky.rfl.news.presentation.MockData
import ru.dekabrsky.rfl.news.presentation.model.NewsUiModel
import ru.dekabrsky.rfl.uikit.RatingBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailsDialog(
    news: NewsUiModel,
    stack: TopLevelBackStack<Route>,
) {
    ModalBottomSheet(
        onDismissRequest = { stack.removeLast() }
    ) {
        NewsDetailsContent(news)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsDetailsContent(news: NewsUiModel) {
    var rating by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = news.time,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = news.title,
            style = MaterialTheme.typography.titleMedium,
        )

        if (!news.text.isNullOrBlank()) {
            Text(
                text = news.text,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        if (!news.imageUrl.isNullOrBlank()) {
            GlideImage(
                model = news.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit,
            )
        }

        RatingBar(rating) { rating = it }

        if (rating > 0f) {
            Text("Ваша оценка: $rating")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsDetailDialogPreview() {
    NewsDetailsContent(
        MockData.getNews().first(),
    )
}