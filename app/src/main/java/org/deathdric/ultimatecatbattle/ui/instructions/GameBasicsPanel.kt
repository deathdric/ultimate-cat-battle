package org.deathdric.ultimatecatbattle.ui.instructions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import org.deathdric.ultimatecatbattle.ui.InstructionsActiveItem
import org.deathdric.ultimatecatbattle.ui.InstructionsText
import org.deathdric.ultimatecatbattle.ui.MessageText
import org.deathdric.ultimatecatbattle.ui.SimpleButton
import org.deathdric.ultimatecatbattle.ui.UltimateCatBattleViewModel

@Composable
fun GameBasicsPanel(viewModel: UltimateCatBattleViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        InstructionsText(text = stringResource(id = R.string.game_basics_part1), modifier = Modifier.padding(8.dp))
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)){
            MessageText(text = stringResource(id = R.string.game_basics_hit_points))
            Image(painter = painterResource(id = R.drawable.hitpoints2), contentDescription = null, modifier = Modifier.size(24.dp))
        }
        InstructionsText(text = stringResource(id = R.string.game_basics_part2), modifier = Modifier.padding(8.dp), textAlign = TextAlign.Start)
        InstructionsText(text = stringResource(id = R.string.game_basics_part3), modifier = Modifier.padding(8.dp))
        InstructionsText(text = stringResource(id = R.string.game_basics_part4), modifier = Modifier.padding(8.dp))
        MessageText(text = stringResource(id = R.string.game_basics_top_bar), modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), textAlign = TextAlign.Center)
        Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
            .padding(4.dp)
            .border(2.dp, Color.Black)) {
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(4.dp)) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.menu), contentDescription = null, modifier = Modifier.size(40.dp))
                }
                Column(modifier = Modifier.weight(3f)) {
                    InstructionsText(text = stringResource(id = R.string.in_game_menu), modifier = Modifier.padding(8.dp))
                }
            }
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(4.dp)) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.details_on), contentDescription = null, modifier = Modifier.size(40.dp))
                    Image(painter = painterResource(id = R.drawable.details_off), contentDescription = null, modifier = Modifier.size(40.dp))
                }
                Column(modifier = Modifier.weight(3f)) {
                    InstructionsText(text = stringResource(id = R.string.switch_move_details), modifier = Modifier.padding(8.dp))
                }
            }
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(4.dp)) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.player1), contentDescription = null, modifier = Modifier
                        .padding(2.dp)
                        .size(40.dp))
                    Image(painter = painterResource(id = R.drawable.player2), contentDescription = null, modifier = Modifier
                        .padding(2.dp)
                        .size(40.dp))
                    Image(painter = painterResource(id = R.drawable.computer), contentDescription = null, modifier = Modifier
                        .padding(2.dp)
                        .size(40.dp))
                }
                Column(modifier = Modifier.weight(3f)) {
                    InstructionsText(text = stringResource(id = R.string.active_player_display), modifier = Modifier.padding(8.dp))
                }
            }
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(4.dp)) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.cat2), contentDescription = null, modifier = Modifier.size(40.dp))
                    Image(painter = painterResource(id = R.drawable.penguin1), contentDescription = null, modifier = Modifier.size(40.dp))
                }
                Column(modifier = Modifier.weight(3f)) {
                    InstructionsText(text = stringResource(id = R.string.active_character_display), modifier = Modifier.padding(8.dp))
                }
            }
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(4.dp)) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.clock), contentDescription = null, modifier = Modifier.size(40.dp))
                }
                Column(modifier = Modifier.weight(3f)) {
                    InstructionsText(text = stringResource(id = R.string.remaining_time_display), modifier = Modifier.padding(8.dp))
                }
            }
        }
        Row {
            Spacer(modifier = Modifier.padding(8.dp).weight(1f))
            SimpleButton(
                onClick = { viewModel.switchInstructionsItem(InstructionsActiveItem.ATTACK_MOVES) },
                text = stringResource(id = R.string.game_attacks),
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )
        }
    }
}

@Composable
@Preview
fun GameBasicPanelPreview() {
    GameBasicsPanel(
        viewModel = UltimateCatBattleViewModel(),
        modifier = Modifier
            .background(
                Color.White
            )
            .fillMaxSize()
            .verticalScroll(rememberScrollState()))
}