package org.deathdric.ultimatecatbattle.model.save

data class SavedTeam(
    val id: String,
    val players: List<SavedPlayer>
)