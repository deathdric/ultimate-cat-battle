package org.deathdric.ultimatecatbattle.data

import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.SupportAction

object SupportRepository {

    val berserk = SupportAction (
        id = 1,
        name = R.string.support_berserk,
        attackBonus = 25,
        defenseBonus = -25,
        criticalBonus = 15,
        delay = 10,
        duration = 100
    )

    val guard = SupportAction (
        id = 2,
        name = R.string.support_guard,
        attackBonus = -25,
        defenseBonus = 50,
        criticalBonus = -10,
        avoidBonus = 10,
        delay = 30,
        duration = 100
    )

    val speed = SupportAction (
        id = 3,
        name = R.string.support_speed,
        attackBonus = -25,
        hitBonus = 25,
        avoidBonus = 10,
        criticalBonus = 15,
        delay = 10,
        duration = 100
    )

    val powerFist = SupportAction (
        id = 4,
        name = R.string.support_power_fist,
        criticalBonus = 30,
        delay = 20,
        duration = 100
    )
}