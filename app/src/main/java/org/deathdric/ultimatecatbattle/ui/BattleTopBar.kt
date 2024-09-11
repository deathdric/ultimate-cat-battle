package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.data.PlayerRepository
import org.deathdric.ultimatecatbattle.model.Player

@Composable
fun BattleTopBar(viewModel: UltimateCatBattleViewModel, uiState: UltimateCatBattleUiState, modifier: Modifier = Modifier) {
    Row (modifier = modifier
        .fillMaxWidth()
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { viewModel.toggleDetails() }, modifier = Modifier.size(50.dp)) {
            if (uiState.showActionDetails) {
                Image(
                    painter = painterResource(id = R.drawable.details_off),
                    contentDescription = "-"
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.details_on),
                    contentDescription = "+"
                )
            }
        }
        Spacer(modifier = Modifier.size(width = 50.dp, height = 20.dp))
        Text (
            text = stringResource(id = R.string.player_turn).format(stringResource(id = uiState.activePlayerName)),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 28.sp,
            modifier = Modifier.weight(1f)
        )
        Image(painter = painterResource(id = R.drawable.clock),
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .size(50.dp),
            contentDescription = null
        )
        Text (
            modifier = Modifier.defaultMinSize(minWidth = 40.dp),
            text =  "%s".format(uiState.remainingTime),
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

    }

}

@Composable
@Preview
fun BattleTopBarPreview() {
    BattleTopBar(
        modifier = Modifier.background(Color.White),
        viewModel = UltimateCatBattleViewModel(),
        uiState = UltimateCatBattleUiState(
            activePlayerId = 1,
            activePlayerIcon = PlayerRepository.catPlayer.icon,
            activePlayerName = PlayerRepository.catPlayer.name,
            player1 = Player(PlayerRepository.catPlayer).toStatus(true),
            player2 = Player(PlayerRepository.penguinPlayer).toStatus(false),
            remainingTime = 20
        )
    )
}