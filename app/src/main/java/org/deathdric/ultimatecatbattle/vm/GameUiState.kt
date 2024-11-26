package org.deathdric.ultimatecatbattle.vm

import androidx.annotation.StringRes
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.AttackActionPreview
import org.deathdric.ultimatecatbattle.model.AttackActionResult
import org.deathdric.ultimatecatbattle.model.Game
import org.deathdric.ultimatecatbattle.model.Player
import org.deathdric.ultimatecatbattle.model.PlayerId
import org.deathdric.ultimatecatbattle.model.SupportAction
import org.deathdric.ultimatecatbattle.model.SupportActionResult
import org.deathdric.ultimatecatbattle.model.Team

enum class GameStatus {
    INTRO,
    PLAYER_TURN,
    MOVE_SELECT,
    ATTACK_CONFIRM,
    ATTACK_EXECUTION,
    ATTACK_RESULT,
    SUPPORT_CONFIRM,
    SUPPORT_EXECUTION,
    SUPPORT_RESULT,
    GAME_OVER,
    DEBUG
}

data class PlayerState (
    val player: Player,
    val team: Team
) {
    fun remainingTime(curTime: Int) : Int {
        return (player.nextTime - curTime).coerceAtLeast(0)
    }
}

data class AttackSelectionState (
    val preview: AttackActionPreview,
    val selectedTarget: PlayerId,
    val actionExecutionQuote: Int = R.string.attack_quote_multi_1
)

data class SupportSelectionState (
    val availableTargets: List<PlayerId>,
    val selectedTarget: PlayerId,
    val supportAction: SupportAction,
    val supportExecutionQuote: Int = R.string.support_quote_self_1
)

data class GamePlayerInfo (
    val activePlayer : PlayerState,
    val players : List<PlayerState>,
    val curTime: Int
) {
    fun computeRemainingTime() : Int {
        val nextPlayerSwitch = this.players
            .filter { this.activePlayer.player.id != it.player.id && it.player.isAlive }
            .minOfOrNull { it.player.nextTime } ?: this.curTime
        return nextPlayerSwitch - this.curTime
    }
}

fun Player.toState(game: Game) : PlayerState {
    return PlayerState(this, game.findTeam(this).get())
}

fun Game.toPlayerInfo() : GamePlayerInfo {
    val activePlayer = this.curActivePlayer!!.toState(this)
    val players = this.players.map { it.toState(this) }.toList()
    return GamePlayerInfo(activePlayer, players, this.curTime)
}

fun Game.toGameOverInfo(
    @StringRes
    gameOverQuote: Int
): GameOverInfo {
    val aliveWinners = mutableListOf<PlayerState>()
    val defeatedWinners = mutableListOf<PlayerState>()
    val winningTeams = this.teams.filter { it.isAlive }
    for (team in winningTeams) {
        for (player in team.players) {
            val playerInfo = player.toState(this)
            if (player.isAlive) {
                aliveWinners.add(playerInfo)
            } else {
                defeatedWinners.add(playerInfo)
            }
        }
    }
    return GameOverInfo(aliveWinners, defeatedWinners, gameOverQuote)
}

data class GameOverInfo (
    val aliveWinners: List<PlayerState>,
    val defeatedWinners : List<PlayerState>,
    val gameOverQuote: Int
)

data class GameUiState (
    val playerInfo: GamePlayerInfo,
    val status: GameStatus,
    val startTurnQuote: Int = R.string.start_turn_quote_1,
    val selectedAttack: AttackSelectionState? = null,
    val selectedSupport: SupportSelectionState? = null,
    val attackResult: AttackActionResult? = null,
    val supportResult: SupportActionResult? = null,
    val gameOverInfo: GameOverInfo? = null
)