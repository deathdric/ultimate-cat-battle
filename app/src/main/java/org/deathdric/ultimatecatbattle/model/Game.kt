package org.deathdric.ultimatecatbattle.model

import java.util.Optional
import java.util.stream.Collectors

class Game(val teams: List<Team>) {

    var curTime = 0
        private set

    val players: List<Player> = teams.stream().flatMap { it.players.stream() }.collect(Collectors.toList())

    var curActivePlayer : Player? = null
        private set

    val isGameOver get() = run {
        teams.stream().filter { it.isAlive } .count() == 1L
                || teams.stream().filter { !it.id.isComputer && it.isAlive }.count() == 0L
    }

    fun findPlayer(playerId: PlayerId) : Optional<Player> {
        return players.stream().filter { it.id == playerId }.findFirst()
    }

    fun findTeam(player: Player) : Optional<Team> {
        return teams.stream().filter { it.players.contains(player)}.findFirst()
    }

    fun updateActivePlayer() : Boolean {
        val nextCurTime = players.stream().filter { it.isAlive}.map { it.nextTime }.sorted().findFirst().get()
        val eligiblePlayers = players.stream().filter { it.isAlive && it.nextTime == nextCurTime }.collect(Collectors.toList())
        curTime = nextCurTime
        if (curActivePlayer != null && eligiblePlayers.contains(curActivePlayer)) {
            return false
        } else {
            curActivePlayer = eligiblePlayers[0]
            return true
        }
    }

    fun canAutoTarget(targets: List<Player>, targetType: TargetType): Boolean {
        return when(targetType) {
            TargetType.ALL_OTHERS, TargetType.ALL_ALLIES, TargetType.ALL_ENEMIES, TargetType.SELF -> true
            TargetType.ONE_ALLY, TargetType.ONE_ENEMY -> targets.size == 1
        }
    }

    fun findTargets(playerId: PlayerId, targetType: TargetType): List<Player> {
        val player = findPlayer(playerId);
        if (!player.isPresent) { return listOf() }
        val foundTeam = findTeam(player.get())
        if (!foundTeam.isPresent) { return listOf() }
        var playerList = when(targetType) {
            TargetType.SELF -> listOf(player.get())
            TargetType.ONE_ALLY, TargetType.ALL_ALLIES -> foundTeam.get().players
            TargetType.ONE_ENEMY, TargetType.ALL_ENEMIES -> teams.stream()
                .filter { !it.equals(foundTeam.get()) }
                .flatMap { it.players.stream() }
                .collect(Collectors.toList())

            TargetType.ALL_OTHERS -> players.stream().filter { it.id != playerId }.collect(Collectors.toList())
        }
        return playerList.stream().filter { it.isAlive }.collect(Collectors.toList())
    }
}