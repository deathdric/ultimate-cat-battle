package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.ui.instructions.AttackMovesPanel
import org.deathdric.ultimatecatbattle.ui.instructions.GameBasicsPanel
import org.deathdric.ultimatecatbattle.ui.instructions.SupportMovesPanel

@Composable
fun GameInstructionsDetailsScreen(viewModel: UltimateCatBattleViewModel, activeItem: InstructionsActiveItem, modifier: Modifier = Modifier) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Row {
            SimpleButton(
                onClick = { viewModel.switchMenuActiveItem(MenuActiveItem.MAIN_MENU) },
                text = stringResource(id = R.string.back_to_menu),
                modifier = Modifier.weight(1f)
            )
            SimpleButton(
                onClick = { viewModel.switchMenuActiveItem(MenuActiveItem.GAME_INSTRUCTIONS_MENU) },
                text = stringResource(id = R.string.game_instructions),
                modifier = Modifier.weight(1f)
            )
        }
        TitleText(text = stringResource(id = activeItem.title), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        when (activeItem) {
            InstructionsActiveItem.GAME_BASICS -> {
                GameBasicsPanel(viewModel, modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState()))
            }
            InstructionsActiveItem.ATTACK_MOVES -> {
                AttackMovesPanel(viewModel, modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState()))
            }
            InstructionsActiveItem.SUPPORT_MOVES -> {
                SupportMovesPanel(viewModel, modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState()))
            }
        }
    }
}

@Composable
@Preview
fun GameInstructionsDetailsScreenPreview() {
    GameInstructionsDetailsScreen(viewModel = UltimateCatBattleViewModel(), activeItem = InstructionsActiveItem.GAME_BASICS, modifier = Modifier.background(
        Color.White))
}