package ru.dekabrsky.rfl.news.presentation.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.dekabrsky.rfl.navigation.Route
import ru.dekabrsky.rfl.navigation.TopLevelBackStack
import ru.dekabrsky.rfl.news.presentation.MockData
import ru.dekabrsky.rfl.news.presentation.model.NewsDetailsViewState
import ru.dekabrsky.rfl.news.presentation.model.NewsUiModel
import ru.dekabrsky.rfl.news.presentation.viewModel.NewsDetailsViewModel
import ru.dekabrsky.rfl.uikit.RatingBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailsDialog(
    news: NewsUiModel,
) {
    val viewModel = koinViewModel<NewsDetailsViewModel> {
        parametersOf(news)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    ModalBottomSheet(
        onDismissRequest = { viewModel.onBack() },
    ) {
        NewsDetailsContent(state, viewModel::onRatingChanged)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsDetailsContent(
    state: NewsDetailsViewState,
    onRatingChanged: (Float) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val context = LocalContext.current

        Icon(
            Icons.Default.Share,
            null,
            Modifier.clickable {
                shareText(context, state.news.title)
            }
        )

        Text(
            text = state.news.time,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = state.news.title,
            style = MaterialTheme.typography.titleMedium,
        )

        if (!state.news.text.isNullOrBlank()) {
            Text(
                text = state.news.text,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        if (!state.news.imageUrl.isNullOrBlank()) {
            GlideImage(
                model = state.news.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit,
            )
        }

        RatingBar(state.rating) { onRatingChanged(it) }

        if (state.userVoteVisible) {
            Text("Ваша оценка: ${state.rating}")
        }
    }
}

fun shareText(context: Context, text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, "Поделиться через"))
}

@Preview(showBackground = true)
@Composable
fun NewsDetailDialogPreview() {
    NewsDetailsContent(
        NewsDetailsViewState(MockData.getNews().first()),
    )
}