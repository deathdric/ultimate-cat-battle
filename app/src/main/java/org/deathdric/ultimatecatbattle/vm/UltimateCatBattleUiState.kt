package org.deathdric.ultimatecatbattle.vm

enum class RootUiStatus {
    START_SCREEN,
    PLAYER_SELECT,
    TEAM_SELECT,
    MAIN_GAME
}

data class StartScreenState(
    val catClicked: Boolean = false,
    val penguinClicked: Boolean = false
)

data class UltimateCatBattleUiState(
    val rootUiStatus: RootUiStatus,
    val startScreenState: StartScreenState,
    val characterAllocationState: GlobalCharacterAllocationState,
    val teamAllocationState: GlobalTeamAllocationState,
    val gameState: GameUiState? = null,
    val toggleContextHelp: Boolean = false,
    val toggleReturnHomeScreen: Boolean = false
)
