package org.deathdric.ultimatecatbattle.model

import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.Player.Companion.MAX_STAT_VALUE
import org.deathdric.ultimatecatbattle.model.Player.Companion.MIN_STAT_VALUE
import kotlin.random.Random

fun AttackAction.apply(attacker : Player, defender: Player) : AttackResult {
    attacker.applyDelay(this.delay)
    val attackSimulation = this.simulate(attacker, defender)
    val hitDice = Random.nextInt(0, 100)
    val success = hitDice <= attackSimulation.successChance
    if (!success) {
        return AttackResult(success = false, damage = 0, critical = false)
    }
    var damage = Random.nextInt(attackSimulation.minDamage, attackSimulation.maxDamage + 1)
    val critDice = Random.nextInt(0, 100)
    val critical = critDice <= attackSimulation.critChance
    if (critical) {
        damage = (damage * 3) / 2
    }
    defender.applyDamage(damage)
    return AttackResult(success = true, damage = damage, critical = critical)
}

fun AttackAction.simulate(attacker: Player, defender: Player) : AttackSimulationResult {
    var minDamage = (this.minDamage * (100 + attacker.attack - defender.defense)) / 100
    if (minDamage < 1) {
        minDamage = 1
    }
    val minDamageEnough = minDamage >= defender.hitPoints
    var maxDamage = (this.maxDamage * (100 + attacker.attack - defender.defense)) / 100
    if (maxDamage < 1) {
        maxDamage = 1
    }
    val maxDamageEnough = maxDamage >= defender.hitPoints
    val hitChance = this.hit + attacker.hit - defender.avoid
    val critChance = this.critical + attacker.critical
    return AttackSimulationResult(
        minDamage = minDamage,
        minDamageEnough = minDamageEnough,
        maxDamage = maxDamage,
        maxDamageEnough = maxDamageEnough,
        successChance = hitChance,
        critChance = critChance
    )
}

fun SupportAction.apply(player: Player, curTime : Int) {
    player.applyDelay(this.delay)
    val statusEffect = StatusEffect(
        attackBonus = this.attackBonus,
        defenseBonus = this.defenseBonus,
        hitBonus = this.hitBonus,
        avoidBonus = this.avoidBonus,
        criticalBonus = this.criticalBonus,
        expires = curTime + this.duration + this.delay
    )
    player.addEffect(statusEffect)
}

fun isStatCapped(currentValue: Int, effectValue: Int, minValue: Int = MIN_STAT_VALUE, maxValue: Int = MAX_STAT_VALUE) : Boolean {
    val computedValue = currentValue + effectValue
    return effectValue >= maxValue || effectValue <= minValue
}

fun SupportAction.computeEffects(player: Player) : List<SupportEffect> {
    val effectList = mutableListOf<SupportEffect>()
    if (this.attackBonus != 0) {
        effectList.add(SupportEffect(this.attackBonus, R.string.stat_attack, R.drawable.attack, isStatCapped(player.attack, this.attackBonus)))
    }
    if (this.defenseBonus != 0) {
        effectList.add(SupportEffect(this.defenseBonus, R.string.stat_defense, R.drawable.defense, isStatCapped(player.defense, this.defenseBonus)))
    }
    if (this.hitBonus != 0) {
        effectList.add(SupportEffect(this.hitBonus, R.string.stat_hit, R.drawable.hit, isStatCapped(player.hit, this.hitBonus)))
    }
    if (this.avoidBonus != 0) {
        effectList.add(SupportEffect(this.avoidBonus, R.string.stat_avoid, R.drawable.avoid2, isStatCapped(player.avoid, this.avoidBonus)))
    }
    if (this.criticalBonus != 0) {
        effectList.add(SupportEffect(this.criticalBonus, R.string.stat_crit, R.drawable.critical, isStatCapped(player.critical, this.criticalBonus)))
    }
    return effectList
}