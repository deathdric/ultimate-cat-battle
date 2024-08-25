package org.deathdric.ultimatecatbattle.data

import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.PlayerTemplate

object PlayerRepository {

    val catPlayer = PlayerTemplate(
        name = R.string.cat,
        icon = R.drawable.cat2,
        iconLoss = R.drawable.cat_loss,
        maxHp = 400,
        attackActions = listOf(
            AttackRepository.dragonFist,
            AttackRepository.fingerOfDeath,
            AttackRepository.greatExplosion,
            AttackRepository.sweepingKick,
            AttackRepository.heartStrike
        ),
        supportActions = listOf(
            SupportRepository.berserk,
            SupportRepository.guard,
            SupportRepository.speed,
            SupportRepository.powerFist
        )
    )

    val penguinPlayer = PlayerTemplate(
        name = R.string.penguin,
        icon = R.drawable.penguin1,
        iconLoss = R.drawable.penguin_loss,
        maxHp = 400,
        attackActions = listOf(
            AttackRepository.dragonFist,
            AttackRepository.beakStrike,
            AttackRepository.greatExplosion,
            AttackRepository.sweepingKick,
            AttackRepository.heartStrike
        ),
        supportActions = listOf(
            SupportRepository.berserk,
            SupportRepository.guard,
            SupportRepository.speed,
            SupportRepository.powerFist
        )
    )
}