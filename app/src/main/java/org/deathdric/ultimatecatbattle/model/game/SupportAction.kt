package org.deathdric.ultimatecatbattle.model.game

data class SupportActionResult (
    val targets: List<Player>,
    val statusEffect: List<StatusEffect>
)

fun SupportActionTemplate.toSupportAction(nextAvailable : Int = 0) : SupportAction {
    return SupportAction(
        this.id,
        this.targetType,
        ArrayList(this.selfEffects),
        ArrayList(this.otherEffects),
        this.delay,
        this.duration,
        this.coolDown,
        nextAvailable
    )
}

data class SupportAction (
    val id: SupportActionId,
    val targetType: TargetType,
    val selfEffects: List<StatusEffect>,
    val otherEffects: List<StatusEffect>,
    val delay: Int,
    val duration: Int,
    val coolDown: Int = 0,
    var nextAvailable: Int = 0
) {

    fun isAvailable(curTime: Int) : Boolean {
        return nextAvailable <= curTime
    }

    private fun updateAvailability(curTime: Int) {
        nextAvailable = curTime + coolDown
    }

    fun apply(curTime: Int, caster: Player, targets: List<Player>) : SupportActionResult {
        caster.applyDelay(this.delay)
        updateAvailability(curTime)
        val targetEffects = if (targets.size > 1) {
            this.otherEffects
        } else if (targets[0].id == caster.id) {
            this.selfEffects
        } else {
            this.otherEffects
        }

        for (targetPlayer in targets) {
            val statusModifier = generateStatusModifier(targetEffects, curTime + duration)
            targetPlayer.addEffect(statusModifier.copy())
        }
        return SupportActionResult(targets, targetEffects.toList())
    }
}