package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.deathdric.ultimatecatbattle.ui.screens.MainGameScreen
import org.deathdric.ultimatecatbattle.ui.screens.PlayerSelectScreen
import org.deathdric.ultimatecatbattle.ui.screens.PowerSelectScreen
import org.deathdric.ultimatecatbattle.ui.screens.ReturnHomeScreen
import org.deathdric.ultimatecatbattle.ui.screens.StartScreen
import org.deathdric.ultimatecatbattle.ui.screens.TeamSelectScreen
import org.deathdric.ultimatecatbattle.ui.screens.help.HelpContextScreen
import org.deathdric.ultimatecatbattle.ui.toolkit.screenModifier
import org.deathdric.ultimatecatbattle.vm.RootUiStatus
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

data class ScreenConstraints (
    val maxWidth : Dp,
    val maxHeight : Dp
)

@Composable
fun UltimateCatBattleApp() {

    BoxWithConstraints (modifier = Modifier.screenModifier()) {
        val screenConstraints = ScreenConstraints(maxWidth = this.maxWidth, maxHeight = this.maxHeight)
        val viewModel : UltimateCatBattleViewModel = viewModel()
        val uiState = viewModel.uiState.collectAsState().value
        if (uiState.toggleContextHelp) {
            HelpContextScreen(screenConstraints = screenConstraints, viewModel = viewModel, uiState = uiState,
                modifier = Modifier.fillMaxSize())
        } else {
            when (uiState.rootUiStatus) {
                RootUiStatus.START_SCREEN -> {
                    StartScreen(
                        screenConstraints,
                        viewModel = viewModel,
                        uiState = uiState,
                        Modifier.fillMaxSize()
                    )
                }

                RootUiStatus.PLAYER_SELECT -> {
                    PlayerSelectScreen(
                        screenConstraints,
                        viewModel = viewModel,
                        uiState = uiState,
                        Modifier.fillMaxSize()
                    )
                }

                RootUiStatus.POWER_ALLOCATIONS -> {
                    PowerSelectScreen(
                        screenConstraints,
                        viewModel = viewModel,
                        uiState = uiState,
                        Modifier.fillMaxSize()
                    )
                }

                RootUiStatus.TEAM_SELECT -> {
                    TeamSelectScreen(
                        screenConstraints,
                        viewModel = viewModel,
                        uiState = uiState,
                        Modifier.fillMaxSize()
                    )
                }

                RootUiStatus.MAIN_GAME -> {
                    if (uiState.toggleReturnHomeScreen) {
                        ReturnHomeScreen(screenConstraints = screenConstraints, viewModel = viewModel,
                            modifier = Modifier.fillMaxSize())
                    } else {
                        MainGameScreen(
                            screenConstraints,
                            viewModel = viewModel,
                            gameState = uiState.gameState!!,
                            Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}