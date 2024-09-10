package org.deathdric.ultimatecatbattle.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SupportEffect (
    val statModifier: Int,
    @StringRes
    val statName: Int,
    @DrawableRes
    val statImage: Int,
    val maxedOut: Boolean
)
