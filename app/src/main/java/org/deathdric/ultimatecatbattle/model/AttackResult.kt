package org.deathdric.ultimatecatbattle.model

data class AttackResult(
    val success: Boolean,
    val damage : Int,
    val critical : Boolean
)
