package org.deathdric.ultimatecatbattle.data

import org.deathdric.ultimatecatbattle.model.AttackActionId
import org.deathdric.ultimatecatbattle.model.AttackActionTemplate
import org.deathdric.ultimatecatbattle.model.AttackDamageRange
import org.deathdric.ultimatecatbattle.model.AttackStatusEffect
import org.deathdric.ultimatecatbattle.model.DelayEffect
import org.deathdric.ultimatecatbattle.model.StatusEffect
import org.deathdric.ultimatecatbattle.model.StatusEffectType
import org.deathdric.ultimatecatbattle.model.TargetType

object AttackRepository {
    private val dragonFist = AttackActionTemplate (
        id = AttackActionId.DRAGON_FIST,
        targetType = TargetType.ONE_ENEMY,
        defaultDamageRange = AttackDamageRange(18, 25),
        damage = mapOf(),
        hit = 75,
        delay = 20,
        critical = 15,
        statusEffect = AttackStatusEffect(50, 100, listOf(StatusEffect(StatusEffectType.DEFENSE, -10)))
    )

    private val fingerOfDeath = AttackActionTemplate (
        id = AttackActionId.FINGER_OF_DEATH,
        targetType = TargetType.ONE_ENEMY,
        defaultDamageRange = AttackDamageRange(10, 12),
        damage = mapOf(),
        hit = 100,
        delay = 15,
        critical = 30,
        statusEffect = AttackStatusEffect(75, 100, listOf(StatusEffect(StatusEffectType.AVOID, -5)))
    )

    private val greatExplosion = AttackActionTemplate (
        id = AttackActionId.GREAT_EXPLOSION,
        targetType = TargetType.ALL_ENEMIES,
        defaultDamageRange = AttackDamageRange(30, 40),
        damage = mapOf(Pair(2, AttackDamageRange(20, 27)), Pair(3, AttackDamageRange(15, 20))),
        hit = 70,
        delay = 30,
        critical = 10
    )

    private val sweepingKick = AttackActionTemplate (
        id = AttackActionId.SWEEPING_KICK,
        targetType = TargetType.ALL_ENEMIES,
        defaultDamageRange = AttackDamageRange(60, 80),
        damage = mapOf(Pair(2, AttackDamageRange(40, 54)), Pair(3, AttackDamageRange(30, 40))),
        hit = 50,
        delay = 50,
        critical = 5,
        coolDown = 100,
        delayEffect = DelayEffect(50, 10)
    )

    private val heartStrike = AttackActionTemplate (
        id = AttackActionId.HEART_STRIKE,
        targetType = TargetType.ONE_ENEMY,
        defaultDamageRange = AttackDamageRange(40, 50),
        damage = mapOf(),
        hit = 90,
        coolDown = 100,
        delay = 50,
        critical = 20,
        statusEffect = AttackStatusEffect(50, 100, listOf(StatusEffect(StatusEffectType.HIT, -5), StatusEffect(StatusEffectType.ATTACK, -5)))
    )

    private val beakStrike = AttackActionTemplate (
        id = AttackActionId.BEAK_STRIKE,
        targetType = TargetType.ONE_ENEMY,
        defaultDamageRange = AttackDamageRange(10, 12),
        damage = mapOf(),
        hit = 100,
        delay = 15,
        critical = 30,
        statusEffect = AttackStatusEffect(75, 100, listOf(StatusEffect(StatusEffectType.AVOID, -5)))
    )

    fun getAttack(id: AttackActionId) : AttackActionTemplate {
        return when(id) {
            AttackActionId.BEAK_STRIKE -> beakStrike
            AttackActionId.DRAGON_FIST -> dragonFist
            AttackActionId.HEART_STRIKE -> heartStrike
            AttackActionId.SWEEPING_KICK -> sweepingKick
            AttackActionId.FINGER_OF_DEATH -> fingerOfDeath
            AttackActionId.GREAT_EXPLOSION -> greatExplosion
        }
    }
}