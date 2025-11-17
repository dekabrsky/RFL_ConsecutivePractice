package ru.dekabrsky.rfl.players.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ru.dekabrsky.rfl.players.domain.model.GetPlayersInteractor
import ru.dekabrsky.rfl.players.domain.model.Player
import ru.dekabrsky.uikit.uikit.Spacing

@Composable
fun PlayersScreen() {
    val interactor = remember { GetPlayersInteractor() }

    Column {
        interactor.getPlayers().forEach { PlayerItem(it) }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayerItem(player: Player) {
    Card(Modifier
        .padding(horizontal = Spacing.medium, vertical = Spacing.small)
        .fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(Spacing.medium)
        ) {
            GlideImage(
                "https://flagcdn.com/40x30/${player.country.lowercase()}.png",
                null
            )

            Spacer(Modifier.width(Spacing.medium))

            Column {
                Text(player.name)
                Text(player.team)
            }
        }
    }
}