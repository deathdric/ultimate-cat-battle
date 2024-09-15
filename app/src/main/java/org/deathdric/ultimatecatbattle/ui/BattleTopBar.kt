package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
        Box {
            Image(painter = painterResource(id = R.drawable.menu), contentDescription = stringResource(
                id = R.string.in_game_menu
            ),
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .size(50.dp)
                .clickable {  viewModel.displayMenu() }
                .border(1.dp, Color.Black))
        }
        Spacer(modifier = Modifier.size(16.dp))
        Box {
            if (uiState.showActionDetails) {
                Image(
                    painter = painterResource(id = R.drawable.details_off),
                    contentDescription = "-",
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                        .size(50.dp)
                        .clickable {  viewModel.toggleDetails() }
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.details_on),
                    contentDescription = "+",
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                        .size(50.dp)
                        .clickable {  viewModel.toggleDetails() }
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        val playerTypeIcon = if (uiState.activePlayerId == 1) {
            R.drawable.player1
        } else if (viewModel.isSinglePlayer) {
            R.drawable.computer
        } else {
            R.drawable.player2
        }
        Image(painter = painterResource(id = playerTypeIcon),
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .size(50.dp),
            contentDescription = null
        )
        Image(painter = painterResource(id = uiState.activePlayerIcon),
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .size(50.dp),
            contentDescription = null
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