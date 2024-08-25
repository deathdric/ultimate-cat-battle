package org.deathdric.ultimatecatbattle.data

import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.AttackAction

object AttackRepository {
    val dragonFist = AttackAction (
        id = 1,
        name = R.string.attack_dragon_fist,
        minDamage = 18,
        maxDamage = 25,
        hit = 75,
        delay = 20,
        critical = 15
    )

    val fingerOfDeath = AttackAction (
        id = 2,
        name = R.string.attack_finger_of_death,
        minDamage = 10,
        maxDamage = 12,
        hit = 100,
        delay = 15,
        critical = 30
    )

    val greatExplosion = AttackAction (
        id = 3,
        name = R.string.attack_great_explosion,
        minDamage = 30,
        maxDamage = 40,
        hit = 70,
        delay = 30,
        critical = 10
    )

    val sweepingKick = AttackAction (
        id = 4,
        name = R.string.attack_sweeping_kick,
        minDamage = 60,
        maxDamage = 80,
        hit = 50,
        delay = 50,
        critical = 5
    )

    val heartStrike = AttackAction (
        id = 5,
        name = R.string.attack_heart_strike,
        minDamage = 40,
        maxDamage = 50,
        hit = 90,
        delay = 50,
        critical = 20
    )

    val beakStrike = AttackAction (
        id = 6,
        name = R.string.attack_beak_strike,
        minDamage = 10,
        maxDamage = 12,
        hit = 100,
        delay = 15,
        critical = 30
    )
}