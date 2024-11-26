package org.deathdric.ultimatecatbattle.model

import kotlin.random.Random

enum class ComputerMoveType {
    ATTACK,
    SUPPORT
}

data class ComputerMove (
    val moveType: ComputerMoveType,
    val attackAction: AttackActionId? = null,
    val supportAction: SupportActionId? = null,
    val actionTarget: PlayerId
)

data class WeightedComputerMove (
    val computerMove: ComputerMove,
    var weight: Int
)

fun Game.findComputerMove(computerPlayerId: PlayerId, allyCount: Int, enemyCount: Int) : ComputerMove {
    val availableMoves = mutableListOf<WeightedComputerMove>()
    val computerPlayer = this.findPlayer(computerPlayerId).get()
    val curTime = this.curTime
    val baseAttackWeight = 30 / enemyCount
    val baseSupportWeight = 15 / allyCount
    val availableSupports = computerPlayer.supportActions.filter { it.isAvailable(curTime) }
    for (support in availableSupports) {
        val targets = this.findTargets(computerPlayerId, support.targetType)
        if (support.targetType == TargetType.ALL_ALLIES) {
            val supportMove = ComputerMove(ComputerMoveType.SUPPORT, supportAction = support.id, actionTarget = targets[0].id)
            availableMoves.add(WeightedComputerMove(supportMove, baseSupportWeight))
        } else {
            for (target in targets) {
                val supportMove = ComputerMove(ComputerMoveType.SUPPORT, supportAction = support.id, actionTarget = target.id)
                availableMoves.add(WeightedComputerMove(supportMove, baseSupportWeight))
            }
        }
    }

    val availableAttacks = computerPlayer.attackActions.filter { it.isAvailable(curTime) }
    for (attack in availableAttacks) {
        val targets = this.findTargets(computerPlayerId, attack.targetType)
        if (attack.targetType == TargetType.ALL_ENEMIES) {
            val attackMove = ComputerMove(ComputerMoveType.ATTACK, attackAction = attack.id, actionTarget = targets[0].id)
            availableMoves.add(WeightedComputerMove(attackMove, baseAttackWeight * targets.size))
        } else {
            for (target in targets) {
                val attackMove = ComputerMove(ComputerMoveType.ATTACK, attackAction = attack.id, actionTarget = target.id)
                availableMoves.add(WeightedComputerMove(attackMove, baseAttackWeight))
            }
        }
    }
    val totalWeight = availableMoves.stream().map { act -> act.weight }.reduce { t, u -> t + u }
    val selectDice = Random.nextInt(0, totalWeight.orElse(1))
    var weightCount = 0
    for (availableAction in availableMoves) {
        weightCount += availableAction.weight
        if (selectDice < weightCount) {
            return availableAction.computerMove
        }
    }
    return availableMoves.last().computerMove
}