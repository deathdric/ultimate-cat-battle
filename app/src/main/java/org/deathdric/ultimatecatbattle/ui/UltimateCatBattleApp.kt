package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.deathdric.ultimatecatbattle.R

@Composable
fun UltimateCatBattleApp(modifier: Modifier = Modifier) {
    val viewModel : UltimateCatBattleViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value
    if (uiState.isStartScreen) {
        StartScreen(viewModel = viewModel)
    } else {
        UltimateCatBattleMainScreen(viewModel = viewModel, uiState = uiState)
    }
}

@Composable
@Preview
fun UltimateCatBattleAppPreview() {
    UltimateCatBattleApp()
}