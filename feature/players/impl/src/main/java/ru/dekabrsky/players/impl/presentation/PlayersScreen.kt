package ru.dekabrsky.players.impl.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ru.dekabrsky.players.api.presentation.PlayerItem
import ru.dekabrsky.players.impl.domain.GetPlayersInteractor

@Composable
fun PlayersScreen() {
    val interactor = remember { GetPlayersInteractor() }

    Column {
        interactor.getPlayers().forEach { PlayerItem(it) }
    }
}
