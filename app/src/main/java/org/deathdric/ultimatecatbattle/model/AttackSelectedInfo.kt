package org.deathdric.ultimatecatbattle.model

data class AttackSelectedInfo(
    val attackAction: AttackAction,
    val attackSimulationResult : AttackSimulationResult,
    val nextTurn : NextTurnInfo
)
