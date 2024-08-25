package org.deathdric.ultimatecatbattle.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PlayerTemplate(
    @StringRes
    val name: Int,
    @DrawableRes
    val icon: Int,
    @DrawableRes
    val iconLoss: Int,
    val maxHp : Int,
    val attackActions: List<AttackAction>,
    val supportActions : List<SupportAction>
)
