package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.data.AttackRepository
import org.deathdric.ultimatecatbattle.data.PlayerRepository
import org.deathdric.ultimatecatbattle.data.SupportRepository
import org.deathdric.ultimatecatbattle.model.AttackAction
import org.deathdric.ultimatecatbattle.model.AttackResult
import org.deathdric.ultimatecatbattle.model.Player

@Composable
fun AttackDonePanel(viewModel: UltimateCatBattleViewModel, uiState: UltimateCatBattleUiState, modifier: Modifier = Modifier) {

    val resultIcon = if (uiState.attackResult!!.critical) {R.drawable.attack_critical}
        else if (uiState.attackResult.success) {R.drawable.attack_success}
        else { R.drawable.attack_fail}

    Column (modifier = modifier.padding(16.dp)) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            Column (modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)){
                MessageText(
                    text = stringResource(id = R.string.used_skill_message).format(
                        stringResource(id = uiState.activePlayerName),
                        stringResource(id = uiState.lastAttack!!.name),
                    ),
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                if (uiState.attackResult.critical) {
                    MessageText(
                        text = stringResource(id = R.string.critical_message),
                        color = Color.Red
                    )
                }
                if (uiState.attackResult.success) {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        MessageText(
                            text = stringResource(id = R.string.damage_done_message),
                            color = Color.Black,
                            fontWeight = FontWeight.Normal
                        )
                        MessageText(
                            text = "${uiState.attackResult.damage}",
                            color = Color.Black,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                } else {
                    MessageText(
                        text = stringResource(id = R.string.failed_attack_message),
                        color = Color.Blue
                    )
                }
            }
            Image(painter = painterResource(id = resultIcon), contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp))
        }
        if (uiState.isGameOver) {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(id = R.drawable.trophy),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp).size(30.dp)
                )
                TitleText(
                    text = stringResource(id = R.string.player_wins).format(
                        stringResource(id = uiState.activePlayerName)
                    ),
                    color = Color.Black
                )
                Image(
                    painter = painterResource(id = R.drawable.trophy),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp).size(30.dp)
                )
            }
            SimpleButton(
                onClick = { viewModel.returnToMainScreen()},
                text = stringResource(id = R.string.main_screen_return),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        } else {
            SimpleButton(
                onClick = { viewModel.postAction() },
                text = stringResource(id = R.string.proceed),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview
fun AttackDonePanelPreview() {

    AttackDonePanel(viewModel = UltimateCatBattleViewModel(), uiState = UltimateCatBattleUiState(
        activePlayerId = 1,
        activePlayerIcon = PlayerRepository.catPlayer.icon,
        activePlayerName = PlayerRepository.catPlayer.name,
        player1 = Player(PlayerRepository.catPlayer).toStatus(true),
        player2 = Player(PlayerRepository.penguinPlayer).toStatus(false),
        remainingTime = 20,
        actionMode = ActionMode.ATTACK_DONE,
        lastAttack = AttackRepository.dragonFist,
        attackResult = AttackResult(success = true, critical = true, damage = 50)
    ), modifier = Modifier.background(Color.White))
}

@Composable
@Preview
fun AttackMissedPanelPreview() {

    AttackDonePanel(viewModel = UltimateCatBattleViewModel(), uiState = UltimateCatBattleUiState(
        activePlayerId = 1,
        activePlayerIcon = PlayerRepository.catPlayer.icon,
        activePlayerName = PlayerRepository.catPlayer.name,
        player1 = Player(PlayerRepository.catPlayer).toStatus(true),
        player2 = Player(PlayerRepository.penguinPlayer).toStatus(false),
        remainingTime = 20,
        actionMode = ActionMode.ATTACK_DONE,
        lastAttack = AttackRepository.dragonFist,
        attackResult = AttackResult(success = false, critical = false, damage = 0)
    ), modifier = Modifier.background(Color.White))
}

@Composable
@Preview
fun AttackDonePanelOnGameOverPreview() {

    AttackDonePanel(viewModel = UltimateCatBattleViewModel(), uiState = UltimateCatBattleUiState(
        activePlayerId = 1,
        activePlayerIcon = PlayerRepository.catPlayer.icon,
        activePlayerName = PlayerRepository.catPlayer.name,
        player1 = Player(PlayerRepository.catPlayer).toStatus(true),
        player2 = Player(PlayerRepository.penguinPlayer).toStatus(false),
        remainingTime = 20,
        isGameOver = true,
        actionMode = ActionMode.ATTACK_DONE,
        lastAttack = AttackRepository.dragonFist,
        attackResult = AttackResult(success = true, critical = false, damage = 50)
    ), modifier = Modifier.background(Color.White))
}