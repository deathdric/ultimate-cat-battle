package org.deathdric.ultimatecatbattle.model

data class SupportSelectedInfo(
    val supportAction: SupportAction,
    val effects: List<SupportEffect>,
    val nextTurn: NextTurnInfo
)
