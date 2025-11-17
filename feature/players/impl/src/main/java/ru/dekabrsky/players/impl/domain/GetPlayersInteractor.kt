package ru.dekabrsky.players.impl.domain

import ru.dekabrsky.players.api.domain.IPlayersInteractor
import ru.dekabrsky.players.api.domain.Player

class GetPlayersInteractor: IPlayersInteractor {
    fun getPlayers() = listOf(
        Player("Александр Головин", "Монако", "FR"),
        Player("Алексей Миранчук", "Атланта", "US"),
        Player("Никита Иосифов", "Целе", "SI"),
    )

    override fun getPlayer(id: String) = getPlayers().random()
}