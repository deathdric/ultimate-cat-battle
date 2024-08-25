package org.deathdric.ultimatecatbattle.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PlayerStatus(@StringRes
                        val name: Int,
                        @DrawableRes
                        val icon: Int,
                        val active: Boolean = false,
                        val hitPoints: Int = 0,
                        val healthState: HealthState = HealthState.OK,
                        val attack: Int = 0,
                        val defense: Int = 0,
                        val hit: Int = 0,
                        val avoid: Int = 0,
                        val critical: Int = 0,
                        val alive: Boolean = true) {
}
