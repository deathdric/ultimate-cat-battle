package org.deathdric.ultimatecatbattle.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.AttackActionId
import org.deathdric.ultimatecatbattle.model.AttackActionPreview
import org.deathdric.ultimatecatbattle.model.ComputerMove
import org.deathdric.ultimatecatbattle.model.ComputerMoveType
import org.deathdric.ultimatecatbattle.model.Game
import org.deathdric.ultimatecatbattle.model.Player
import org.deathdric.ultimatecatbattle.model.PlayerId
import org.deathdric.ultimatecatbattle.model.PlayerType
import org.deathdric.ultimatecatbattle.model.RandomNumberGenerator
import org.deathdric.ultimatecatbattle.model.SupportAction
import org.deathdric.ultimatecatbattle.model.SupportActionId
import org.deathdric.ultimatecatbattle.model.TargetType
import org.deathdric.ultimatecatbattle.model.TeamId
import org.deathdric.ultimatecatbattle.model.createNewGame
import org.deathdric.ultimatecatbattle.model.findComputerMove

class UltimateCatBattleViewModel: ViewModel() {

    private var playerCount: Int = 1
    private var characterAllocation : MutableMap<PlayerId, CharacterAllocation> = createCharacterAllocation(playerCount)
    private var teamAllocation: MutableMap<PlayerKey, TeamAllocation> = createTeamAllocations(characterAllocation.values.toList(),playerCount)
    private var game: Game? = null
    private var numberGenerator = RandomNumberGenerator()

    private fun initialState(): UltimateCatBattleUiState {
        return UltimateCatBattleUiState(
            rootUiStatus = RootUiStatus.START_SCREEN,
            startScreenState = StartScreenState(),
            characterAllocationState = createGlobalCharacterAllocationState(characterAllocation, playerCount),
            teamAllocationState = createGlobalTeamAllocationState(teamAllocation, playerCount)
        )
    }
    private val _uiState = MutableStateFlow(initialState())
    val uiState: StateFlow<UltimateCatBattleUiState> = _uiState


    fun startupSwitchCat() {
        _uiState.update { previous ->
            val oldStartInfo = previous.startScreenState
            val newStartInfo = oldStartInfo.copy(catClicked = !oldStartInfo.catClicked)
            previous.copy(startScreenState = newStartInfo)
        }
    }

    fun startupSwitchPenguin() {
        _uiState.update { previous ->
            val oldStartInfo = previous.startScreenState
            val newStartInfo = oldStartInfo.copy(penguinClicked = !oldStartInfo.penguinClicked)
            previous.copy(startScreenState = newStartInfo)
        }
    }

    fun startPlayerSelection(nbPlayers: Int) {
        playerCount = nbPlayers
        characterAllocation = createCharacterAllocation(nbPlayers)
        _uiState.update { previous ->
            previous.copy(rootUiStatus = RootUiStatus.PLAYER_SELECT,
                characterAllocationState = createGlobalCharacterAllocationState(this.characterAllocation, this.playerCount))
        }
    }

    fun selectPlayerAllocation(playerId: PlayerId, playerType: PlayerType) {
        val playerAllocation = this.characterAllocation[playerId] ?: return
        this.characterAllocation[playerId] = playerAllocation.copy(playerType = playerType)


        _uiState.update { previous ->
            previous.copy(characterAllocationState = createGlobalCharacterAllocationState(this.characterAllocation, this.playerCount))
        }
    }

    fun toggleCharacter(playerId: PlayerId, enable: Boolean) {
        val playerAllocation = this.characterAllocation[playerId] ?: return
        this.characterAllocation[playerId] = playerAllocation.copy(enable = enable, playerType = PlayerType.COMPUTER)
        if (!enable) {
            val activeCharacters = characterAllocation.values.stream().filter { it.enable }.count()
            if (activeCharacters.toInt() == playerCount) {
                val computedAllocations = characterAllocation.values.stream().filter { it.enable && it.playerType == PlayerType.COMPUTER}
                for (computedAllocation in computedAllocations) {
                    this.characterAllocation[computedAllocation.playerId] = computedAllocation.copy(playerType = PlayerType.PLAYER1)
                }
            }
        }
        _uiState.update { previous ->
            previous.copy(characterAllocationState = createGlobalCharacterAllocationState(this.characterAllocation, this.playerCount))
        }
    }

    fun selectTeamAllocation(playerKey: PlayerKey, teamId: TeamId) {
        val teamAllocation = this.teamAllocation[playerKey] ?: return
        this.teamAllocation[playerKey] = teamAllocation.copy(teamId = teamId)


        _uiState.update { previous ->
            previous.copy(teamAllocationState = createGlobalTeamAllocationState(this.teamAllocation, this.playerCount))
        }
    }

    fun startTeamSelection() {
        teamAllocation = createTeamAllocations(characterAllocation.values.toList(), playerCount)
        val computerControlledCharacters =
            characterAllocation.values.count { it.enable && (it.playerType == PlayerType.COMPUTER) }

        val skipTeamSelection =
            (playerCount == 1 && computerControlledCharacters == 1)
                    || (playerCount == 2 && computerControlledCharacters == 0)

        if (skipTeamSelection) {
            startGame()
        } else {
            _uiState.update { previous ->
                previous.copy(
                    rootUiStatus = RootUiStatus.TEAM_SELECT,
                    teamAllocationState = createGlobalTeamAllocationState(
                        teamAllocation,
                        this.playerCount
                    )
                )
            }
        }
    }

    fun startGame() {
        game = createNewGame(characterAllocation, teamAllocation, numberGenerator)
        game!!.updateActivePlayer()
        _uiState.update { previous ->
            previous.copy(rootUiStatus = RootUiStatus.MAIN_GAME,
                gameState = GameUiState(game!!.toPlayerInfo(), GameStatus.INTRO))
        }
    }

    fun displayNewTurn() {
        _uiState.update { previous ->
            val gameState = GameUiState(
                playerInfo = game!!.toPlayerInfo(),
                status = GameStatus.PLAYER_TURN,
                startTurnQuote = StartTurnQuotes.pickRandomQuote(numberGenerator),
                selectedAttack = null,
                attackResult = null
            )
            previous.copy(gameState = gameState)
        }
    }

    private fun executeComputerAttack(computerPlayer: Player, nextMove: ComputerMove) {
        val attackActionMove = computerPlayer.attackActions.first { it.id == nextMove.attackAction }
        val attackActionSimulation = attackActionMove.simulate(computerPlayer, game!!.findTargets(computerPlayer.id, attackActionMove.targetType))
        _uiState.update { previous ->
            val gameState = GameUiState(
                playerInfo = game!!.toPlayerInfo(),
                status = GameStatus.ATTACK_EXECUTION,
                startTurnQuote = previous.gameState!!.startTurnQuote,
                selectedAttack = AttackSelectionState(attackActionSimulation, nextMove.actionTarget,
                    generateAttackQuote(attackActionSimulation)),
                attackResult = null
            )
            previous.copy(gameState = gameState)
        }
    }

    private fun executeComputerSupport(computerPlayer: Player, nextMove: ComputerMove) {
        val supportActionMove = computerPlayer.supportActions.first { it.id == nextMove.supportAction }
        val targets = game!!.findTargets(computerPlayer.id, supportActionMove.targetType).map { it.id }
        _uiState.update { previous ->
            val gameState = GameUiState(
                playerInfo = game!!.toPlayerInfo(),
                status = GameStatus.SUPPORT_EXECUTION,
                startTurnQuote = previous.gameState!!.startTurnQuote,
                selectedSupport = SupportSelectionState(availableTargets = targets, supportAction = supportActionMove,
                    selectedTarget = nextMove.actionTarget, supportExecutionQuote = generateSupportQuote(supportAction = supportActionMove,
                        selectedTarget = nextMove.actionTarget, currentPlayer = computerPlayer.id)),
                supportResult = null
            )
            previous.copy(gameState = gameState)
        }
    }

    fun chooseNextMove() {
        val curPlayer = game!!.curActivePlayer!!
        if (curPlayer.playerType == PlayerType.COMPUTER) {
            val currentTeam = game!!.findTeam(curPlayer).get()
            val allyCount = currentTeam.players.size.coerceAtLeast(1)
            val enemyCount = (game!!.players.size - allyCount).coerceAtLeast(1)

            val nextMove = game!!.findComputerMove(curPlayer.id, allyCount, enemyCount)
            when(nextMove.moveType) {
                ComputerMoveType.ATTACK -> executeComputerAttack(curPlayer, nextMove)
                ComputerMoveType.SUPPORT -> executeComputerSupport(curPlayer, nextMove)
            }

        } else {

            _uiState.update { previous ->
                val gameState = GameUiState(
                    playerInfo = game!!.toPlayerInfo(),
                    status = GameStatus.MOVE_SELECT,
                    startTurnQuote = previous.gameState!!.startTurnQuote,
                    selectedAttack = null,
                    attackResult = null
                )
                previous.copy(gameState = gameState)
            }
        }
    }

    fun selectAttack(attackActionId: AttackActionId) {
        val player = game!!.curActivePlayer!!
        val attackAction = player.attackActions.first { it.id == attackActionId }
        val attackActionSimulation = attackAction.simulate(player, game!!.findTargets(player.id, attackAction.targetType))
        _uiState.update { previous ->
            val attackActionState = AttackSelectionState (
                preview = attackActionSimulation,
                selectedTarget = attackActionSimulation.targetedPreviews[0].target.id
            )
            val gameState = previous.gameState!!.copy(status = GameStatus.ATTACK_CONFIRM, selectedAttack = attackActionState)
            previous.copy(gameState = gameState)
        }
    }

    fun selectSupport(supportActionId: SupportActionId) {
        val player = game!!.curActivePlayer!!
        val supportAction = player.supportActions.first { it.id == supportActionId }
        val availableTargets = game!!.findTargets(player.id, supportAction.targetType)
        _uiState.update { previous ->
            val supportSelectionState = SupportSelectionState (
                supportAction = supportAction,
                selectedTarget = player.id,
                availableTargets = availableTargets.map { it.id }
            )
            val gameState = previous.gameState!!.copy(status = GameStatus.SUPPORT_CONFIRM, selectedSupport = supportSelectionState)
            previous.copy(gameState = gameState)
        }
    }

    fun changeAttackTarget(playerId: PlayerId) {
        _uiState.update { previous ->
            val attackActionState = previous.gameState!!.selectedAttack!!.copy(selectedTarget = playerId)
            val gameState = previous.gameState.copy(status = GameStatus.ATTACK_CONFIRM, selectedAttack = attackActionState)
            previous.copy(gameState = gameState)
        }
    }

    fun changeSupportTarget(playerId: PlayerId) {
        _uiState.update { previous ->
            val supportActionState = previous.gameState!!.selectedSupport!!.copy(selectedTarget = playerId)
            val gameState = previous.gameState.copy(status = GameStatus.SUPPORT_CONFIRM, selectedSupport = supportActionState)
            previous.copy(gameState = gameState)
        }
    }

    private fun generateAttackQuote(attackPreview: AttackActionPreview) : Int {
        val isMultiTarget = if (attackPreview.attackAction.targetType == TargetType.ALL_ENEMIES) {
            attackPreview.targetedPreviews.count() > 1
        } else {
            false
        }
        return if (isMultiTarget) AttackMultiQuotes.pickRandomQuote(numberGenerator) else AttackSingleQuotes.pickRandomQuote(numberGenerator)
    }

    fun confirmAttack() {
        _uiState.update { previous ->

            val selectedAttack = previous.gameState!!.selectedAttack!!

            val gameState = previous.gameState.copy(status = GameStatus.ATTACK_EXECUTION,
                selectedAttack = selectedAttack.copy(actionExecutionQuote = generateAttackQuote(selectedAttack.preview)))
            previous.copy(gameState = gameState)
        }
    }

    private fun generateSupportQuote(supportAction: SupportAction, selectedTarget: PlayerId, currentPlayer: PlayerId) : Int {
        val isSelf = selectedTarget == currentPlayer
        return if (isSelf) SupportSelfQuotes.pickRandomQuote(numberGenerator) else SupportAllyQuotes.pickRandomQuote(numberGenerator)
    }

    fun confirmSupport() {
        _uiState.update { previous ->

            val selectedSupport = previous.gameState!!.selectedSupport!!

            val gameState = previous.gameState.copy(status = GameStatus.SUPPORT_EXECUTION,
                selectedSupport = selectedSupport.copy(supportExecutionQuote = generateSupportQuote(selectedSupport.supportAction, selectedTarget = selectedSupport.selectedTarget, previous.gameState.playerInfo.activePlayer.player.id)))
            previous.copy(gameState = gameState)
        }
    }

    fun executeAttack(attackActionId: AttackActionId, target: PlayerId) {
        val player = game!!.curActivePlayer!!
        val attackAction = player.attackActions.first { it.id == attackActionId }
        val attackTargets = if (attackAction.targetType == TargetType.ALL_ENEMIES) {
            game!!.findTargets(player.id, attackAction.targetType)
        } else {
            val playerTarget = game!!.players.first { it.id == target }
            listOf(playerTarget)
        }
        val attackResult = attackAction.apply(game!!.curTime, player, attackTargets)
        _uiState.update { previous ->
            val gameUiState = GameUiState(
                playerInfo = game!!.toPlayerInfo(),
                status = GameStatus.ATTACK_RESULT,
                startTurnQuote = R.string.start_turn_quote_1,
                selectedAttack = previous.gameState!!.selectedAttack!!,
                attackResult = attackResult
            )
            previous.copy(gameState = gameUiState)
        }
    }

    fun executeSupport(supportActionId: SupportActionId, target: PlayerId) {
        val player = game!!.curActivePlayer!!
        val supportAction = player.supportActions.first { it.id == supportActionId }
        val supportTargets = if (supportAction.targetType == TargetType.ALL_ALLIES) {
            game!!.findTargets(player.id, supportAction.targetType)
        } else {
            val playerTarget = game!!.players.first { it.id == target }
            listOf(playerTarget)
        }
        val supportResult = supportAction.apply(game!!.curTime, player, supportTargets)
        _uiState.update { previous ->
            val gameUiState = GameUiState(
                playerInfo = game!!.toPlayerInfo(),
                status = GameStatus.SUPPORT_RESULT,
                startTurnQuote = R.string.start_turn_quote_1,
                selectedSupport = previous.gameState!!.selectedSupport!!,
                supportResult = supportResult
            )
            previous.copy(gameState = gameUiState)
        }
    }

    fun endAction() {
        val playerChanged = game!!.updateActivePlayer()
        game!!.players.forEach { it.applyEffectExpiration(game!!.curTime) }
        if (game!!.isGameOver) {
            _uiState.update { previous ->
                val gameState = GameUiState(
                    playerInfo = game!!.toPlayerInfo(),
                    status = GameStatus.GAME_OVER,
                    startTurnQuote = previous.gameState!!.startTurnQuote,
                    selectedAttack = null,
                    selectedSupport = null,
                    attackResult = null,
                    supportResult = null,
                    gameOverInfo = game!!.toGameOverInfo(GameOverQuotes.pickRandomQuote(this.numberGenerator))
                )
                previous.copy(gameState = gameState)
            }
        } else if (playerChanged) {
            displayNewTurn()
        } else {
            chooseNextMove()
        }
    }


    fun cancelAttack() {
        _uiState.update { previous ->
            val gameState = previous.gameState!!.copy(status = GameStatus.MOVE_SELECT, selectedAttack = null)
            previous.copy(gameState = gameState)
        }
    }

    fun cancelSupport() {
        _uiState.update { previous ->
            val gameState = previous.gameState!!.copy(status = GameStatus.MOVE_SELECT, selectedSupport = null)
            previous.copy(gameState = gameState)
        }
    }

    fun cancelTeamAllocation() {
        _uiState.update { previous ->
            previous.copy(rootUiStatus = RootUiStatus.PLAYER_SELECT)
        }
    }

    fun resetToMainScreen() {
        _uiState.update {
            initialState()
        }
    }

    fun toggleContextHelp(enabled: Boolean) {
        _uiState.update { previous ->
            previous.copy(toggleContextHelp = enabled)
        }
    }

    fun toggleReturnHomeScreen(enabled: Boolean) {
        _uiState.update { previous ->
            previous.copy(toggleReturnHomeScreen = enabled)
        }
    }
}