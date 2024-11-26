package org.deathdric.ultimatecatbattle.ui

import androidx.compose.ui.graphics.Color
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.AttackActionId
import org.deathdric.ultimatecatbattle.model.Player
import org.deathdric.ultimatecatbattle.model.PlayerId
import org.deathdric.ultimatecatbattle.model.PlayerType
import org.deathdric.ultimatecatbattle.model.StatusEffectType
import org.deathdric.ultimatecatbattle.model.SupportActionId
import org.deathdric.ultimatecatbattle.model.TeamId

fun PlayerId.aliveIcon() : Int {
    return when(this) {
        PlayerId.CAT -> R.drawable.cat2
        PlayerId.PENGUIN -> R.drawable.penguin1
        PlayerId.MOUSE -> R.drawable.mouse
        PlayerId.RABBIT -> R.drawable.rabbit
    }
}

fun PlayerId.deadIcon() : Int {
    return when(this) {
        PlayerId.CAT -> R.drawable.cat_loss
        PlayerId.PENGUIN -> R.drawable.penguin_loss
        PlayerId.MOUSE -> R.drawable.mouse_loss
        PlayerId.RABBIT -> R.drawable.rabbit_loss
    }
}

fun PlayerId.fullImage() : Int {
    return when(this) {
        PlayerId.CAT -> R.drawable.cat_full
        PlayerId.PENGUIN -> R.drawable.penguin_full
        PlayerId.MOUSE -> R.drawable.mouse_full
        PlayerId.RABBIT -> R.drawable.rabbit_full
    }
}

fun PlayerId.name(): Int {
    return when(this) {
        PlayerId.CAT -> R.string.cat
        PlayerId.PENGUIN -> R.string.penguin
        PlayerId.MOUSE -> R.string.mouse
        PlayerId.RABBIT -> R.string.rabbit
    }
}

fun PlayerType.icon() : Int {
    return when(this) {
        PlayerType.PLAYER1 -> R.drawable.player1
        PlayerType.PLAYER2 -> R.drawable.player2
        PlayerType.PLAYER3 -> R.drawable.player3
        PlayerType.PLAYER4 -> R.drawable.player4
        PlayerType.COMPUTER -> R.drawable.computer
    }
}

fun PlayerType.name() : Int {
    return when(this) {
        PlayerType.PLAYER1 -> R.string.player1
        PlayerType.PLAYER2 -> R.string.player2
        PlayerType.PLAYER3 -> R.string.player3
        PlayerType.PLAYER4 -> R.string.player4
        PlayerType.COMPUTER -> R.string.computer
    }
}

fun Int.playerStatColor(): Color {
    return if (this > 0) {
        Color(0xFF008000)
    } else if (this < 0) {
        Color.Red
    } else {
        Color.Black
    }
}

fun Player.hitPointsColor() : Color {
    val hitPointRatio = (this.hitPoints * 100)/this.maxHitPoints.coerceAtLeast(1)
    return if (hitPointRatio > 66) {
        Color(0xFF008000)
    } else if (hitPointRatio > 33) {
        Color(0xFF906000)
    } else {
        Color.Red
    }
}

fun TeamId.icon() : Int {
    return when(this) {
        TeamId.FISH_BLUE -> R.drawable.fish_blue
        TeamId.BALL_GREEN -> R.drawable.ball_green
        TeamId.CHEESE_RED -> R.drawable.cheese_red
        TeamId.MOON_BROWN -> R.drawable.moon_brown
        TeamId.SUN_ORANGE -> R.drawable.sun_orange
        TeamId.STAR_PURPLE ->R.drawable.star_purple
        TeamId.CARROT_YELLOW -> R.drawable.carrot_yellow
    }
}

fun TeamId.color() : Color {
    return when(this) {
        TeamId.FISH_BLUE -> Color(0xFF0000FF)
        TeamId.BALL_GREEN -> Color(0xFF00FF00)
        TeamId.CHEESE_RED -> Color(0xFFFF0000)
        TeamId.MOON_BROWN -> Color(0xFF805020)
        TeamId.SUN_ORANGE -> Color(0xFFFFA000)
        TeamId.STAR_PURPLE -> Color(0xFFA000FF)
        TeamId.CARROT_YELLOW -> Color(0xFFFFFF00)
    }
}


fun TeamId.name() : Int {
    return when(this) {
        TeamId.FISH_BLUE -> R.string.fish_blue
        TeamId.BALL_GREEN -> R.string.ball_green
        TeamId.CHEESE_RED -> R.string.cheese_red
        TeamId.MOON_BROWN -> R.string.moon_brown
        TeamId.SUN_ORANGE -> R.string.sun_orange
        TeamId.STAR_PURPLE ->R.string.star_purple
        TeamId.CARROT_YELLOW -> R.string.carrot_yellow
    }
}

fun AttackActionId.icon() : Int {
    return when(this) {
        AttackActionId.FINGER_OF_DEATH -> R.drawable.finger_of_death
        AttackActionId.DRAGON_FIST -> R.drawable.dragon_fist
        AttackActionId.BEAK_STRIKE -> R.drawable.beak_strike
        AttackActionId.GREAT_EXPLOSION -> R.drawable.great_explosion
        AttackActionId.SWEEPING_KICK -> R.drawable.sweeping_kick
        AttackActionId.HEART_STRIKE -> R.drawable.heart_strike
    }
}

fun AttackActionId.name() : Int {
    return when(this) {
        AttackActionId.FINGER_OF_DEATH -> R.string.attack_finger_of_death
        AttackActionId.DRAGON_FIST -> R.string.attack_dragon_fist
        AttackActionId.BEAK_STRIKE -> R.string.attack_beak_strike
        AttackActionId.GREAT_EXPLOSION -> R.string.attack_great_explosion
        AttackActionId.SWEEPING_KICK -> R.string.attack_sweeping_kick
        AttackActionId.HEART_STRIKE -> R.string.attack_heart_strike
    }
}

fun SupportActionId.name() : Int {
    return when(this) {
        SupportActionId.BERSERK -> R.string.support_berserk
        SupportActionId.GUARD -> R.string.support_guard
        SupportActionId.SPEED -> R.string.support_speed
    }
}

fun SupportActionId.icon() : Int {
    return when(this) {
        SupportActionId.BERSERK -> R.drawable.berserk
        SupportActionId.GUARD -> R.drawable.guard
        SupportActionId.SPEED -> R.drawable.speed
    }
}

fun SupportActionId.mainEffectType() : StatusEffectType {
    return when(this) {
        SupportActionId.BERSERK -> StatusEffectType.ATTACK
        SupportActionId.GUARD -> StatusEffectType.DEFENSE
        SupportActionId.SPEED -> StatusEffectType.HIT
    }
}

fun StatusEffectType.icon() : Int {
    return when(this) {
        StatusEffectType.HIT -> R.drawable.hit
        StatusEffectType.AVOID -> R.drawable.avoid2
        StatusEffectType.ATTACK -> R.drawable.attack
        StatusEffectType.DEFENSE -> R.drawable.defense
        StatusEffectType.CRITICAL -> R.drawable.critical
    }
}

fun StatusEffectType.name() : Int {
    return when(this) {
        StatusEffectType.HIT -> R.string.stat_hit
        StatusEffectType.AVOID -> R.string.stat_avoid
        StatusEffectType.ATTACK -> R.string.stat_attack
        StatusEffectType.DEFENSE -> R.string.stat_defense
        StatusEffectType.CRITICAL -> R.string.stat_crit
    }
}