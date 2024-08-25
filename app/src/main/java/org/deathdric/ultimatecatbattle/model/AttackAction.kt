package org.deathdric.ultimatecatbattle.model

import androidx.annotation.StringRes

data class AttackAction(
    val id : Int,
    @StringRes
    val name : Int,
    val minDamage : Int,
    val maxDamage: Int,
    val hit : Int,
    val critical : Int,
    val delay : Int
) {

}