package org.deathdric.ultimatecatbattle.model.save

data class SavedGame(
    val version: Int,
    val currentTime: Int,
    val teams: List<SavedTeam>
)
