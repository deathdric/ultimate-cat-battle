package org.deathdric.ultimatecatbattle.model.save

data class SavedPlayer(
    val playerId: String,
    val playerType: String,
    val computerMoveTypeChoice: String?,
    val nextAvailableTurn: Int,
    val availableAttacks: List<SavedAvailableAction>,
    val availableSupports: List<SavedAvailableAction>,
    val hitPoints: Int,
    val maxHitPoints: Int,
    val baseAttack: Int = 0,
    val baseDefense: Int = 0,
    val baseHit: Int = 0,
    val baseAvoid: Int = 0,
    val baseCrit: Int = 0,
    val statusModifiers: List<SavedStatusModifier> = listOf()
)
