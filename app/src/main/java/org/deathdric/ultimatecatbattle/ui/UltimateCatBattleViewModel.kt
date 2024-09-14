package org.deathdric.ultimatecatbattle.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.deathdric.ultimatecatbattle.data.PlayerRepository
import org.deathdric.ultimatecatbattle.model.AttackAction
import org.deathdric.ultimatecatbattle.model.AttackResult
import org.deathdric.ultimatecatbattle.model.AttackSelectedInfo
import org.deathdric.ultimatecatbattle.model.NextTurnInfo
import org.deathdric.ultimatecatbattle.model.Player
import org.deathdric.ultimatecatbattle.model.SupportAction
import org.deathdric.ultimatecatbattle.model.SupportSelectedInfo
import org.deathdric.ultimatecatbattle.model.apply
import org.deathdric.ultimatecatbattle.model.computeEffects
import org.deathdric.ultimatecatbattle.model.simulate
import kotlin.random.Random

class UltimateCatBattleViewModel : ViewModel() {
    private var curTime = 0
    private var nextTurn = 0
    private var activePlayerId = 1
    var isSinglePlayer = false
        private set
    private var player1 : Player = Player(PlayerRepository.catPlayer)
    private var player2 : Player = Player(PlayerRepository.penguinPlayer)
    private val currentPlayerName get() = if (activePlayerId == 1) player1.template.name else player2.template.name
    private val currentPlayerIcon get() = if (activePlayerId == 1) player1.template.icon else player2.template.icon
    private val remainingTime get() = Math.abs(player1.nextTime - player2.nextTime)

    private val _uiState = MutableStateFlow(UltimateCatBattleUiState(
        isStartScreen = true,
        player1 = player1.toStatus(),
        player2 = player2.toStatus(),
        activePlayerName = player1.template.name,
        activePlayerIcon = player1.template.icon,
        activePlayerId = 1
    ))
    val uiState: StateFlow<UltimateCatBattleUiState> = _uiState

    fun startGame(isSinglePlayer: Boolean) {
        this.isSinglePlayer = isSinglePlayer
        player1 = Player(PlayerRepository.catPlayer)
        player2 = Player(PlayerRepository.penguinPlayer)
        curTime = 0
        nextTurn = 0
        val initPlayer1Delay = Random.nextInt(0, 20)
        var initPlayer2Delay = Random.nextInt(0, 20)
        while (initPlayer2Delay == initPlayer1Delay) {
            initPlayer2Delay = Random.nextInt(0, 20)
        }
        player1.applyDelay(initPlayer1Delay)
        player2.applyDelay(initPlayer2Delay)
        updateStatus()
        updateTurnSwitch(true)
    }

    fun proceedWithGame() {
        _uiState.update { previous ->
            previous.copy(actionMode = ActionMode.PLAYER_TURN_SWITCH)
        }
    }

    fun returnToMainScreen() {
        _uiState.value = UltimateCatBattleUiState(
            isStartScreen = true,
            player1 = player1.toStatus(),
            player2 = player2.toStatus(),
            activePlayerName = player1.template.name,
            activePlayerIcon = player1.template.icon,
            activePlayerId = 1
        )
    }

    fun postAction() {
        val turnChange = updateStatus()
        if (turnChange) {
            updateTurnSwitch(false)
        } else {
            updateActionSelect()
        }
    }

    fun startPlayerTurn() {
        updateActionSelect()
    }

    fun selectAttack(attackAction: AttackAction) {
        val attacker = if (activePlayerId == 1) player1 else player2
        val defender = if (activePlayerId == 1) player2 else player1
        val attackSimulationResult = attackAction.simulate(attacker, defender)
        val diffTime = (defender.nextTime - curTime) - attackAction.delay
        val nextTurnInfo = if (diffTime > 0) {
            NextTurnInfo(attacker, diffTime)
        } else {
            NextTurnInfo(defender, -diffTime)
        }

        val attackSelectedInfo = AttackSelectedInfo(attackAction, attackSimulationResult, nextTurnInfo)
        updateAttackSelectionState(attackSelectedInfo)

    }

    fun selectSupport(supportAction: SupportAction) {
        val attacker = if (activePlayerId == 1) player1 else player2
        val defender = if (activePlayerId == 1) player2 else player1
        val diffTime = (defender.nextTime - curTime) - supportAction.delay
        val nextTurnInfo = if (diffTime > 0) {
            NextTurnInfo(attacker, diffTime)
        } else {
            NextTurnInfo(defender, -diffTime)
        }

        val supportSelectedInfo = SupportSelectedInfo(supportAction, supportAction.computeEffects(attacker), nextTurnInfo)
        updateSupportSelectionState(supportSelectedInfo)

    }

    fun chooseAttack(attackAction: AttackAction) {
        val attacker = if (activePlayerId == 1) player1 else player2
        val defender = if (activePlayerId == 1) player2 else player1
        val attackResult = attackAction.apply(attacker, defender)
        updateAttackResultState(attackAction, attackResult)
    }

    fun chooseSupport(supportAction: SupportAction) {
        val targetPlayer = if (activePlayerId == 1) player1 else player2
        supportAction.apply(targetPlayer, curTime)
        updateSupportResultState(supportAction)
    }

    fun toggleDetails() {
        _uiState.update { previous ->
            previous.copy(showActionDetails = !previous.showActionDetails)
        }
    }

    fun displayMenu() {
        _uiState.update { previous ->
            previous.copy(menuState = MenuState(active = true))
        }
    }

    fun quitMenu() {
        _uiState.update { previous ->
            previous.copy(menuState = MenuState(active = false))
        }
    }


    fun switchMenuActiveItem(activeItem: MenuActiveItem) {
        _uiState.update { previous ->
            previous.copy(menuState = MenuState(active = true, activeItem = activeItem))
        }
    }

    private fun updateStatus() : Boolean {
        curTime = Math.min(player1.nextTime, player2.nextTime)
        player1.applyEffectExpiration(curTime)
        player2.applyEffectExpiration(curTime)
        val nextPlayerId : Int = if(player1.nextTime < player2.nextTime) 1 else 2
        val playerTurnChanged = nextPlayerId != activePlayerId
        activePlayerId = nextPlayerId
        return playerTurnChanged
    }

    private fun updateTurnSwitch(isNewGame: Boolean) {
        _uiState.update { previous ->
            previous.copy(isStartScreen = false,
                actionMode = if (isNewGame) ActionMode.START_GAME else ActionMode.PLAYER_TURN_SWITCH,
                activePlayerName = currentPlayerName,
                activePlayerIcon = currentPlayerIcon,
                remainingTime = remainingTime,
                player1 = player1.toStatus(activePlayerId == 1),
                player2 = player2.toStatus(activePlayerId == 2),
                activePlayerId = activePlayerId
            )
        }
    }

    private fun updateActionSelect() {

        if (activePlayerId == 2 && isSinglePlayer) {
            _uiState.update { previous ->
                previous.copy(
                    remainingTime = remainingTime
                )
            }
            val computerAction = computeTurn(player2, player1)
            computerAction.invoke(this)
        } else {
            _uiState.update { previous ->
                previous.copy(
                    isStartScreen = false,
                    actionMode = ActionMode.ACTION_SELECT,
                    remainingTime = remainingTime,
                    player1 = player1.toStatus(activePlayerId == 1),
                    player2 = player2.toStatus(activePlayerId == 2),
                    availableAttacks = if (activePlayerId == 1) player1.template.attackActions else player2.template.attackActions,
                    availableSupports = if (activePlayerId == 2) player2.template.supportActions else player2.template.supportActions
                )
            }
        }
    }

    fun cancelMoveSelection() {
        _uiState.update { previous ->
            previous.copy(
                attackSelectedInfo = null,
                supportSelectedInfo = null
            )
        }
    }


    private fun updateAttackSelectionState(attackSelectedInfo: AttackSelectedInfo) {
        _uiState.update { previous ->
            previous.copy(
                supportSelectedInfo = null,
                attackSelectedInfo = attackSelectedInfo
            )
        }
    }

    private fun updateAttackResultState(attackAction: AttackAction, attackResult: AttackResult) {
        _uiState.update { previous ->
            previous.copy(isStartScreen = false,
                attackSelectedInfo = null,
                supportSelectedInfo = null,
                isGameOver = !player1.isAlive || !player2.isAlive,
                actionMode = ActionMode.ATTACK_DONE,
                player1 = player1.toStatus(activePlayerId == 1),
                player2 = player2.toStatus(activePlayerId == 2),
                attackResult = attackResult,
                lastAttack = attackAction
            )
        }

    }

    private fun updateSupportSelectionState(supportSelectedInfo: SupportSelectedInfo) {
        _uiState.update { previous ->
            previous.copy(
                attackSelectedInfo = null,
                supportSelectedInfo = supportSelectedInfo
            )
        }
    }

    private fun updateSupportResultState(supportAction: SupportAction) {
        _uiState.update { previous ->
            previous.copy(isStartScreen = false,
                attackSelectedInfo = null,
                supportSelectedInfo = null,
                actionMode = ActionMode.STATUS_DONE,
                player1 = player1.toStatus(activePlayerId == 1),
                player2 = player2.toStatus(activePlayerId == 2),
                lastSupport = supportAction
            )
        }
    }
}