package org.deathdric.ultimatecatbattle.model

import androidx.annotation.StringRes

data class SupportAction (
    val id : Int,
    @StringRes
    val name : Int,
    val attackBonus : Int = 0,
    val defenseBonus : Int = 0,
    val hitBonus : Int = 0,
    val avoidBonus : Int = 0,
    val criticalBonus : Int = 0,
    val delay : Int,
    val duration : Int
)