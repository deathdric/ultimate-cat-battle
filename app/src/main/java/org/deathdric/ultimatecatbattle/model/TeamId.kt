package org.deathdric.ultimatecatbattle.model

enum class TeamId(val isComputer: Boolean) {
    BALL_GREEN(false),
    FISH_BLUE(false),
    CARROT_YELLOW(false),
    CHEESE_RED(false),
    STAR_PURPLE(true),
    MOON_BROWN(true),
    SUN_ORANGE(true)
}

fun allPlayerTeams(playerCount : Int) : List<TeamId> {
    return TeamId.entries.filter { !it.isComputer }.subList(0, playerCount)
}

fun allComputerTeams(computerCount: Int) : List<TeamId> {
    return TeamId.entries.filter { it.isComputer }.subList(0, computerCount)
}