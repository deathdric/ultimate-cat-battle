package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R

@Composable
fun PlayerTurnPanel(viewModel: UltimateCatBattleViewModel, uiState: UltimateCatBattleUiState, modifier: Modifier = Modifier) {
    Column (modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = uiState.activePlayerIcon),
            contentDescription = stringResource(id = uiState.activePlayerName),
            modifier = Modifier.size(100.dp)
        )
        SimpleButton(onClick = { viewModel.startPlayerTurn() }, modifier = Modifier.padding(16.dp), text = stringResource(id = R.string.start_turn))
    }
}