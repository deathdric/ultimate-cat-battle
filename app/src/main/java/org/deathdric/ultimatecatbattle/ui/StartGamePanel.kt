package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun StartGamePanel(viewModel: UltimateCatBattleViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        TitleText(
            text = stringResource(id = R.string.new_game),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp).fillMaxWidth()
        )
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier.weight(1f).padding(4.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(percent = 10))
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MessageText(
                    text = stringResource(id = R.string.player1),
                    textAlign = TextAlign.Center
                )
                Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.player1), contentDescription = null, modifier = Modifier
                        .size(50.dp)
                        .padding(4.dp))
                    Image(painter = painterResource(id = PlayerRepository.catPlayer.icon), contentDescription = null, modifier = Modifier
                        .size(50.dp)
                        .padding(4.dp))
                }
            }
            Column(
                modifier = Modifier.weight(1f).padding(4.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(percent = 10))
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val secondMessageText = if (viewModel.isSinglePlayer) R.string.computer else R.string.player2
                val secondPlayerIcon = if (viewModel.isSinglePlayer) R.drawable.computer else R.drawable.player2
                MessageText(
                    text = stringResource(id = secondMessageText),
                    textAlign = TextAlign.Center
                )
                Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = secondPlayerIcon), contentDescription = null, modifier = Modifier
                        .size(50.dp)
                        .padding(4.dp))
                    Image(painter = painterResource(id = PlayerRepository.penguinPlayer.icon), contentDescription = null, modifier = Modifier
                        .size(50.dp)
                        .padding(4.dp))
                }
            }
        }
        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
            MessageText(
                text = stringResource(id = R.string.objective_description),
                modifier = Modifier.padding(8.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.hitpoints2),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
        SimpleButton(onClick = { viewModel.proceedWithGame() }, text = stringResource(id = R.string.proceed))
    }
}

@Composable
@Preview
fun StartGamePanelPreview() {
    StartGamePanel(viewModel = UltimateCatBattleViewModel(),
        modifier = Modifier.background(Color.White)
    )
}