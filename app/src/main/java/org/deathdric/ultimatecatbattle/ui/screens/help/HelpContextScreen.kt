package org.deathdric.ultimatecatbattle.ui.screens.help

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.screens.getPlayerScreenDisplayOptions
import org.deathdric.ultimatecatbattle.ui.screens.getStartScreenDisplayOptions
import org.deathdric.ultimatecatbattle.ui.screens.getTeamScreenDisplayOptions
import org.deathdric.ultimatecatbattle.ui.screens.toActivePlayerPanelDisplayOptions
import org.deathdric.ultimatecatbattle.ui.toolkit.ElementSizeMode
import org.deathdric.ultimatecatbattle.ui.toolkit.SimpleButton
import org.deathdric.ultimatecatbattle.ui.toolkit.TitleText
import org.deathdric.ultimatecatbattle.vm.GameStatus
import org.deathdric.ultimatecatbattle.vm.RootUiStatus
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleUiState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

data class HelpContextScreenDisplayOptions (
    val titleSizeMode: ElementSizeMode,
    val textSize: TextUnit,
    val lineHeight: TextUnit,
    val buttonSizeMode: ElementSizeMode,
    val bottomPadding: Dp
)

fun ScreenConstraints.toHelpContextScreenDisplayOptions () : HelpContextScreenDisplayOptions {
    val bottomPadding = if (this.maxHeight < 600.dp && this.maxWidth < 900.dp) {
        0.dp
    } else {
        30.dp
    }

    val titleSizeMode = if (this.maxHeight < 480.dp) {
        ElementSizeMode.NORMAL
    } else if (this.maxHeight < 700.dp) {
        ElementSizeMode.LARGE
    } else {
        ElementSizeMode.VERY_LARGE
    }

    val textSize = if (maxHeight < 330.dp) {
        12.sp
    } else if (maxHeight < 500.dp) {
        13.sp
    } else if (maxWidth < 900.dp) {
        14.sp
    } else if(maxWidth < 1000.dp) {
        16.sp
    } else {
        18.sp
    }

    val lineHeight = (textSize * 5)/4

    val buttonSizeMode = if (this.maxHeight < 400.dp) {
        ElementSizeMode.NORMAL
    } else if (this.maxHeight < 650.dp) {
        ElementSizeMode.LARGE
    } else {
        ElementSizeMode.VERY_LARGE
    }
    return HelpContextScreenDisplayOptions(titleSizeMode, textSize, lineHeight, buttonSizeMode, bottomPadding)
}

@Composable
fun HelpContextScreen(screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
                      uiState: UltimateCatBattleUiState, modifier: Modifier = Modifier
) {
    val displayOptions = screenConstraints.toHelpContextScreenDisplayOptions()

    Column(modifier = modifier.background(colorResource(id = R.color.title_background_color))) {
        Row (modifier = Modifier.fillMaxSize()) {
            Column (modifier = Modifier.weight(1f)) {
                Spacer(modifier = Modifier.fillMaxHeight())
            }
            val titleText = when(uiState.rootUiStatus) {
                RootUiStatus.START_SCREEN -> R.string.help_start_screen_title
                RootUiStatus.PLAYER_SELECT -> R.string.help_player_select_screen_title
                RootUiStatus.TEAM_SELECT -> R.string.help_team_select_screen_title
                RootUiStatus.MAIN_GAME -> {
                    when(uiState.gameState!!.status) {
                        GameStatus.INTRO -> R.string.help_game_intro_screen_title
                        GameStatus.PLAYER_TURN -> R.string.help_game_start_turn_screen_title
                        GameStatus.MOVE_SELECT -> R.string.help_game_select_move_screen_title
                        GameStatus.ATTACK_CONFIRM -> R.string.help_game_attack_confirm_screen_title
                        GameStatus.SUPPORT_CONFIRM -> R.string.help_game_support_confirm_screen_title
                        GameStatus.ATTACK_EXECUTION -> R.string.help_game_attack_execution_screen_title
                        GameStatus.SUPPORT_EXECUTION -> R.string.help_game_support_execution_screen_title
                        GameStatus.ATTACK_RESULT -> R.string.help_game_attack_result_screen_title
                        GameStatus.SUPPORT_RESULT -> R.string.help_game_support_result_screen_title
                        GameStatus.GAME_OVER -> R.string.help_game_over_screen_title
                        else -> R.string.help
                    }
                }
            }
            Column (modifier = Modifier
                .weight(3f)
                .padding(bottom = displayOptions.bottomPadding)) {
                Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .border(1.dp, Color.Black)
                        .background(Color.White)
                        .padding(4.dp)
                        .fillMaxWidth()) {
                    TitleText(text = stringResource(id = titleText), sizeMode = displayOptions.titleSizeMode)
                }
                Row (horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 4.dp)
                        .border(1.dp, Color.Black)
                        .background(Color.White)
                        .padding(4.dp)
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(ScrollState(0))) {
                    when (uiState.rootUiStatus) {
                        RootUiStatus.START_SCREEN -> {
                            StartContextScreen(
                                helpContextScreenDisplayOptions = displayOptions,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        RootUiStatus.PLAYER_SELECT -> {
                            PlayerSelectContextScreen(
                                helpContextScreenDisplayOptions = displayOptions,
                                playerSelectScreenDisplayOptions = screenConstraints.getPlayerScreenDisplayOptions(),
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        RootUiStatus.TEAM_SELECT -> {
                            TeamSelectContextScreen(
                                helpContextScreenDisplayOptions = displayOptions,
                                teamSelectScreenDisplayOptions = screenConstraints.getTeamScreenDisplayOptions(),
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        RootUiStatus.MAIN_GAME -> {
                            when(uiState.gameState!!.status) {
                                GameStatus.INTRO -> {
                                    GameIntroContextScreen(helpContextScreenDisplayOptions = displayOptions,
                                        modifier = Modifier.fillMaxSize())
                                }
                                GameStatus.PLAYER_TURN -> {
                                    StartTurnContextScreen(
                                        helpContextScreenDisplayOptions = displayOptions,
                                        activePlayerPanelDisplayOptions = screenConstraints.toActivePlayerPanelDisplayOptions(),
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                GameStatus.MOVE_SELECT -> {
                                    MoveSelectContextScreen(
                                        helpContextScreenDisplayOptions = displayOptions,
                                        activePlayerPanelDisplayOptions = screenConstraints.toActivePlayerPanelDisplayOptions(),
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                GameStatus.ATTACK_CONFIRM -> {
                                    AttackConfirmContextScreen(
                                        helpContextScreenDisplayOptions = displayOptions,
                                        activePlayerPanelDisplayOptions = screenConstraints.toActivePlayerPanelDisplayOptions(),
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                GameStatus.SUPPORT_CONFIRM -> {
                                    SupportConfirmContextScreen(
                                        helpContextScreenDisplayOptions = displayOptions,
                                        activePlayerPanelDisplayOptions = screenConstraints.toActivePlayerPanelDisplayOptions(),
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                GameStatus.ATTACK_EXECUTION -> {
                                    AttackExecutionContextScreen(helpContextScreenDisplayOptions = displayOptions,
                                        modifier = Modifier.fillMaxSize())
                                }
                                GameStatus.SUPPORT_EXECUTION -> {
                                    SupportExecutionContextScreen(helpContextScreenDisplayOptions = displayOptions,
                                        modifier = Modifier.fillMaxSize())
                                }
                                GameStatus.ATTACK_RESULT -> {
                                    AttackResultContextScreen(helpContextScreenDisplayOptions = displayOptions,
                                        modifier = Modifier.fillMaxSize())
                                }
                                GameStatus.SUPPORT_RESULT -> {
                                    SupportResultContextScreen(helpContextScreenDisplayOptions = displayOptions,
                                        modifier = Modifier.fillMaxSize())
                                }
                                GameStatus.GAME_OVER -> {
                                    GameOverContextScreen(helpContextScreenDisplayOptions = displayOptions,
                                        modifier = Modifier.fillMaxSize())
                                }
                                else -> {

                                }
                            }
                        }

                    }
                }
                Row(modifier = Modifier
                    .border(1.dp, Color.Black)
                    .background(Color.White)) {
                    SimpleButton(
                        onClick = { viewModel.toggleContextHelp(false) },
                        sizeMode = displayOptions.buttonSizeMode,
                        text = stringResource(id = R.string.back)
                    )
                }
            }
            Column (modifier = Modifier.weight(1f)) {
                Spacer(modifier = Modifier.fillMaxHeight())
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row (modifier = Modifier.background(Color.White)) {
        }
    }
}

@Composable
fun GenericHelpContextScreenPreview(screenConstraints: ScreenConstraints) {
    val viewModel = UltimateCatBattleViewModel()
    val uiState = viewModel.uiState.collectAsState().value
    HelpContextScreen(
        screenConstraints,
        UltimateCatBattleViewModel(),
        uiState,
        Modifier
            .safeContentPadding()
            .statusBarsPadding()
            .systemBarsPadding()
            .navigationBarsPadding()
            .captionBarPadding()
            .background(Color.White)
            .requiredSize(screenConstraints.maxWidth, screenConstraints.maxHeight)
    )
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=560dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun HelpContextScreenPreviewVerySmall() {
    GenericHelpContextScreenPreview(screenConstraints = ScreenConstraints(560.dp, 300.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=740dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun HelpContextScreenPreviewSmallUnderLimit() {
    GenericHelpContextScreenPreview(screenConstraints = ScreenConstraints(740.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=750dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun HelpContextScreenPreviewMediumWidthOverLimit() {
    GenericHelpContextScreenPreview(screenConstraints = ScreenConstraints(750.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun HelpContextScreenPreviewMediumWidthHigherPadding() {
    GenericHelpContextScreenPreview(screenConstraints = ScreenConstraints(800.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=900dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun HelpContextScreenPreviewLargeWidth() {
    GenericHelpContextScreenPreview(screenConstraints = ScreenConstraints(900.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=700dp,height=500dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun HelpContextScreenPreviewMediumWidthMediumHeight() {
    GenericHelpContextScreenPreview(screenConstraints = ScreenConstraints(700.dp, 500.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=500dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun HelpContextScreenPreviewMediumWidthMediumHeightMaxPadding() {
    GenericHelpContextScreenPreview(screenConstraints = ScreenConstraints(800.dp, 500.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=630dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun HelpContextScreenPreviewMediumWidthLargeHeight() {
    GenericHelpContextScreenPreview(screenConstraints = ScreenConstraints(800.dp, 630.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=1280dp,height=701dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun HelpContextScreenPreviewLargeSize() {
    GenericHelpContextScreenPreview(screenConstraints = ScreenConstraints(1280.dp, 701.dp))
}