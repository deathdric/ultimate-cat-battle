package org.deathdric.ultimatecatbattle.model

import org.deathdric.ultimatecatbattle.data.AttackRepository
import org.deathdric.ultimatecatbattle.ui.mainEffectType
import kotlin.random.Random

enum class ComputerMoveType {
    ATTACK,
    SUPPORT
}

data class AvailableComputerMove (
    val moveType: ComputerMoveType,
    val attackActionPreview: AttackActionPreview? = null,
    val supportAction: SupportAction? = null,
    val actionTargets: List<Player>
)

fun AvailableComputerMove.toComputerMove() : ComputerMove {
    return when(moveType) {
        ComputerMoveType.SUPPORT -> ComputerMove(moveType = moveType, supportAction = supportAction!!.id, actionTarget = actionTargets[0].id)
        ComputerMoveType.ATTACK -> ComputerMove(moveType = moveType, attackAction = attackActionPreview!!.attackAction.id, actionTarget = actionTargets[0].id)
    }
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



fun Game.availableMoves(computerPlayerId: PlayerId) : List<AvailableComputerMove> {
    val computerPlayer = this.findPlayer(computerPlayerId).get()
    val availableMoves = mutableListOf<AvailableComputerMove>()
    val availableSupports = computerPlayer.supportActions.filter { it.isAvailable(curTime) }
    for (support in availableSupports) {
        val targets = this.findTargets(computerPlayerId, support.targetType)
        if (support.targetType == TargetType.ALL_ALLIES) {
            val supportMove = AvailableComputerMove(ComputerMoveType.SUPPORT, supportAction = support, actionTargets = targets)
            availableMoves.add(supportMove)
        } else {
            for (target in targets) {
                val supportMove = AvailableComputerMove(ComputerMoveType.SUPPORT, supportAction = support, actionTargets = listOf(target))
                availableMoves.add(supportMove)
            }
        }
    }

    val availableAttacks = computerPlayer.attackActions.filter { it.isAvailable(curTime) }
    for (attack in availableAttacks) {
        val targets = this.findTargets(computerPlayerId, attack.targetType)
        if (attack.targetType == TargetType.ALL_ENEMIES) {
            val preview = attack.simulate(computerPlayer, targets)
            val attackMove = AvailableComputerMove(ComputerMoveType.ATTACK, attackActionPreview = preview, actionTargets = targets)
            availableMoves.add(attackMove)
        } else {
            for (target in targets) {
                val previewTarget = listOf(target)
                val preview = attack.simulate(computerPlayer, previewTarget)
                val attackMove = AvailableComputerMove(ComputerMoveType.ATTACK, attackActionPreview = preview, actionTargets = previewTarget)
                availableMoves.add(attackMove)
            }
        }
    }
    return availableMoves
}

interface ComputerMoveAlgorithm {
    fun computeNextMove(player: Player,
                        playerTeam: Team,
                        opponentTeams: List<Team>,
                        availableMoves: List<AvailableComputerMove>,
                        numberGenerator: NumberGenerator) : ComputerMove
}

enum class ComputerMoveChoiceType {
    RANDOM,
    DAMAGE,
    DAMAGE_BEST3
}

interface ComputerMoveWeightEvaluator {
    fun computeWeightedMoves(player: Player,
                             playerTeam: Team,
                             opponentTeams: List<Team>,
                             availableMoves: List<AvailableComputerMove>) : List<WeightedComputerMove>
}

class WeightBaseMoveAlgorithm (
    private val weightEvaluator: ComputerMoveWeightEvaluator,
    private val maxChoiceCount: Int? = null
) : ComputerMoveAlgorithm  {
    override fun computeNextMove(
        player: Player,
        playerTeam: Team,
        opponentTeams: List<Team>,
        availableMoves: List<AvailableComputerMove>,
        numberGenerator: NumberGenerator
    ): ComputerMove {
        val weightedMoves = weightEvaluator.computeWeightedMoves(player, playerTeam, opponentTeams, availableMoves)
        val filteredWeightMoves = if (maxChoiceCount != null) {
            weightedMoves.sortedBy { it.weight }.reversed().subList(0, maxChoiceCount.coerceAtMost(weightedMoves.size))
        } else {
            weightedMoves
        }

        val totalWeight = filteredWeightMoves.stream().map { act -> act.weight }.reduce { t, u -> t + u }
        val selectDice = numberGenerator.roll(0, totalWeight.orElse(1))
        var weightCount = 0
        for (availableAction in filteredWeightMoves) {
            weightCount += availableAction.weight
            if (selectDice < weightCount) {
                return availableAction.computerMove
            }
        }
        return filteredWeightMoves.last().computerMove
    }
}

class RandomWeightMoveEvaluator : ComputerMoveWeightEvaluator {

    override fun computeWeightedMoves(
        player: Player,
        playerTeam: Team,
        opponentTeams: List<Team>,
        availableMoves: List<AvailableComputerMove>
    ): List<WeightedComputerMove> {
        val weightedMoves = mutableListOf<WeightedComputerMove>()
        val allyCount = playerTeam.players.count { it.isAlive }
        val enemyCount = opponentTeams.flatMap { it.players }.count { it.isAlive }
        val baseAttackWeight = 30 / enemyCount
        val baseSupportWeight = 15 / allyCount
        for (possibleMove in availableMoves) {
            when (possibleMove.moveType) {
                ComputerMoveType.ATTACK -> {
                    weightedMoves.add(WeightedComputerMove(possibleMove.toComputerMove(), baseAttackWeight * enemyCount))
                }
                ComputerMoveType.SUPPORT -> {
                    weightedMoves.add(WeightedComputerMove(possibleMove.toComputerMove(), baseSupportWeight))
                }
            }
        }
        return weightedMoves
    }

}

fun AttackActionPreview.computeAttackDamageScore() : Int {
    var score = 0
    for (targetedPreview in this.targetedPreviews) {
        val cappedMinDamage = targetedPreview.minDamage.coerceAtMost(targetedPreview.target.hitPoints)
        val cappedMaxDamage = targetedPreview.maxDamage.coerceAtMost(targetedPreview.target.hitPoints)

        val mediumDamage = (cappedMinDamage + cappedMaxDamage) / 2

        val mediumDamageWithCrit = mediumDamage + ((mediumDamage * targetedPreview.critical.coerceAtLeast(0).coerceAtMost(100)) / 200)

        score += (mediumDamageWithCrit * targetedPreview.hit.coerceAtLeast(0).coerceAtMost(100)) / 100
    }



    return ((score * 100) / this.attackAction.delay.coerceAtLeast(1)).coerceAtLeast(1)
}

class DamageBasedComputerMoveWeightEvaluator : ComputerMoveWeightEvaluator {
    override fun computeWeightedMoves(
        player: Player,
        playerTeam: Team,
        opponentTeams: List<Team>,
        availableMoves: List<AvailableComputerMove>
    ): List<WeightedComputerMove> {
        val weightedMoves = mutableListOf<WeightedComputerMove>()
        for (move in availableMoves) {
            when(move.moveType) {
                ComputerMoveType.ATTACK -> {
                    val attackWeight = move.attackActionPreview!!.computeAttackDamageScore()
                    weightedMoves.add(WeightedComputerMove(move.toComputerMove(), attackWeight))
                }
                ComputerMoveType.SUPPORT -> {
                    if (move.actionTargets[0].id == player.id) {
                        val supportEffectType = move.supportAction!!.id.mainEffectType()
                        val opponentCount = opponentTeams.flatMap { it.players }.count { it.isAlive }
                        val teamSize = playerTeam.players.size
                        if (supportEffectType == StatusEffectType.HIT) {
                            val weight = ((50 - player.hit) * opponentCount * opponentCount) / teamSize
                            weightedMoves.add(WeightedComputerMove(move.toComputerMove(), weight))
                        } else if (supportEffectType == StatusEffectType.ATTACK) {
                            val weight = ((50 - player.attack) * opponentCount * opponentCount) / teamSize
                            weightedMoves.add(WeightedComputerMove(move.toComputerMove(), weight))
                        }
                    }
                }
            }
        }
        return weightedMoves
    }


}

fun ComputerMoveChoiceType.getAlgorithm() : ComputerMoveAlgorithm {
    return when(this) {
        ComputerMoveChoiceType.RANDOM -> WeightBaseMoveAlgorithm(weightEvaluator = RandomWeightMoveEvaluator())
        ComputerMoveChoiceType.DAMAGE -> WeightBaseMoveAlgorithm(weightEvaluator = DamageBasedComputerMoveWeightEvaluator())
        ComputerMoveChoiceType.DAMAGE_BEST3 -> WeightBaseMoveAlgorithm(weightEvaluator = DamageBasedComputerMoveWeightEvaluator(), maxChoiceCount = 3)
    }
}