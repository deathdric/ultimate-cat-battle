package org.deathdric.ultimatecatbattle.model

class Player(val template : PlayerTemplate) {

    var hitPoints : Int = template.maxHp
        private set

    private var statusEffects : MutableList<StatusEffect> = ArrayList()
    val isAlive get() = hitPoints > 0
    val attack get()  = computeStat(statusFunction = {it.attackBonus})
    val defense get()  = computeStat(statusFunction = {it.defenseBonus})
    val hit get()  = computeStat(statusFunction = {it.hitBonus})
    val avoid get()  = computeStat(statusFunction = {it.avoidBonus})
    val critical get() = computeStat(statusFunction = {it.criticalBonus})
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

    fun addEffect(newEffect: StatusEffect) {
        statusEffects.add(newEffect)
    }

    fun applyEffectExpiration(curTime : Int) {
        val newEffects = ArrayList<StatusEffect>()
        for (curEffect in statusEffects) {
            if (curEffect.expires > curTime) {
                newEffects.add(curEffect)
            }
        }
        statusEffects = newEffects
    }

    private fun computeStat(
        statusFunction : (StatusEffect) -> Int,
        minValue : Int = -50,
        maxValue : Int = 50
    ) : Int {
        var total = 0
        for (effect in statusEffects) {
            total += statusFunction(effect)
        }
        if (total > maxValue) {
            total = maxValue;
        }
        if (total < minValue) {
            total = minValue;
        }
        return total;
    }

}