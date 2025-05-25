package org.deathdric.ultimatecatbattle.data.save

import org.deathdric.ultimatecatbattle.data.game.AttackRepository
import org.deathdric.ultimatecatbattle.data.game.SupportRepository
import org.deathdric.ultimatecatbattle.model.game.AttackActionId
import org.deathdric.ultimatecatbattle.model.game.ComputerMoveChoiceType
import org.deathdric.ultimatecatbattle.model.game.Game
import org.deathdric.ultimatecatbattle.model.game.Player
import org.deathdric.ultimatecatbattle.model.game.PlayerId
import org.deathdric.ultimatecatbattle.model.game.PlayerType
import org.deathdric.ultimatecatbattle.model.game.StatusModifier
import org.deathdric.ultimatecatbattle.model.game.SupportActionId
import org.deathdric.ultimatecatbattle.model.game.Team
import org.deathdric.ultimatecatbattle.model.game.TeamId
import org.deathdric.ultimatecatbattle.model.game.computeTeamRatio
import org.deathdric.ultimatecatbattle.model.game.toAttackAction
import org.deathdric.ultimatecatbattle.model.game.toSupportAction
import org.deathdric.ultimatecatbattle.model.save.SavedAvailableAction
import org.deathdric.ultimatecatbattle.model.save.SavedGame
import org.deathdric.ultimatecatbattle.model.save.SavedPlayer
import org.deathdric.ultimatecatbattle.model.save.SavedStatusModifier
import org.deathdric.ultimatecatbattle.model.save.SavedTeam

object GameSaveConverter {

    fun exportGameData(game: Game) : SavedGame {
        val teams = game.teams.map { pl -> exportTeamData(pl) }

        return SavedGame(
            1,
            game.curTime,
            teams
        )
    }

    private fun exportTeamData(team: Team) : SavedTeam {
        val players = team.players.map { pl -> exportPlayerData(pl) }
        return SavedTeam(team.id.name, players)
    }

    private fun exportPlayerData(player: Player) : SavedPlayer {
        val computerMoveChoiceType = if (player.playerType.isComputer) {
            player.computerMoveChoiceType.name
        } else {
            null
        }
        val availableAttacks = player.attackActions.map { at -> SavedAvailableAction(at.id.name, at.nextAvailable) }
        val availableSupports = player.supportActions.map { sp -> SavedAvailableAction(sp.id.name, sp.nextAvailable) }
        val statusModifiers = player.statusEffects.map { md -> SavedStatusModifier(
            attack = md.attack,
            defense = md.defense,
            hit = md.hit,
            avoid = md.avoid,
            critical = md.critical,
            expires = md.expires)
        }

        return SavedPlayer(
            playerId = player.id.name,
            playerType = player.playerType.name,
            computerMoveTypeChoice = computerMoveChoiceType,
            nextAvailableTurn = player.nextTime,
            availableAttacks = availableAttacks,
            availableSupports = availableSupports,
            hitPoints = player.hitPoints,
            maxHitPoints = player.maxHitPoints,
            baseAttack = player.baseAttack,
            baseDefense = player.baseDefense,
            baseHit = player.baseHit,
            baseAvoid = player.baseAvoid,
            baseCrit = player.baseCrit,
            statusModifiers = statusModifiers
        )
    }

    fun importGameData(savedGame: SavedGame): Game {
        val totalPlayerCount = savedGame.teams.sumOf { tm -> tm.players.size }
        val teamCounts = savedGame.teams.associate { tm -> Pair(tm.id, tm.players.size) }
        val teams = savedGame.teams.map { tm -> importTeamData(tm, totalPlayerCount, computeTeamRatio(teamCounts, tm.id))  }
        return Game(teams, savedGame.currentTime)
    }

    private fun importTeamData(savedTeam: SavedTeam, totalPlayerCount: Int, teamRatio: Int) : Team {
        val teamId = TeamId.valueOf(savedTeam.id)
        val opponentCount = totalPlayerCount - savedTeam.players.size
        val players = savedTeam.players.map { pl -> importPlayer(pl, opponentCount, teamRatio) }
        return Team(players, teamId)
    }

    private fun importPlayer(savedPlayer: SavedPlayer, opponentCount: Int, teamRatio: Int) : Player {
        val playerAttacks = savedPlayer.availableAttacks.map { at ->
            val attackId = AttackActionId.valueOf(at.actionId)
            val attackTemplate = AttackRepository.getAttack(attackId)
            attackTemplate.toAttackAction(teamRatio, opponentCount, at.nextAvailable)
        }
        val playerSupports = savedPlayer.availableSupports.map { sp ->
            val supportId = SupportActionId.valueOf(sp.actionId)
            val supportTemplate = SupportRepository.getSupport(supportId)
            supportTemplate.toSupportAction(sp.nextAvailable)
        }
        val playerEffects = savedPlayer.statusModifiers.map { md ->
            StatusModifier(
                attack = md.attack,
                defense = md.defense,
                hit = md.hit,
                avoid = md.avoid,
                critical = md.critical,
                expires = md.expires)
        }

        return Player(
            maxHitPoints = savedPlayer.maxHitPoints,
            id = PlayerId.valueOf(savedPlayer.playerId),
            playerType = PlayerType.valueOf(savedPlayer.playerType),
            computerMoveChoiceType = if (savedPlayer.computerMoveTypeChoice != null) ComputerMoveChoiceType.valueOf(savedPlayer.computerMoveTypeChoice) else ComputerMoveChoiceType.RANDOM,
            hitPoints = savedPlayer.hitPoints,
            nextTime = savedPlayer.nextAvailableTurn,
            baseAttack = savedPlayer.baseAttack,
            baseDefense = savedPlayer.baseDefense,
            baseHit = savedPlayer.baseHit,
            baseAvoid = savedPlayer.baseAvoid,
            baseCrit = savedPlayer.baseCrit,
            attackActions = playerAttacks,
            supportActions = playerSupports,
            statusEffects = playerEffects.toMutableList()
        )
    }
}

open class GameImportException(message: String) : Exception(message)