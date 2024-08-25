package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.data.PlayerRepository
import org.deathdric.ultimatecatbattle.data.SupportRepository
import org.deathdric.ultimatecatbattle.model.Player

@Composable
fun StatusDonePanel(viewModel: UltimateCatBattleViewModel, uiState: UltimateCatBattleUiState, modifier: Modifier = Modifier) {
    Column (modifier = modifier.padding(16.dp)) {
        Row (verticalAlignment = Alignment.CenterVertically){
            Text(
                text = stringResource(id = R.string.used_skill_message).format(
                    stringResource(id = uiState.activePlayerName),
                    stringResource(id = uiState.lastSupport!!.name)
                ),
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp).weight(1f)
            )
            Image(painter = painterResource(id = R.drawable.support_done), contentDescription = null,
                modifier = Modifier.padding(16.dp).size(100.dp))
        }
        Button(
            onClick = { viewModel.postAction() },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.proceed)
            )
        }
    }
}

@Composable
@Preview
fun StatusDonePanelPreview() {

    StatusDonePanel(viewModel = UltimateCatBattleViewModel(), uiState = UltimateCatBattleUiState(
        activePlayerId = 1,
        activePlayerIcon = PlayerRepository.catPlayer.icon,
        activePlayerName = PlayerRepository.catPlayer.name,
        player1 = Player(PlayerRepository.catPlayer).toStatus(true),
        player2 = Player(PlayerRepository.penguinPlayer).toStatus(false),
        remainingTime = 20,
        actionMode = ActionMode.STATUS_DONE,
        lastSupport = SupportRepository.berserk
    ), modifier = Modifier.background(Color.White))
}