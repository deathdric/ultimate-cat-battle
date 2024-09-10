package org.deathdric.ultimatecatbattle.model

data class AttackSimulationResult(
    val minDamage : Int,
    val minDamageEnough : Boolean,
    val maxDamage : Int,
    val maxDamageEnough : Boolean,
    val successChance : Int,
    val critChance : Int
)
