package org.deathdric.ultimatecatbattle.model

fun generateStatusModifier(effects: List<StatusEffect>, expires: Int) : StatusModifier {
    var attack = 0
    var defense = 0
    var avoid = 0
    var hit = 0
    var critical = 0
    for (effect in effects) {
        when(effect.effectType) {
            StatusEffectType.ATTACK -> { attack += effect.effectValue }
            StatusEffectType.DEFENSE -> { defense += effect.effectValue }
            StatusEffectType.HIT -> { hit += effect.effectValue }
            StatusEffectType.AVOID -> { avoid += effect.effectValue }
            StatusEffectType.CRITICAL -> { critical += effect.effectValue }
        }
    }
    return StatusModifier(attack, defense, hit, avoid, critical, expires)
}

fun StatusModifier.toStatusEffects() : List<StatusEffect> {
    val effectList = mutableListOf<StatusEffect>()
    for (effectType in StatusEffectType.entries) {
        val effectValue = effectType.statusFunction.invoke(this)
        if (effectValue != 0) {
            effectList.add(StatusEffect(effectType, effectValue));
        }
    }
    return effectList
}