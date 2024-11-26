package org.deathdric.ultimatecatbattle.model

data class AttackDamageRange (
    val minDamage: Int,
    val maxDamage: Int
)

data class AttackActionTemplate (
    val id: AttackActionId,
    val targetType: TargetType,
    val defaultDamageRange: AttackDamageRange,
    val damage: Map<Int, AttackDamageRange>,
    val hit: Int,
    val critical: Int,
    val delay: Int,
    val coolDown: Int = 0,
    val statusEffect: AttackStatusEffect? = null,
    val delayEffect: DelayEffect? = null
)