package org.deathdric.ultimatecatbattle.data

import org.deathdric.ultimatecatbattle.model.StatusEffect
import org.deathdric.ultimatecatbattle.model.StatusEffectType
import org.deathdric.ultimatecatbattle.model.SupportActionId
import org.deathdric.ultimatecatbattle.model.SupportActionTemplate
import org.deathdric.ultimatecatbattle.model.TargetType

object SupportRepository {

    private val berserk : SupportActionTemplate = SupportActionTemplate(
        id = SupportActionId.BERSERK,
        targetType = TargetType.ONE_ALLY,
        duration = 100,
        delay = 10,
        coolDown = 100,
        selfEffects = listOf(
            StatusEffect(StatusEffectType.ATTACK, 50),
            StatusEffect(StatusEffectType.DEFENSE, -25),
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
            StatusEffect(StatusEffectType.ATTACK, -25),
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
            StatusEffect(StatusEffectType.ATTACK, -15),
            StatusEffect(StatusEffectType.DEFENSE, -15),
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