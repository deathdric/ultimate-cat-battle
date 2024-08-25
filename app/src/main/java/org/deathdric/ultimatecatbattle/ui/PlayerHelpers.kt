package org.deathdric.ultimatecatbattle.ui

import org.deathdric.ultimatecatbattle.model.Player

fun Player.toStatus(active : Boolean = false) : PlayerStatus {

    val hitPointRatio = (this.hitPoints * 100) / this.template.maxHp
    val healthStatus = when(hitPointRatio) {
        in 0..33 -> HealthState.CRITICAL
        in 34..67 -> HealthState.WARNING
        else -> HealthState.OK
    }

    val playerIcon = if (this.isAlive) template.icon else template.iconLoss

    return PlayerStatus(
        name = this.template.name,
        icon = playerIcon,
        hitPoints = this.hitPoints,
        healthState = healthStatus,
        attack = this.attack,
        defense = this.defense,
        hit = this.hit,
        avoid = this.avoid,
        critical = this.critical,
        active = active,
        alive = this.isAlive
    )
}
