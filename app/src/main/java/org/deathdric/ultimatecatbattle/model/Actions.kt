package org.deathdric.ultimatecatbattle.model

import kotlin.random.Random

fun AttackAction.apply(attacker : Player, defender: Player) : AttackResult {
    attacker.applyDelay(this.delay)
    val hitDice = Random.nextInt(0, 100)
    val success = hitDice <= (this.hit + attacker.hit - defender.avoid)
    if (!success) {
        return AttackResult(success = false, damage = 0, critical = false)
    }
    var damage = Random.nextInt(this.minDamage, this.maxDamage + 1)
    damage = (damage * (100 + attacker.attack - defender.defense)) / 100
    if (damage < 1) {
        damage = 1
    }
    val critDice = Random.nextInt(0, 100)
    val critical = critDice <= (this.critical + attacker.critical)
    if (critical) {
        damage = (damage * 3) / 2
    }
    defender.applyDamage(damage)
    return AttackResult(success = true, damage = damage, critical = critical)
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