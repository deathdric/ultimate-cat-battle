package org.deathdric.ultimatecatbattle.ui.instructions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
import org.deathdric.ultimatecatbattle.data.AttackRepository
import org.deathdric.ultimatecatbattle.ui.AttackActionButton
import org.deathdric.ultimatecatbattle.ui.InstructionsActiveItem
import org.deathdric.ultimatecatbattle.ui.InstructionsText
import org.deathdric.ultimatecatbattle.ui.SimpleButton
import org.deathdric.ultimatecatbattle.ui.TitleText
import org.deathdric.ultimatecatbattle.ui.UltimateCatBattleViewModel

@Composable
fun AttackMovesPanel(viewModel: UltimateCatBattleViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        InstructionsText(text = stringResource(id = R.string.game_attacks_part1), modifier = Modifier.padding(8.dp))
        Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AttackActionButton(
                    windowWidthSizeClass = WindowWidthSizeClass.Expanded,
                    windowHeightSizeClass = WindowHeightSizeClass.Compact,
                    attackAction = AttackRepository.dragonFist,
                    showDetails = false,
                    onClick = { /* Do nothing, just for display */ },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(260.dp))
                AttackActionButton(
                    windowWidthSizeClass = WindowWidthSizeClass.Expanded,
                    windowHeightSizeClass = WindowHeightSizeClass.Compact,
                    attackAction = AttackRepository.dragonFist,
                    showDetails = true,
                    onClick = { /* Do nothing, just for display */ },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(260.dp))
            }
        }

        InstructionsText(text = stringResource(id = R.string.game_attacks_part2), modifier = Modifier.padding(8.dp))
        Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
            .padding(4.dp)
            .border(2.dp, Color.Black)) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .padding(4.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.damage),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(60.dp)
                    )
                }
                Column(modifier = Modifier.weight(3f)) {
                    TitleText(
                        text = "%s / %s".format(stringResource(id = R.string.min_damage), stringResource(id = R.string.max_damage)),
                        modifier = Modifier.padding(8.dp)
                    )
                    InstructionsText(text = stringResource(id = R.string.game_attacks_damage_part1), modifier = Modifier.padding(8.dp))
                    InstructionsText(text = stringResource(id = R.string.game_attacks_damage_part2), modifier = Modifier.padding(8.dp))
                    InstructionsText(text = stringResource(id = R.string.game_attacks_damage_part3), modifier = Modifier.padding(8.dp))
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .padding(4.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hit_chance),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(60.dp)
                    )
                }
                Column(modifier = Modifier.weight(3f)) {
                    TitleText(
                        text = stringResource(id = R.string.chance_to_hit)+ " (%)",
                        modifier = Modifier.padding(8.dp)
                    )
                    InstructionsText(text = stringResource(id = R.string.game_attacks_hit_part1), modifier = Modifier.padding(8.dp))
                    InstructionsText(text = stringResource(id = R.string.game_attacks_hit_part2), modifier = Modifier.padding(8.dp))
                    InstructionsText(text = stringResource(id = R.string.game_attacks_hit_part3), modifier = Modifier.padding(8.dp))
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .padding(4.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.critical),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(60.dp)
                    )
                }
                Column(modifier = Modifier.weight(3f)) {
                    TitleText(
                        text = stringResource(id = R.string.chance_to_crit) + " (%)",
                        modifier = Modifier.padding(8.dp)
                    )
                    InstructionsText(text = stringResource(id = R.string.game_attacks_crit_part1), modifier = Modifier.padding(8.dp))
                    InstructionsText(text = stringResource(id = R.string.game_attacks_crit_part2), modifier = Modifier.padding(8.dp))
                    InstructionsText(text = stringResource(id = R.string.game_attacks_crit_part3), modifier = Modifier.padding(8.dp))
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .padding(4.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(60.dp)
                    )
                }
                Column(modifier = Modifier.weight(3f)) {
                    TitleText(
                        text = stringResource(id = R.string.time_cost),
                        modifier = Modifier.padding(8.dp)
                    )
                    InstructionsText(text = stringResource(id = R.string.game_attacks_time_part1), modifier = Modifier.padding(8.dp))
                }
            }
        }
        Row {
            SimpleButton(
                onClick = { viewModel.switchInstructionsItem(InstructionsActiveItem.GAME_BASICS) },
                text = stringResource(id = R.string.game_basics),
                modifier = Modifier.padding(8.dp).weight(1f)
            )
            SimpleButton(
                onClick = { viewModel.switchInstructionsItem(InstructionsActiveItem.SUPPORT_MOVES) },
                text = stringResource(id = R.string.game_supports),
                modifier = Modifier.padding(8.dp).weight(1f)
            )
        }
    }
}

@Composable
@Preview
fun AttackMovesPanelPreview() {
    AttackMovesPanel(
        viewModel = UltimateCatBattleViewModel(),
        modifier = Modifier
            .background(
                Color.White
            )
            .fillMaxSize()
            .verticalScroll(rememberScrollState()))
}