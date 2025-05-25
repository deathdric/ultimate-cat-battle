package org.deathdric.ultimatecatbattle.data.game

import org.deathdric.ultimatecatbattle.model.game.StatusEffect
import org.deathdric.ultimatecatbattle.model.game.StatusEffectType
import org.deathdric.ultimatecatbattle.model.game.SupportActionId
import org.deathdric.ultimatecatbattle.model.game.SupportActionTemplate
import org.deathdric.ultimatecatbattle.model.game.TargetType

object SupportRepository {

    private val berserk : SupportActionTemplate = SupportActionTemplate(
        id = SupportActionId.BERSERK,
        targetType = TargetType.ONE_ALLY,
        duration = 100,
        delay = 10,
        coolDown = 100,
        selfEffects = listOf(
            StatusEffect(StatusEffectType.ATTACK, 50),
            StatusEffect(StatusEffectType.DEFENSE, -20),
            StatusEffect(StatusEffectType.AVOID, -10),
            StatusEffect(StatusEffectType.CRITICAL, 25),
        ),
        otherEffects = listOf(
            StatusEffect(StatusEffectType.ATTACK, 25),
            StatusEffect(StatusEffectType.CRITICAL, 15)
        )
    )

    private val guard : SupportActionTemplate = SupportActionTemplate(
        id = SupportActionId.GUARD,
        targetType = TargetType.ONE_ALLY,
        duration = 100,
        delay = 20,
        coolDown = 100,
        selfEffects = listOf(
            StatusEffect(StatusEffectType.ATTACK, -20),
            StatusEffect(StatusEffectType.DEFENSE, 50),
            StatusEffect(StatusEffectType.HIT, -10),
            StatusEffect(StatusEffectType.AVOID, 10)
        ),
        otherEffects = listOf(
            StatusEffect(StatusEffectType.DEFENSE, 25),
            StatusEffect(StatusEffectType.AVOID, 5)
        )
    )

    private val speed : SupportActionTemplate = SupportActionTemplate(
        id = SupportActionId.SPEED,
        targetType = TargetType.ONE_ALLY,
        duration = 100,
        delay = 10,
        coolDown = 100,
        selfEffects = listOf(
            StatusEffect(StatusEffectType.ATTACK, -10),
            StatusEffect(StatusEffectType.DEFENSE, -10),
            StatusEffect(StatusEffectType.HIT, 30),
            StatusEffect(StatusEffectType.AVOID, 20)

        ),
        otherEffects = listOf(
            StatusEffect(StatusEffectType.HIT, 15),
            StatusEffect(StatusEffectType.AVOID, 10)
        )
    )

    fun getSupport(id: SupportActionId) : SupportActionTemplate {
        return when(id) {
            SupportActionId.BERSERK -> berserk
            SupportActionId.GUARD -> guard
            SupportActionId.SPEED -> speed
        }
    }
}