package org.deathdric.ultimatecatbattle.model

import org.deathdric.ultimatecatbattle.ui.mainEffectType

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
    DAMAGE_BEST3,
    ACCURACY_80,
    DEFENSE_80
}

interface ComputerMoveWeightEvaluator {
    fun computeWeightedMoves(player: Player,
                             playerTeam: Team,
                             opponentTeams: List<Team>,
                             availableMoves: List<AvailableComputerMove>) : List<WeightedComputerMove>
}

class WeightBaseMoveAlgorithm (
    private val weightEvaluator: ComputerMoveWeightEvaluator,
    private val maxChoiceCount: Int? = null,
    private val minWeightThreshold: Int? = null
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
        } else if (minWeightThreshold != null) {
            val maxWeight = weightedMoves.maxOf { it.weight }
            val minThreshold = (maxWeight * minWeightThreshold) / 100
            weightedMoves.filter { it.weight >= minThreshold }
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

fun AttackActionPreview.computeAttackDamageScore(squareHitRatio: Boolean = false, includeEffects: Boolean = false) : Int {
    var score = 0
    for (targetedPreview in this.targetedPreviews) {
        val cappedMinDamage = targetedPreview.minDamage.coerceAtMost(targetedPreview.target.hitPoints)
        val cappedMaxDamage = targetedPreview.maxDamage.coerceAtMost(targetedPreview.target.hitPoints)

        val mediumDamage = (cappedMinDamage + cappedMaxDamage) / 2

        val mediumDamageWithCrit = mediumDamage + ((mediumDamage * targetedPreview.critical.coerceAtLeast(0).coerceAtMost(100)) / 200)

        val damageBeforeHit : Int
        if (includeEffects) {

            if (this.attackAction.statusEffect != null) {
                var bonus = 0
                for (effect in attackAction.statusEffect.effects) {
                    val effectBonus = when(effect.effectType) {
                        StatusEffectType.ATTACK -> (targetedPreview.target.attack - effect.effectValue + 25)
                        StatusEffectType.AVOID -> (targetedPreview.target.avoid - effect.effectValue + 25)
                        StatusEffectType.HIT -> (targetedPreview.target.hit - effect.effectValue + 25)
                        StatusEffectType.DEFENSE -> (targetedPreview.target.defense - effect.effectValue + 25)
                        StatusEffectType.CRITICAL -> (targetedPreview.target.critical - effect.effectValue + 25)
                    }
                    bonus += effectBonus.coerceAtLeast(0)
                }
                damageBeforeHit = mediumDamageWithCrit + ((bonus * attackAction.statusEffect.chance) / 100)
            } else if (this.attackAction.delayEffect != null) {
                damageBeforeHit = mediumDamageWithCrit + ((this.attackAction.delayEffect.delay * this.attackAction.delayEffect.chance) / 100)
            } else {
                damageBeforeHit = mediumDamageWithCrit
            }
        } else {
            damageBeforeHit = mediumDamageWithCrit
        }

        val targetHitRatio = targetedPreview.hit.coerceAtLeast(0).coerceAtMost(100)

        val damageScore = (damageBeforeHit * targetHitRatio) / 100

        if (squareHitRatio) {
            score += (damageScore * targetHitRatio) / 100
        } else {
            score += damageScore
        }
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
                        val weight: Int
                        if (supportEffectType == StatusEffectType.HIT) {
                            weight = ((50 - player.hit) * opponentCount * opponentCount) / teamSize
                        } else if (supportEffectType == StatusEffectType.ATTACK) {
                            weight = ((50 - player.attack) * opponentCount * opponentCount) / teamSize
                        } else {
                            weight = 1
                        }
                        weightedMoves.add(WeightedComputerMove(move.toComputerMove(), weight.coerceAtLeast(1)))
                    }
                }
            }
        }
        return weightedMoves
    }
}

class AccuracyBasedComputerMoveWeightEvaluator : ComputerMoveWeightEvaluator {
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
                    val attackWeight = move.attackActionPreview!!.computeAttackDamageScore(squareHitRatio = true)
                    weightedMoves.add(WeightedComputerMove(move.toComputerMove(), attackWeight))
                }
                ComputerMoveType.SUPPORT -> {
                    if (move.actionTargets[0].id == player.id) {
                        val supportEffectType = move.supportAction!!.id.mainEffectType()
                        val opponents = opponentTeams.flatMap { it.players }.filter { it.isAlive }
                        val opponentCount = opponents.size
                        val teamSize = playerTeam.players.size
                        val weight: Int
                        if (supportEffectType == StatusEffectType.HIT) {
                            weight = ((50 - player.hit) * opponentCount * opponentCount) / teamSize

                        } else if (supportEffectType == StatusEffectType.ATTACK) {
                            weight = ((50 - player.attack) * opponentCount * opponentCount) / teamSize
                        } else if (supportEffectType == StatusEffectType.DEFENSE) {
                            val opponentOffensiveStatus =
                                opponents.sumOf { 2 * (it.hit - player.avoid) + it.attack - player.defense }
                            val hitPointRatio = ((player.hitPoints * 100) / player.maxHitPoints).coerceAtLeast(1)
                            weight = opponentCount * (100 - hitPointRatio) + opponentOffensiveStatus
                        } else {
                            weight = 1
                        }
                        weightedMoves.add(WeightedComputerMove(move.toComputerMove(), weight.coerceAtLeast(1)))
                    }
                }
            }
        }
        return weightedMoves
    }
}

class DefensiveMoveWeightEvaluator : ComputerMoveWeightEvaluator {
    override fun computeWeightedMoves(
        player: Player,
        playerTeam: Team,
        opponentTeams: List<Team>,
        availableMoves: List<AvailableComputerMove>
    ): List<WeightedComputerMove> {
        val weightedMoves = mutableListOf<WeightedComputerMove>()
        val opponents = opponentTeams.flatMap { it.players }.filter { it.isAlive }
        val opponentCount = opponents.size
        for (move in availableMoves) {
            when(move.moveType) {
                ComputerMoveType.SUPPORT -> {
                    if (move.actionTargets[0].id == player.id) {
                        val weight: Int
                        val mainEffect = move.supportAction!!.id.mainEffectType()
                        if (mainEffect == StatusEffectType.DEFENSE) {
                            weight = 200 * opponentCount
                        } else if (mainEffect == StatusEffectType.HIT) {
                            weight = 190 * opponentCount
                        } else {
                            weight = 1
                        }
                        weightedMoves.add(WeightedComputerMove(move.toComputerMove(), weight))
                    }
                }
                ComputerMoveType.ATTACK -> {
                    val attackWeight = move.attackActionPreview!!.computeAttackDamageScore(squareHitRatio = true, includeEffects = true)
                    weightedMoves.add(WeightedComputerMove(move.toComputerMove(), attackWeight))
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
        ComputerMoveChoiceType.ACCURACY_80 -> WeightBaseMoveAlgorithm(weightEvaluator =  AccuracyBasedComputerMoveWeightEvaluator(), minWeightThreshold = 80)
        ComputerMoveChoiceType.DEFENSE_80 -> WeightBaseMoveAlgorithm(weightEvaluator =  DefensiveMoveWeightEvaluator(), minWeightThreshold = 80)
    }
}