package org.deathdric.ultimatecatbattle.model

enum class StatusEffectType (val minValue: Int, val maxValue: Int, val statusFunction : (StatusModifier) -> Int) {
    ATTACK(-50, 50,  { it.attack }),
    DEFENSE(-50, 50,  { it.defense }),
    HIT(-50, 50,  { it.hit }),
    AVOID(-50, 50,  { it.avoid }),
    CRITICAL(-50, 50,  { it.critical })
}