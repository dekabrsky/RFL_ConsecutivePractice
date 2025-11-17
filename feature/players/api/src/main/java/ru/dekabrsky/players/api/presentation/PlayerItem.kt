package ru.dekabrsky.players.api.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ru.dekabrsky.players.api.domain.Player
import ru.dekabrsky.uikit.uikit.Spacing

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayerItem(player: Player) {
    Card(
        Modifier.Companion
            .padding(horizontal = Spacing.medium, vertical = Spacing.small)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Companion.CenterVertically,
            modifier = Modifier.Companion.padding(Spacing.medium)
        ) {
            GlideImage(
                "https://flagcdn.com/40x30/${player.country.lowercase()}.png",
                null
            )

            Spacer(Modifier.Companion.width(Spacing.medium))

            Column {
                Text(player.name)
                Text(player.team)
            }
        }
    }
}