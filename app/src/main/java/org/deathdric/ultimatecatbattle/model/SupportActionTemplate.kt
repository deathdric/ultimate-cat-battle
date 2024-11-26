package org.deathdric.ultimatecatbattle.model

data class SupportActionTemplate(
    val id: SupportActionId,
    val targetType: TargetType,
    val selfEffects: List<StatusEffect>,
    val otherEffects: List<StatusEffect>,
    val delay: Int,
    val duration: Int,
    val coolDown: Int = 0
)
