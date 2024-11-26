package org.deathdric.ultimatecatbattle.model

data class AttackStatusEffect (
    val chance: Int,
    val duration: Int,
    val effects: List<StatusEffect>
)

data class DelayEffect (
    val chance: Int,
    val delay: Int
)

enum class AttackSuccessMode {
    SUCCESS,
    CRITICAL,
    FAILURE
}

data class TargetedAttackActionResult (
    val target: Player,
    val successMode: AttackSuccessMode,
    val damage: Int = 0,
    val delay: Int = 0,
    val statusEffect: StatusModifier? = null
)

data class AttackActionResult (
    val targetedResults: List<TargetedAttackActionResult>
)

data class TargetedAttackActionPreview (
    val target: Player,
    val minDamage: Int,
    val maxDamage: Int,
    val hit: Int,
    val critical: Int
) {
    val minCanKill get() = run { minDamage >= target.hitPoints }
    val maxCanKill get() = run { maxDamage >= target.hitPoints }
}

data class AttackActionPreview (
    val player: Player,
    val attackAction: AttackAction,
    val targetedPreviews: List<TargetedAttackActionPreview>
)

fun AttackActionTemplate.toAttackAction(teamRatio: Int, opponentCount: Int) : AttackAction {
    val damageRange = this.damage[opponentCount] ?: this.defaultDamageRange
    var minDamage = damageRange.minDamage
    var maxDamage = damageRange.maxDamage
    if (teamRatio == 2) {
        minDamage = (damageRange.minDamage * 3) / 2
        maxDamage = (damageRange.maxDamage * 3) / 2
    } else if (teamRatio >= 3) {
        minDamage = damageRange.minDamage * 2
        maxDamage = damageRange.maxDamage * 2
    }
    return AttackAction(
        id = this.id,
        targetType = this.targetType,
        minDamage = minDamage,
        maxDamage = maxDamage,
        hit = this.hit,
        critical = this.critical,
        delay = this.delay,
        coolDown = this.coolDown,
        statusEffect = this.statusEffect,
        delayEffect = this.delayEffect
    )
}

data class AttackAction (
    val id: AttackActionId,
    val targetType: TargetType,
    val minDamage: Int,
    val maxDamage: Int,
    val hit: Int,
    val critical: Int,
    val delay: Int,
    val coolDown: Int = 0,
    val statusEffect: AttackStatusEffect? = null,
    val delayEffect: DelayEffect? = null
) {
    var nextAvailable = 0
        private set;

    fun isAvailable(curTime: Int) : Boolean {
        return nextAvailable <= curTime
    }

    fun updateAvailability(curTime: Int) {
        nextAvailable = curTime + coolDown
    }

    fun simulate(attacker: Player, targets: List<Player> ) : AttackActionPreview {
        val targetedList = mutableListOf<TargetedAttackActionPreview>()
        for (defender in targets) {
            var minDamage = (this.minDamage * (100 + attacker.attack - defender.defense)) / 100
            if (minDamage < 1) {
                minDamage = 1
            }
            var maxDamage = (this.maxDamage * (100 + attacker.attack - defender.defense)) / 100
            if (maxDamage < 1) {
                maxDamage = 1
            }
            val hitChance = this.hit + attacker.hit - defender.avoid
            val critChance = this.critical + attacker.critical
            targetedList.add(TargetedAttackActionPreview(defender, minDamage, maxDamage, hitChance, critChance))
        }
        return AttackActionPreview(attacker, this, targetedList)
    }

    fun apply(curTime: Int, attacker: Player, targets: List<Player>, rng: NumberGenerator = RandomNumberGenerator()) : AttackActionResult {
        attacker.applyDelay(this.delay)
        updateAvailability(curTime)
        val resultList = mutableListOf<TargetedAttackActionResult>()
        val preview = simulate(attacker, targets)
        for (previewTarget in preview.targetedPreviews) {
            val hitDice = rng.roll(0, 100)
            val success = hitDice <= previewTarget.hit
            if (success) {
                var damage = rng.roll(previewTarget.minDamage, previewTarget.maxDamage + 1)
                val critDice = rng.roll(0, 100)
                val critical = critDice <= previewTarget.critical
                val successMode = if (critical) AttackSuccessMode.CRITICAL else AttackSuccessMode.SUCCESS
                if (critical) {
                    damage = (damage * 3) / 2
                }
                previewTarget.target.applyDamage(damage)
                var delay = 0
                var statusResult: StatusModifier? = null

                if (delayEffect != null) {
                    val delayDice = rng.roll(0, 100)
                    if (delayDice <= delayEffect.chance) {
                        delay = delayEffect.delay
                        if (critical) {
                            delay = (delay * 3) / 2
                        }
                        previewTarget.target.applyDelay(delay)
                    }
                }

                if (statusEffect != null) {
                    val statusDice = rng.roll(0, 100)
                    if (statusDice <= statusEffect.chance) {
                        var duration = statusEffect.duration
                        if (critical) {
                            duration = (duration * 3) / 2
                        }
                        statusResult = generateStatusModifier(statusEffect.effects, curTime + duration)
                        previewTarget.target.addEffect(statusResult)
                    }
                }
                resultList.add(
                    TargetedAttackActionResult(previewTarget.target,
                        successMode,
                        damage,
                        delay,
                        statusResult)
                )

            } else {
                resultList.add(
                    TargetedAttackActionResult(previewTarget.target,
                    AttackSuccessMode.FAILURE)
                )
            }

        }

        return AttackActionResult(resultList)
    }
}
