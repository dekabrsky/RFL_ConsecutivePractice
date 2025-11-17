package ru.dekabrsky.rfl.players.domain.model

class GetPlayersInteractor {
    fun getPlayers() = listOf(
        Player("Александр Головин", "Монако", "FR"),
        Player("Алексей Миранчук", "Атланта", "US"),
        Player("Никита Иосифов", "Целе", "SI"),
    )

    fun getPlayer(id: String) = getPlayers().random()
}