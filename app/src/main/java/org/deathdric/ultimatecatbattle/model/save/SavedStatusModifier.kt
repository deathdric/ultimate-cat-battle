package org.deathdric.ultimatecatbattle.model.save

data class SavedStatusModifier(
    val attack : Int,
    val defense : Int,
    val hit : Int,
    val avoid : Int,
    val critical : Int,
    val expires : Int
)
