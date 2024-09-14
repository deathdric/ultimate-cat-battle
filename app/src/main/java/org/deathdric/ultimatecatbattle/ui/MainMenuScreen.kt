package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R

@Composable
fun MainMenuScreen(viewModel: UltimateCatBattleViewModel, uiState: UltimateCatBattleUiState, modifier: Modifier = Modifier) {
    Row(modifier = Modifier
        .safeContentPadding()
        .statusBarsPadding()
        .systemBarsPadding()
        .navigationBarsPadding()
        .captionBarPadding()
        .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = R.drawable.cat2), contentDescription = null, modifier = Modifier.size(100.dp))
        when(uiState.menuState.activeItem) {
            MenuActiveItem.MAIN_MENU -> {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SimpleButton(
                        onClick = { viewModel.quitMenu() },
                        text = stringResource(id = R.string.resume_game)
                    )
                    SimpleButton(
                        onClick = { viewModel.switchMenuActiveItem(MenuActiveItem.CONFIRM_QUIT_GAME) },
                        text = stringResource(id = R.string.main_screen_return)
                    )
                    SimpleButton(
                        onClick = { viewModel.switchMenuActiveItem(MenuActiveItem.ABOUT_SCREEN) },
                        text = stringResource(id = R.string.about_game)
                    )
                }

            }
            MenuActiveItem.CONFIRM_QUIT_GAME -> {
                ConfirmDialog(questionText = stringResource(id = R.string.confirm_go_main_screen), onConfirm = { viewModel.returnToMainScreen() }, onCancel = { viewModel.displayMenu() },
                    modifier = Modifier.weight(1f))
            }
            MenuActiveItem.ABOUT_SCREEN -> {
                AboutScreen(viewModel = viewModel, modifier = Modifier.weight(1f))
            }
        }
        Image(painter = painterResource(id = R.drawable.penguin1), contentDescription = null, modifier = Modifier.size(100.dp))
    }
}

@Composable
@Preview
fun MainMenuScreenPreview() {
    val viewModel = UltimateCatBattleViewModel()
    viewModel.startGame(false)
    MainMenuScreen(viewModel = viewModel, uiState = viewModel.uiState.collectAsState().value)
}