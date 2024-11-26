package org.deathdric.ultimatecatbattle.model

data class PlayerTemplate (
    val playerId: PlayerId,
    val maxHitPoints: Int = 400,
    val attackActions : List<AttackActionId>,
    val supportActions: List<SupportActionId>
)