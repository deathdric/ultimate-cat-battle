package org.deathdric.ultimatecatbattle.model

data class StatusEffect(
    val attackBonus : Int,
    val defenseBonus : Int,
    val hitBonus : Int,
    val avoidBonus : Int,
    val criticalBonus : Int,
    val expires : Int
)
