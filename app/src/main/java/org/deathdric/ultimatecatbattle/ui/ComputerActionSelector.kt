package org.deathdric.ultimatecatbattle.ui

import org.deathdric.ultimatecatbattle.model.Player
import kotlin.random.Random

class ActionInfo(val actionData : (UltimateCatBattleViewModel) -> Unit) {
    var weight : Int = 1
}


fun computeTurn(
    currentPlayer: Player,
    opponent : Player
) : (UltimateCatBattleViewModel) -> Unit {
    val availableActions : MutableList<ActionInfo> = ArrayList()
    for (attackAction in currentPlayer.template.attackActions) {
        val attackActionInfo = ActionInfo { vm -> vm.chooseAttack(attackAction) }
        attackActionInfo.weight = 10
        availableActions.add(attackActionInfo)
    }
    for (supportAction in currentPlayer.template.supportActions) {
        val supportActionInfo = ActionInfo { vm -> vm.chooseSupport(supportAction) }
        supportActionInfo.weight = 5
        availableActions.add(supportActionInfo)
    }
    val totalWeight = availableActions.stream().map { act -> act.weight }.reduce { t, u -> t + u }
    val selectDice = Random.nextInt(0, totalWeight.orElse(1))
    var weightCount = 0
    for (availableAction in availableActions) {
        weightCount += availableAction.weight
        if (selectDice < weightCount) {
            return availableAction.actionData
        }
    }
    return availableActions.last().actionData
}