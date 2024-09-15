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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.data.SupportRepository
import org.deathdric.ultimatecatbattle.ui.InstructionsActiveItem
import org.deathdric.ultimatecatbattle.ui.InstructionsText
import org.deathdric.ultimatecatbattle.ui.SimpleButton
import org.deathdric.ultimatecatbattle.ui.SupportActionButton
import org.deathdric.ultimatecatbattle.ui.TitleText
import org.deathdric.ultimatecatbattle.ui.UltimateCatBattleViewModel

@Composable
fun SupportMovesPanel(viewModel: UltimateCatBattleViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        InstructionsText(
            text = stringResource(id = R.string.game_supports_part1),
            modifier = Modifier.padding(8.dp)
        )
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SupportActionButton(
                    windowWidthSizeClass = WindowWidthSizeClass.Expanded,
                    windowHeightSizeClass = WindowHeightSizeClass.Compact,
                    supportAction = SupportRepository.speed,
                    showDetails = false,
                    onClick = { /* Do nothing, just for display */ },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(260.dp)
                )
                SupportActionButton(
                    windowWidthSizeClass = WindowWidthSizeClass.Expanded,
                    windowHeightSizeClass = WindowHeightSizeClass.Compact,
                    supportAction = SupportRepository.speed,
                    showDetails = true,
                    onClick = { /* Do nothing, just for display */ },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(260.dp)
                )
            }
        }
        InstructionsText(
            text = stringResource(id = R.string.game_supports_part2),
            modifier = Modifier.padding(8.dp)
        )
        InstructionsText(
            text = stringResource(id = R.string.game_supports_part3),
            modifier = Modifier.padding(8.dp)
        )
        InstructionsText(
            text = stringResource(id = R.string.game_supports_part4),
            modifier = Modifier.padding(8.dp)
        )
        InstructionsText(
            text = stringResource(id = R.string.game_supports_part5),
            modifier = Modifier.padding(8.dp)
        )
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
                        painter = painterResource(id = R.drawable.attack),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp).size(60.dp)
                    )
                }
                Column(modifier = Modifier.weight(3f)) {
                    TitleText(
                        text = stringResource(id = R.string.stat_attack),
                        modifier = Modifier.padding(8.dp)
                    )
                    InstructionsText(
                        text = stringResource(id = R.string.game_supports_attack_part1),
                        modifier = Modifier.padding(8.dp)
                    )
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
                        painter = painterResource(id = R.drawable.defense),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp).size(60.dp)
                    )
                }
                Column(modifier = Modifier.weight(3f)) {
                    TitleText(
                        text = stringResource(id = R.string.stat_defense),
                        modifier = Modifier.padding(8.dp)
                    )
                    InstructionsText(
                        text = stringResource(id = R.string.game_supports_defense_part1),
                        modifier = Modifier.padding(8.dp)
                    )
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
                        painter = painterResource(id = R.drawable.hit),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp).size(60.dp)
                    )
                }
                Column(modifier = Modifier.weight(3f)) {
                    TitleText(
                        text = stringResource(id = R.string.stat_hit),
                        modifier = Modifier.padding(8.dp)
                    )
                    InstructionsText(
                        text = stringResource(id = R.string.game_supports_hit_part1),
                        modifier = Modifier.padding(8.dp)
                    )
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
                        painter = painterResource(id = R.drawable.avoid2),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp).size(60.dp)
                    )
                }
                Column(modifier = Modifier.weight(3f)) {
                    TitleText(
                        text = stringResource(id = R.string.stat_avoid),
                        modifier = Modifier.padding(8.dp)
                    )
                    InstructionsText(
                        text = stringResource(id = R.string.game_supports_avoid_part1),
                        modifier = Modifier.padding(8.dp)
                    )
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
                        modifier = Modifier.padding(8.dp).size(60.dp)
                    )
                }
                Column(modifier = Modifier.weight(3f)) {
                    TitleText(
                        text = stringResource(id = R.string.stat_crit),
                        modifier = Modifier.padding(8.dp)
                    )
                    InstructionsText(
                        text = stringResource(id = R.string.game_supports_crit_part1),
                        modifier = Modifier.padding(8.dp)
                    )
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
                        modifier = Modifier.padding(8.dp).size(60.dp)
                    )
                }
                Column(modifier = Modifier.weight(3f)) {
                    TitleText(
                        text = stringResource(id = R.string.time_cost),
                        modifier = Modifier.padding(8.dp)
                    )
                    InstructionsText(
                        text = stringResource(id = R.string.game_supports_time_part1),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
        Row {
            SimpleButton(
                onClick = { viewModel.switchInstructionsItem(InstructionsActiveItem.ATTACK_MOVES) },
                text = stringResource(id = R.string.game_attacks),
                modifier = Modifier.padding(8.dp).weight(1f)
            )
            Spacer(modifier = Modifier.padding(8.dp).weight(1f))
        }
    }
}

@Preview
@Composable
fun SupportMovesPanelPreview() {
    SupportMovesPanel(
        viewModel = UltimateCatBattleViewModel(),
        modifier = Modifier
        .background(
            Color.White
        )
        .fillMaxSize()
        .verticalScroll(rememberScrollState()))
}