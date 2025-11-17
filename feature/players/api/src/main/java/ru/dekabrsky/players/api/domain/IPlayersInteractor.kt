package ru.dekabrsky.players.api.domain

interface IPlayersInteractor {
    fun getPlayer(id: String): Player
}