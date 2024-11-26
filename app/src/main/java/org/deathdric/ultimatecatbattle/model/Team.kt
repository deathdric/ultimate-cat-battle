package org.deathdric.ultimatecatbattle.model

data class Team(val players: List<Player>, val id: TeamId) {
    val isAlive get() = players.stream().anyMatch { it.isAlive }
}
