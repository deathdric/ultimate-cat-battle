package org.deathdric.ultimatecatbattle.model

enum class PlayerType (val isComputer: Boolean) {
    PLAYER1(false),
    PLAYER2(false),
    PLAYER3(false),
    PLAYER4(false),
    COMPUTER(true)
}

fun allPlayerTypes(playerCount: Int) : List<PlayerType> {
    return PlayerType.entries.filter { !it.isComputer }.subList(0, playerCount)
}