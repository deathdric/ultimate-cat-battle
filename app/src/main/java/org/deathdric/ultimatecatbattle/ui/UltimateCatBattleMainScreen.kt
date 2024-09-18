package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import org.deathdric.ultimatecatbattle.R


@Composable
fun UltimateCatBattleMainScreen(windowWidthSizeClass: WindowWidthSizeClass, windowHeightSizeClass: WindowHeightSizeClass, viewModel: UltimateCatBattleViewModel, uiState: UltimateCatBattleUiState) {
    Row (modifier = Modifier
        .background(Color.White)
        .safeContentPadding()
        .statusBarsPadding()
        .systemBarsPadding()
        .navigationBarsPadding()
        .captionBarPadding()) {
        PlayerFrame(isGameOver = uiState.isGameOver, player = uiState.player1)
        Column (modifier = Modifier
            .background(Color.White)
            .weight(1f)
            .fillMaxHeight()) {
            if (uiState.actionMode != ActionMode.START_GAME) {
                BattleTopBar(
                    viewModel = viewModel,
                    uiState = uiState,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            when (uiState.actionMode) {
                ActionMode.START_GAME -> {
                    StartGamePanel(viewModel = viewModel, modifier = Modifier.fillMaxSize())
                }
                ActionMode.ACTION_SELECT -> {
                    if (uiState.attackSelectedInfo != null) {
                        AttackConfirmationPanel(viewModel = viewModel, attackSelectedInfo = uiState.attackSelectedInfo)
                    } else if (uiState.supportSelectedInfo != null) {
                        SupportConfirmationPanel(viewModel = viewModel, supportSelectedInfo = uiState.supportSelectedInfo)
                    } else {
                        ActionSelectionPanel(
                            windowWidthSizeClass = windowWidthSizeClass,
                            windowHeightSizeClass = windowHeightSizeClass,
                            viewModel = viewModel,
                            uiState = uiState,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                ActionMode.ATTACK_DONE -> {
                    AttackDonePanel(
                        viewModel = viewModel,
                        uiState = uiState,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                ActionMode.STATUS_DONE -> {
                    StatusDonePanel(
                        viewModel = viewModel,
                        uiState = uiState,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                ActionMode.PLAYER_TURN_SWITCH -> {
                    PlayerTurnPanel(
                        viewModel = viewModel,
                        uiState = uiState,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

        }
        PlayerFrame(isGameOver = uiState.isGameOver, player = uiState.player2)
    }
}

@Composable
@Preview
fun UltimateCatBattleMainScreenPreview() {
    val viewModel = UltimateCatBattleViewModel()
    viewModel.startGame(false)
    UltimateCatBattleMainScreen(windowWidthSizeClass = WindowWidthSizeClass.Medium, windowHeightSizeClass = WindowHeightSizeClass.Compact, viewModel = viewModel, uiState = viewModel.uiState.collectAsState().value)
}