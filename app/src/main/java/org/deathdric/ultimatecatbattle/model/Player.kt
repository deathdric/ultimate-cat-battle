package org.deathdric.ultimatecatbattle.model

data class Player(val maxHitPoints: Int,
                  val id: PlayerId,
                  val playerType: PlayerType,
                  val attackActions: List<AttackAction> = listOf(),
                  val supportActions: List<SupportAction> = listOf()
) {
    var hitPoints = maxHitPoints
        private set;

    private var statusEffects : MutableList<StatusModifier> = ArrayList()
    val isAlive get() = hitPoints > 0
    val attack get()  = computeStat(StatusEffectType.ATTACK)
    val defense get()  = computeStat(StatusEffectType.DEFENSE)
    val hit get()  = computeStat(StatusEffectType.HIT)
    val avoid get()  = computeStat(StatusEffectType.AVOID)
    val critical get() = computeStat(StatusEffectType.CRITICAL)

    var nextTime = 0
        private set;

    fun applyDelay(delay: Int) {
        nextTime += delay
    }

    fun applyDamage(damage: Int) {
        var newHitPoints = hitPoints - damage
        if (newHitPoints < 0) {
            newHitPoints = 0
        }
        hitPoints = newHitPoints
    }

    fun applyHealing(healing: Int) {
        var newHitPoints = hitPoints + healing
        if (newHitPoints > maxHitPoints) {
            newHitPoints = maxHitPoints
        }
        hitPoints = newHitPoints
    }

    fun addEffect(newEffect: StatusModifier) {
        statusEffects.add(newEffect)
    }


    fun applyEffectExpiration(curTime : Int) {
        val newEffects = ArrayList<StatusModifier>()
        for (curEffect in statusEffects) {
            if (curEffect.expires > curTime) {
                newEffects.add(curEffect)
            }
        }
        statusEffects = newEffects
    }

    private fun computeStat(
        effectType: StatusEffectType
    ) : Int {
        var total = 0
        for (effect in statusEffects) {
            total += effectType.statusFunction.invoke(effect)
        }
        if (total > effectType.maxValue) {
            total = effectType.maxValue;
        }
        if (total < effectType.minValue) {
            total = effectType.minValue;
        }
        return total;
    }
}
