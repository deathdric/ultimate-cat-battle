package org.deathdric.ultimatecatbattle.model

import org.deathdric.ultimatecatbattle.data.AttackRepository
import org.deathdric.ultimatecatbattle.data.PlayerRepository
import org.deathdric.ultimatecatbattle.data.SupportRepository
import org.deathdric.ultimatecatbattle.vm.CharacterAllocation
import org.deathdric.ultimatecatbattle.vm.PlayerKey
import org.deathdric.ultimatecatbattle.vm.TeamAllocation

fun CharacterAllocation.toPlayerKey() : PlayerKey {
    if (this.playerType == PlayerType.COMPUTER) {
        return PlayerKey(this.playerType, this.playerId)
    }
    return PlayerKey(this.playerType, null)
}

fun retrieveTeamId(playerId: PlayerId,
                   characterAllocations: Map<PlayerId, CharacterAllocation>,
                   teamAllocations: Map<PlayerKey, TeamAllocation>) : TeamId? {
    val characterAllocation = characterAllocations[playerId] ?: return null
    val playerKey = if (characterAllocation.playerType.isComputer) PlayerKey(characterAllocation.playerType, playerId) else PlayerKey(characterAllocation.playerType)
    val teamAllocation = teamAllocations[playerKey] ?: return null
    return teamAllocation.teamId
}

fun computeOpponentCount(playerId: PlayerId,
    characterAllocations: Map<PlayerId, CharacterAllocation>,
    teamAllocations: Map<PlayerKey, TeamAllocation>) : Int {
    val teamId = retrieveTeamId(playerId, characterAllocations, teamAllocations) ?: 1
    val otherTeams = teamAllocations.entries.stream().filter { it.value.teamId != teamId }
    var opponentCount = 0
    for (otherTeam in otherTeams) {
        opponentCount += characterAllocations.entries.count { it.value.enable && it.value.toPlayerKey() == otherTeam.key }
    }
    return opponentCount
}

fun computeTeamRatio(playerId: PlayerId,
                     characterAllocations: Map<PlayerId, CharacterAllocation>,
                     teamAllocations: Map<PlayerKey, TeamAllocation>) : Int {
    val teamId = retrieveTeamId(playerId, characterAllocations, teamAllocations) ?: 1
    val teamCount = mutableMapOf<TeamId, Int>();
    for (teamEntry in TeamId.entries) {
        var characterCount = 0
        val selectedTeamAllocations = teamAllocations.entries.filter { it.value.teamId == teamEntry }
        for (selectedTeamAllocation in selectedTeamAllocations) {
            characterCount += characterAllocations.values.count { it.enable && (it.toPlayerKey() == selectedTeamAllocation.key) }
        }
        teamCount[teamEntry] = characterCount
    }
    val maxTeamCount = teamCount.values.max()
    val curTeamCount = teamCount[teamId] ?: 1
    return maxTeamCount / curTeamCount.coerceAtLeast(1)
}

fun createNewPlayer(playerId: PlayerId, playerType: PlayerType, characterCount: Int, teamRatio: Int, opponentCount: Int, startTime: Int, numberGenerator: NumberGenerator = RandomNumberGenerator(), power: Power = Power.NORMAL) : Player{
    val playerTemplate = PlayerRepository.getPlayer(playerId)
    var maxHitPoints = playerTemplate.maxHitPoints * teamRatio
    var baseHit = when(teamRatio) {
        2 -> 5
        3 -> 10
        else -> 0
    }
    var baseAvoid = when(teamRatio) {
        2 -> 5
        3 -> 10
        else -> 0
    }
    var baseDefense = when(teamRatio) {
        2 -> 5
        3 -> 10
        else -> 0
    }

    if (power == Power.WEAK) {
        maxHitPoints = (maxHitPoints * 3) / 4
        baseAvoid -= 5
        baseHit -= 5
    } else if (power == Power.STRONG) {
        maxHitPoints = (maxHitPoints * 5) / 4
        baseAvoid += 5
        baseHit += 5
    } else if (power == Power.VERY_STRONG) {
        maxHitPoints = (maxHitPoints * 3) / 2
        baseAvoid += 10
        baseHit += 10
    }

    val attackActionList = playerTemplate.attackActions.map { AttackRepository.getAttack(it).toAttackAction(teamRatio, opponentCount) }.toList()
    val supportActionList = playerTemplate.supportActions.map { SupportRepository.getSupport(it).toSupportAction() }.toList()
    val algo = if (playerType.isComputer && power != Power.WEAK) {

        val availableAlgos = if (teamRatio > 1)  {
            listOf(ComputerMoveChoiceType.ACCURACY_80, ComputerMoveChoiceType.DAMAGE_BEST3)
        } else if (opponentCount == 1 && characterCount > 2) {
            listOf(ComputerMoveChoiceType.ACCURACY_80, ComputerMoveChoiceType.ACCURACY_EFFECTS_80)
        } else {
            ComputerMoveChoiceType.entries
        }

        val algoIndex = numberGenerator.roll(0, availableAlgos.size)
        availableAlgos[algoIndex]
    } else ComputerMoveChoiceType.RANDOM
    val player = Player(maxHitPoints = maxHitPoints, id = playerId, playerType = playerType,
        attackActions = attackActionList, supportActions = supportActionList, algo,
        baseHit = baseHit, baseAvoid = baseAvoid, baseDefense = baseDefense)
    player.applyDelay(startTime)
    return player
}

fun createNewGame(characterAllocations: Map<PlayerId, CharacterAllocation>,
                  teamAllocations: Map<PlayerKey, TeamAllocation>,
                  numberGenerator: NumberGenerator = RandomNumberGenerator(),
                  powerAllocation: Map<PlayerType, Power> = mapOf<PlayerType, Power>()
                  ) : Game {
    val teams = mutableMapOf<TeamId, MutableList<Player>>()
    val characterCount = characterAllocations.values.count { it.enable }
    for (playerEntry in characterAllocations) {
        if (!playerEntry.value.enable) {
            continue
        }
        val power = powerAllocation[playerEntry.value.playerType] ?: Power.NORMAL
        val teamId = retrieveTeamId(playerEntry.key, characterAllocations, teamAllocations) ?: continue
        val playerList = teams.computeIfAbsent(teamId) { mutableListOf() }
        val teamRatio = computeTeamRatio(playerEntry.key, characterAllocations, teamAllocations)
        val opponentCount = computeOpponentCount(playerEntry.key, characterAllocations, teamAllocations)
        val startTime = numberGenerator.roll(0, 20)
        val player = createNewPlayer(playerEntry.key, playerEntry.value.playerType, characterCount, teamRatio, opponentCount, startTime, numberGenerator, power)
        playerList.add(player)
    }
    return Game(teams = teams.entries.map { Team(it.value.toList(), it.key )})
}