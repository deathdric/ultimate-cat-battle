package org.deathdric.ultimatecatbattle.vm

import org.deathdric.ultimatecatbattle.model.Power
import org.deathdric.ultimatecatbattle.model.PlayerId
import org.deathdric.ultimatecatbattle.model.PlayerType
import org.deathdric.ultimatecatbattle.model.TeamId
import org.deathdric.ultimatecatbattle.model.allComputerTeams
import org.deathdric.ultimatecatbattle.model.allPlayerTeams
import org.deathdric.ultimatecatbattle.model.allPlayerTypes
import java.util.stream.Collectors

data class PlayerKey(val playerType: PlayerType, val playerId: PlayerId? = null)

data class TeamAllocation(val playerKey: PlayerKey, val teamId: TeamId)

data class TeamAllocationState(val teamAllocation: TeamAllocation, val availableTeams: List<TeamId>)

data class TeamAllocationValidation(val validated: Boolean)

data class GlobalTeamAllocationState(val teams: List<TeamAllocationState>, val validation: TeamAllocationValidation)

fun createPowerAllocations(characterAllocations: List<CharacterAllocation>) : MutableMap<PlayerType, Power> {
    val powerAllocations = mutableMapOf<PlayerType, Power>();
    val distinctPlayers = characterAllocations.filter { it.enable }.map { it.playerType }.distinct()
    for (playerType in distinctPlayers) {
        powerAllocations[playerType] = Power.NORMAL
    }
    return powerAllocations
}

fun createTeamAllocations(characterAllocations: List<CharacterAllocation>, playerCount : Int) : MutableMap<PlayerKey, TeamAllocation> {
    val computerPlayers = characterAllocations.stream().filter { cal -> cal.playerType == PlayerType.COMPUTER && cal.enable }.collect(
        Collectors.toList())
    val computerCount = computerPlayers.count()
    val playerList = allPlayerTypes(playerCount)
    val teamAllocations = mutableMapOf<PlayerKey, TeamAllocation>()
    val availableComputerTeams = allComputerTeams(computerCount)
    val availablePlayerTeams = allPlayerTeams(playerCount)
    for (playerIndex in 1..playerCount) {
        val playerKey = PlayerKey(playerList[playerIndex - 1])
        teamAllocations[playerKey] = TeamAllocation(playerKey, availablePlayerTeams[playerIndex - 1])
    }
    for (computerIndex in 1..computerCount) {
        val playerKey = PlayerKey(PlayerType.COMPUTER, computerPlayers[computerIndex - 1].playerId)
        teamAllocations[playerKey] = TeamAllocation(playerKey, availableComputerTeams[computerIndex - 1])
    }
    return teamAllocations
}

fun validateTeamAllocation(teamAllocations: Map<PlayerKey, TeamAllocation>) : TeamAllocationValidation {
    val teamCount = teamAllocations.values.stream().map { it.teamId }.distinct().count()
    return TeamAllocationValidation(validated = (teamCount > 1))
}

fun createGlobalTeamAllocationState(teamAllocations: Map<PlayerKey, TeamAllocation>, playerCount : Int) : GlobalTeamAllocationState {
    val teamAllocationsState = mutableListOf<TeamAllocationState>()
    val computerCount = teamAllocations.keys.stream().filter { cal -> cal.playerType == PlayerType.COMPUTER }.count().toInt()
    for (teamAllocation in teamAllocations.values) {
        if (teamAllocation.playerKey.playerType == PlayerType.COMPUTER) {
            teamAllocationsState.add(TeamAllocationState(teamAllocation, allComputerTeams(computerCount)))
        } else {
            teamAllocationsState.add(TeamAllocationState(teamAllocation, allPlayerTeams(playerCount)))
        }
    }

    return GlobalTeamAllocationState(teamAllocationsState, validateTeamAllocation(teamAllocations))
}