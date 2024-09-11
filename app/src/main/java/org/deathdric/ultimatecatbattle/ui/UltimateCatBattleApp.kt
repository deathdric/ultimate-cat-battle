package org.deathdric.ultimatecatbattle.ui

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UltimateCatBattleApp(windowWidthSizeClass: WindowWidthSizeClass, windowHeightSizeClass: WindowHeightSizeClass, modifier: Modifier = Modifier) {
    val viewModel : UltimateCatBattleViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value
    if (uiState.isStartScreen) {
        StartScreen(windowWidthSizeClass = windowWidthSizeClass, windowHeightSizeClass = windowHeightSizeClass, viewModel = viewModel)
    } else {
        UltimateCatBattleMainScreen(windowWidthSizeClass = windowWidthSizeClass, windowHeightSizeClass = windowHeightSizeClass, viewModel = viewModel, uiState = uiState)
    }
}

@Composable
@Preview
fun UltimateCatBattleAppPreview() {
    UltimateCatBattleApp(WindowWidthSizeClass.Medium, WindowHeightSizeClass.Medium)
}