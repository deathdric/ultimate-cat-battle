package org.deathdric.ultimatecatbattle.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.PlayerId
import org.deathdric.ultimatecatbattle.model.PlayerType
import org.deathdric.ultimatecatbattle.model.StatusModifier
import org.deathdric.ultimatecatbattle.model.Team
import org.deathdric.ultimatecatbattle.model.TeamId
import org.deathdric.ultimatecatbattle.model.createNewPlayer
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.aliveIcon
import org.deathdric.ultimatecatbattle.ui.color
import org.deathdric.ultimatecatbattle.ui.deadIcon
import org.deathdric.ultimatecatbattle.ui.hitPointsColor
import org.deathdric.ultimatecatbattle.ui.icon
import org.deathdric.ultimatecatbattle.ui.name
import org.deathdric.ultimatecatbattle.ui.playerStatColor
import org.deathdric.ultimatecatbattle.ui.toolkit.ElementSizeMode
import org.deathdric.ultimatecatbattle.ui.toolkit.HelpIcon
import org.deathdric.ultimatecatbattle.vm.GamePlayerInfo
import org.deathdric.ultimatecatbattle.vm.GameStatus
import org.deathdric.ultimatecatbattle.vm.GameUiState
import org.deathdric.ultimatecatbattle.vm.PlayerState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

data class MainGameScreenDisplayOptions(
    val mainColumnWeight: Float,
    val characterPanelWeight: Float,
    val activePlayerPanelWeight: Float
)

data class SharedGameScreenDisplayOptions (
    val titleSizeMode: ElementSizeMode,
    val titlePadding: Dp,
    val buttonSizeMode: ElementSizeMode,
    val bottomPadding: Dp
)

data class CharacterPanelDisplayOptions (
    val iconSize: Dp,
    val statisticsIconSize: Dp,
    val statisticTextSize: TextUnit
)

data class ActivePlayerPanelDisplayOptions (
    val menuIconSize: Dp,
    val iconSize: Dp,
    val largeIconSize: Dp,
    val statisticsIconSize: Dp,
    val statisticTextSize: TextUnit,
    val menuBottomPadding: Dp
)

fun ScreenConstraints.toSharedGameScreenDisplayOptions() : SharedGameScreenDisplayOptions {
    val titleSizeMode = if (this.maxHeight < 400.dp) {
        ElementSizeMode.SMALL
    } else if (this.maxHeight < 480.dp) {
        ElementSizeMode.NORMAL
    } else if (this.maxHeight < 700.dp) {
        ElementSizeMode.LARGE
    } else {
        ElementSizeMode.VERY_LARGE
    }

    val titlePadding = if (this.maxHeight > 600.dp) {
        16.dp
    } else if (this.maxHeight > 480.dp) {
        12.dp
    } else {
        8.dp
    }
    val bottomPadding = if (this.maxHeight < 600.dp && this.maxWidth < 650.dp) {
        0.dp
    } else {
        30.dp
    }

    val buttonSizeMode = if (this.maxHeight < 400.dp) {
        ElementSizeMode.NORMAL
    } else if (this.maxHeight < 650.dp) {
        ElementSizeMode.LARGE
    } else {
        ElementSizeMode.VERY_LARGE
    }

    return SharedGameScreenDisplayOptions(titleSizeMode = titleSizeMode, titlePadding,
        buttonSizeMode = buttonSizeMode,  bottomPadding = bottomPadding)
}

fun ScreenConstraints.toMainGameScreenDisplayOptions() : MainGameScreenDisplayOptions {
    val mainColumnWeight = if (maxWidth >= 1200.dp) {
        3.5f
    } else if (maxWidth >= 900.dp) {
        3.2f
    } else if (maxHeight < 500.dp && maxWidth >= 900.dp) {
        3.5f
    } else if (maxHeight < 400.dp && maxWidth >= 900.dp) {
        3.8f
    } else if (maxHeight < 350.dp && maxWidth >= 750.dp) {
        3.5f
    } else {
        3f
    }
    return MainGameScreenDisplayOptions(mainColumnWeight = mainColumnWeight, activePlayerPanelWeight = 1.2f, characterPanelWeight = 1f)
}

fun ScreenConstraints.toActivePlayerPanelDisplayOptions() : ActivePlayerPanelDisplayOptions {
    val menuIconSize = if (maxHeight < 500.dp) {
        50.dp
    } else if (maxWidth < 800.dp) {
        55.dp
    } else if (maxWidth < 950.dp) {
        60.dp
    } else {
        80.dp
    }

    val iconSize = if (maxHeight < 500.dp) {
        40.dp
    } else if (maxWidth < 800.dp) {
        45.dp
    } else if (maxWidth < 950.dp) {
        50.dp
    } else {
        80.dp
    }
    val statIconSize = if (maxHeight < 330.dp) {
        20.dp
    } else if (maxHeight < 500.dp) {
        25.dp
    } else if (maxWidth < 900.dp) {
        30.dp
    } else {
        50.dp
    }
    val statisticsTextSize = if (maxHeight < 330.dp) {
        14.sp
    } else if (maxWidth < 900.dp) {
        15.sp
    } else if(maxWidth < 1000.dp) {
        18.sp
    } else {
        20.sp
    }
    val largeIconSize = iconSize * 2

    val menuBottomPadding = if (maxHeight < 400.dp) {
        2.dp
    } else if (maxHeight < 500.dp) {
        4.dp
    } else {
        8.dp
    }

    return ActivePlayerPanelDisplayOptions(menuIconSize = menuIconSize, iconSize = iconSize, largeIconSize = largeIconSize, statisticsIconSize = statIconSize,
        statisticTextSize = statisticsTextSize, menuBottomPadding = menuBottomPadding)
}

fun ScreenConstraints.toCharacterPanelDisplayOptions() : CharacterPanelDisplayOptions {
    val iconSize = if (maxHeight < 500.dp) {
        40.dp
    } else if (maxWidth < 800.dp) {
        45.dp
    } else if (maxWidth < 950.dp) {
        50.dp
    } else {
        80.dp
    }
    val statIconSize = if (maxHeight < 330.dp) {
        20.dp
    } else if (maxHeight < 500.dp) {
        25.dp
    } else if (maxWidth < 900.dp) {
        30.dp
    } else if (maxWidth < 1000.dp) {
        40.dp
    } else {
        50.dp
    }
    val statisticsTextSize = if (maxHeight < 330.dp) {
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
    return CharacterPanelDisplayOptions(iconSize = iconSize, statisticsIconSize = statIconSize,
        statisticTextSize = statisticsTextSize)
}

@Composable
fun GameDebugPanel(screenConstraints: ScreenConstraints, parentScreenConstraints: ScreenConstraints, modifier: Modifier = Modifier) {
    Column (modifier = modifier){
        Text(text = screenConstraints.toString())
        Text(text = parentScreenConstraints.toString())
    }
}

@Composable
fun MainGameScreen(screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
                   gameState: GameUiState, modifier: Modifier = Modifier
) {
    val mainScreenDisplayOptions = screenConstraints.toMainGameScreenDisplayOptions()
    val characterPanelDisplayOptions = screenConstraints.toCharacterPanelDisplayOptions()
    Row (modifier = modifier) {
        val remainingTime = gameState.playerInfo.computeRemainingTime()

        val showActiveData = gameState.status != GameStatus.INTRO;
        ActivePlayerSidePanel(
            playerState = gameState.playerInfo.activePlayer,
            remainingTime = remainingTime,
            showActivePlayer = showActiveData,
            characterPanelDisplayOptions = screenConstraints.toActivePlayerPanelDisplayOptions(),
            viewModel = viewModel,
            modifier = Modifier
                .weight(mainScreenDisplayOptions.activePlayerPanelWeight)
                .background(colorResource(id = R.color.title_background_color))
                .fillMaxHeight()
        )
        BoxWithConstraints (modifier = Modifier
            .padding(1.dp)
            .border(1.dp, Color.Black)
            .weight(mainScreenDisplayOptions.mainColumnWeight)
            .fillMaxHeight()) {
            val screenContentConstraints = ScreenConstraints(maxWidth = this.maxWidth, maxHeight = this.maxHeight)
            when(gameState.status) {
                GameStatus.DEBUG -> {
                    GameDebugPanel(screenConstraints = screenContentConstraints, parentScreenConstraints = screenConstraints,
                        modifier = Modifier.fillMaxSize())
                }
                GameStatus.INTRO -> GameIntroScreen(
                    screenConstraints = screenContentConstraints,
                    viewModel = viewModel,
                    gameState = gameState,
                    modifier = Modifier.fillMaxSize()
                )
                GameStatus.PLAYER_TURN -> PlayerTurnScreen(
                    screenConstraints = screenContentConstraints,
                    viewModel = viewModel,
                    gameState = gameState,
                    modifier = Modifier.fillMaxSize()
                )
                GameStatus.MOVE_SELECT -> MoveSelectScreen(
                    screenConstraints = screenContentConstraints,
                    viewModel = viewModel,
                    gameState = gameState,
                    modifier = Modifier.fillMaxSize()
                )
                GameStatus.ATTACK_CONFIRM -> AttackConfirmScreen(
                    screenConstraints = screenContentConstraints,
                    viewModel = viewModel,
                    attackSelectionState = gameState.selectedAttack!!,
                    modifier = Modifier.fillMaxSize()
                )
                GameStatus.ATTACK_EXECUTION -> AttackExecutionScreen(
                    screenConstraints = screenContentConstraints,
                    viewModel = viewModel,
                    playerInfo = gameState.playerInfo,
                    attackSelectionState = gameState.selectedAttack!!,
                    modifier = Modifier.fillMaxSize()
                )
                GameStatus.ATTACK_RESULT -> AttackResultScreen(
                    screenConstraints = screenContentConstraints,
                    viewModel = viewModel,
                    playerInfo = gameState.playerInfo,
                    attackActionResult = gameState.attackResult!!,
                    attackSelectionState = gameState.selectedAttack!!,
                    modifier = Modifier.fillMaxSize()
                )
                GameStatus.SUPPORT_CONFIRM -> SupportConfirmScreen(
                    screenConstraints = screenContentConstraints,
                    viewModel = viewModel,
                    supportSelectionState = gameState.selectedSupport!!,
                    playerInfo = gameState.playerInfo,
                    modifier = Modifier.fillMaxSize()
                )
                GameStatus.SUPPORT_EXECUTION -> SupportExecutionScreen(
                    screenConstraints = screenContentConstraints,
                    viewModel = viewModel,
                    supportSelectionState = gameState.selectedSupport!!,
                    playerInfo = gameState.playerInfo,
                    modifier = Modifier.fillMaxSize()
                )
                GameStatus.SUPPORT_RESULT -> SupportResultScreen(
                    screenConstraints = screenContentConstraints,
                    viewModel = viewModel,
                    supportSelectionState = gameState.selectedSupport!!,
                    supportResult = gameState.supportResult!!,
                    playerInfo = gameState.playerInfo,
                    modifier = Modifier.fillMaxSize()
                )
                GameStatus.GAME_OVER -> GameOverScreen(
                    screenConstraints = screenContentConstraints,
                    viewModel = viewModel,
                    gameOverInfo = gameState.gameOverInfo!!,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Column(modifier = Modifier
            .weight(mainScreenDisplayOptions.characterPanelWeight)
            .background(colorResource(id = R.color.title_background_color))
            .fillMaxHeight()) {
            for (player in gameState.playerInfo.players) {
                val isActivePlayer = player.player.id == gameState.playerInfo.activePlayer.player.id
                CharacterStatusPanel(
                    playerState = player,
                    curTime = gameState.playerInfo.curTime,
                    viewModel = viewModel,
                    isActive = isActivePlayer && showActiveData,
                    characterPanelDisplayOptions = characterPanelDisplayOptions,
                    showTimeInfo = showActiveData,
                    modifier = Modifier.padding(2.dp))
            }
        }
    }
}

@Composable
fun ActivePlayerSidePanel(playerState: PlayerState, remainingTime: Int, showActivePlayer: Boolean,
                          characterPanelDisplayOptions: ActivePlayerPanelDisplayOptions, viewModel: UltimateCatBattleViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(horizontalArrangement = Arrangement.Absolute.SpaceBetween, modifier = Modifier
            .padding(
                start = 2.dp,
                end = 2.dp,
                top = 2.dp,
                bottom = characterPanelDisplayOptions.menuBottomPadding
            )
            .border(1.dp, Color.Black)
            .background(Color.White)
            .fillMaxWidth()) {
            Box {
                Image(painter = painterResource(id = R.drawable.home), contentDescription = stringResource(
                    id = R.string.go_home
                ),
                    modifier = Modifier
                        .padding(
                            start = 4.dp,
                            end = 4.dp,
                            top = 4.dp,
                            bottom = 4.dp
                        )
                        .size(characterPanelDisplayOptions.menuIconSize)
                        .clickable { viewModel.toggleReturnHomeScreen(true) }
                        .border(1.dp, Color.Black))
            }
            HelpIcon(
                onClick = { viewModel.toggleContextHelp(true) },
                size = characterPanelDisplayOptions.menuIconSize,
                modifier = Modifier.padding(
                    start = 4.dp,
                    end = 4.dp,
                    top = 4.dp,
                    bottom = 4.dp
                )
            )
        }
        if (showActivePlayer) {
            Row(
                modifier = Modifier
                    .padding(start = 2.dp, end = 2.dp, bottom = 2.dp)
                    .border(1.dp, Color.Black)
                    .background(Color(0xFFFFFF80))
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = playerState.player.id.aliveIcon()),
                        contentDescription = stringResource(id = playerState.player.id.name()),
                        modifier = Modifier
                            .size(characterPanelDisplayOptions.largeIconSize)

                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Image(
                        painter = painterResource(id = playerState.player.playerType.icon()),
                        contentDescription = stringResource(id = playerState.player.playerType.name()),
                        modifier = Modifier
                            .size(characterPanelDisplayOptions.iconSize)
                    )
                    Image(
                        painter = painterResource(id = playerState.team.id.icon()),
                        contentDescription = stringResource(id = playerState.team.id.name()),
                        modifier = Modifier
                            .size(characterPanelDisplayOptions.iconSize)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(start = 2.dp, end = 2.dp)
                    .border(1.dp, Color.Black)
                    .background(Color.White)
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hitpoints2),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(2.dp)
                                .size(characterPanelDisplayOptions.statisticsIconSize)
                        )
                        Text(
                            text = playerState.player.hitPoints.toString(),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = characterPanelDisplayOptions.statisticTextSize,
                            color = playerState.player.hitPointsColor()
                        )
                        Image(
                            painter = painterResource(id = R.drawable.clock),
                            contentDescription = "",
                            modifier = Modifier
                                .size(characterPanelDisplayOptions.statisticsIconSize)
                                .padding(2.dp)
                        )
                        Text(
                            text = remainingTime.toString(),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = characterPanelDisplayOptions.statisticTextSize,
                            color = Color.Black
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.attack),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(2.dp)
                                .size(characterPanelDisplayOptions.statisticsIconSize)
                        )
                        Text(
                            text = playerState.player.attack.toString(),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = characterPanelDisplayOptions.statisticTextSize,
                            color = playerState.player.attack.playerStatColor()
                        )
                        Image(
                            painter = painterResource(id = R.drawable.defense),
                            contentDescription = "",
                            modifier = Modifier
                                .size(characterPanelDisplayOptions.statisticsIconSize)
                                .padding(2.dp)
                        )
                        Text(
                            text = playerState.player.defense.toString(),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = characterPanelDisplayOptions.statisticTextSize,
                            color = playerState.player.defense.playerStatColor()
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hit), contentDescription = "",
                            modifier = Modifier
                                .padding(2.dp)
                                .size(characterPanelDisplayOptions.statisticsIconSize)
                        )
                        Text(
                            text = playerState.player.hit.toString(),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = characterPanelDisplayOptions.statisticTextSize,
                            color = playerState.player.hit.playerStatColor()
                        )
                        Image(
                            painter = painterResource(id = R.drawable.avoid2),
                            contentDescription = "",
                            modifier = Modifier
                                .size(characterPanelDisplayOptions.statisticsIconSize)
                                .padding(2.dp)
                        )
                        Text(
                            text = playerState.player.avoid.toString(),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = characterPanelDisplayOptions.statisticTextSize,
                            color = playerState.player.avoid.playerStatColor()
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.critical),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(2.dp)
                                .size(characterPanelDisplayOptions.statisticsIconSize)
                        )
                        Text(
                            text = playerState.player.critical.toString(),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = characterPanelDisplayOptions.statisticTextSize,
                            color = playerState.player.critical.playerStatColor()
                        )
                    }
                }
            }
        } else {
            Row (modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.damage), contentDescription = null,
                    modifier = Modifier.size(characterPanelDisplayOptions.largeIconSize))
            }
        }

    }
}

@Composable
fun CharacterStatusPanel(playerState: PlayerState, curTime: Int, isActive: Boolean, viewModel: UltimateCatBattleViewModel,
                         characterPanelDisplayOptions: CharacterPanelDisplayOptions,
    showTimeInfo: Boolean, modifier: Modifier = Modifier) {
    val avatarBackgroundColor = if (isActive) colorResource(id = R.color.active_avatar_color) else colorResource(
        id = R.color.inactive_avatar_color
    )
    val statBackgroundColor = if (isActive) Color(0xFFFFFFF8) else Color.White
    val borderColor = if (isActive) colorResource(id = R.color.selected_icon_background) else Color.Black
    val borderSize = if (isActive) 2.dp else 1.dp
    Column (modifier = modifier
        .border(borderSize, borderColor)
        .background(statBackgroundColor), verticalArrangement = Arrangement.SpaceEvenly) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(playerState.team.id.color())
            .padding(top = 2.dp, bottom = 2.dp), horizontalArrangement = Arrangement.Start) {
            val playerIcon = if (playerState.player.isAlive) playerState.player.id.aliveIcon() else playerState.player.id.deadIcon()
            Image(painter = painterResource(id = playerIcon), contentDescription = stringResource(id = playerState.player.id.name()),
                modifier = Modifier
                    .padding(start = 2.dp, end = 2.dp)
                    .size(characterPanelDisplayOptions.iconSize)
                    .background(avatarBackgroundColor))
            Image(painter = painterResource(id = playerState.player.playerType.icon()), contentDescription = stringResource(id = playerState.player.playerType.name()),
                modifier = Modifier.size(characterPanelDisplayOptions.iconSize))
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.hitpoints2), contentDescription = "",
                modifier = Modifier
                    .padding(2.dp)
                    .size(characterPanelDisplayOptions.statisticsIconSize))
            Text(text = playerState.player.hitPoints.toString(), textAlign = TextAlign.Left, modifier = Modifier.weight(1.5f),
                fontWeight = FontWeight.Bold, fontFamily = FontFamily.SansSerif, fontSize = characterPanelDisplayOptions.statisticTextSize, color = playerState.player.hitPointsColor())
            if (!playerState.player.isAlive || !showTimeInfo) {
                Spacer(modifier = Modifier.weight(1f))
            } else if (!isActive) {
                Image(
                    painter = painterResource(id = R.drawable.hourglass),
                    contentDescription = "",
                    modifier = Modifier
                        .size(characterPanelDisplayOptions.statisticsIconSize)
                        .padding(2.dp)
                )
                Text(
                    text = playerState.remainingTime(curTime).toString(),
                    textAlign = TextAlign.Left,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = characterPanelDisplayOptions.statisticTextSize,
                    color = Color.Black
                )
            } else {
                Image(painter = painterResource(id = R.drawable.confirm), contentDescription = "",
                    modifier = Modifier
                        .size(characterPanelDisplayOptions.statisticsIconSize)
                        .padding(2.dp))
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=580dp,height=300dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun MainGameScreenPreviewVerySmall() {
    GenericMainGameScreenPreview(screenConstraints = ScreenConstraints(580.dp, 300.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=740dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun MainGameScreenPreviewSmallUnderLimit() {
    GenericMainGameScreenPreview(screenConstraints = ScreenConstraints(740.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=750dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun MainGameScreenPreviewMediumWidthOverLimit() {
    GenericMainGameScreenPreview(screenConstraints = ScreenConstraints(750.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun MainGameScreenPreviewMediumWidthHigherPadding() {
    GenericMainGameScreenPreview(screenConstraints = ScreenConstraints(800.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=900dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun MainGameScreenPreviewLargeWidth() {
    GenericMainGameScreenPreview(screenConstraints = ScreenConstraints(900.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=700dp,height=500dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun MainGameScreenPreviewMediumWidthMediumHeight() {
    GenericMainGameScreenPreview(screenConstraints = ScreenConstraints(700.dp, 500.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=500dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun MainGameScreenPreviewMediumWidthMediumHeightMaxPadding() {
    GenericMainGameScreenPreview(screenConstraints = ScreenConstraints(800.dp, 500.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=630dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun MainGameScreenPreviewMediumWidthLargeHeight() {
    GenericMainGameScreenPreview(screenConstraints = ScreenConstraints(800.dp, 630.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=900dp,height=700dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun MainGameScreenPreviewLargeSize() {
    GenericMainGameScreenPreview(screenConstraints = ScreenConstraints(900.dp, 700.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=1280dp,height=720dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun MainGameScreenPreviewVeryLargeSize() {
    GenericMainGameScreenPreview(screenConstraints = ScreenConstraints(1280.dp, 720.dp))
}

@Composable
fun GenericMainGameScreenPreview(screenConstraints: ScreenConstraints) {
    val playerInfo = createPlayerInfoForPreview()
    val gameState = GameUiState(playerInfo = playerInfo, GameStatus.DEBUG)
    MainGameScreen(screenConstraints = screenConstraints, viewModel = UltimateCatBattleViewModel(), gameState = gameState,
        modifier = Modifier
            .background(Color.White)
            .requiredSize(screenConstraints.maxWidth, screenConstraints.maxHeight))
}

fun createAllAlivePlayerInfoForPreview() : GamePlayerInfo {
    val cat = createNewPlayer(PlayerId.CAT, PlayerType.PLAYER1, 3, 3, 0)
    cat.addEffect(StatusModifier(-50, 40, -20, 10, 0, 100))
    val firstTeam = Team(listOf(cat), TeamId.BALL_GREEN)
    val penguin = createNewPlayer(PlayerId.PENGUIN, PlayerType.PLAYER2, teamRatio = 1, opponentCount = 1, startTime = 105)
    val rabbit = createNewPlayer(PlayerId.RABBIT, PlayerType.PLAYER3, teamRatio = 1, opponentCount = 1, startTime = 17)
    val mouse = createNewPlayer(PlayerId.MOUSE, PlayerType.PLAYER4, teamRatio = 1, opponentCount = 1, startTime = 10)
    val secondTeam = Team(listOf(penguin, rabbit, mouse), TeamId.FISH_BLUE)
    penguin.applyDamage(penguin.maxHitPoints - 1)
    val catInfo = PlayerState(cat, firstTeam)
    val penguinInfo = PlayerState(penguin, secondTeam)
    val rabbitInfo = PlayerState(rabbit, secondTeam)
    val mouseInfo = PlayerState(mouse, secondTeam)
    return GamePlayerInfo(catInfo, listOf(catInfo, penguinInfo, rabbitInfo, mouseInfo), 0)
}

fun createTeamPlayerInfoForPreview() : GamePlayerInfo {
    val cat = createNewPlayer(PlayerId.CAT, PlayerType.PLAYER1, 3, 3, 0)
    val firstTeam = Team(listOf(cat), TeamId.BALL_GREEN)
    val penguin = createNewPlayer(PlayerId.PENGUIN, PlayerType.PLAYER2, teamRatio = 1, opponentCount = 1, startTime = 105)
    val rabbit = createNewPlayer(PlayerId.RABBIT, PlayerType.PLAYER3, teamRatio = 1, opponentCount = 1, startTime = 17)
    val mouse = createNewPlayer(PlayerId.MOUSE, PlayerType.PLAYER4, teamRatio = 1, opponentCount = 1, startTime = 10)
    val secondTeam = Team(listOf(penguin, rabbit, mouse), TeamId.FISH_BLUE)
    val catInfo = PlayerState(cat, firstTeam)
    val penguinInfo = PlayerState(penguin, secondTeam)
    val rabbitInfo = PlayerState(rabbit, secondTeam)
    val mouseInfo = PlayerState(mouse, secondTeam)
    return GamePlayerInfo(penguinInfo, listOf(catInfo, penguinInfo, rabbitInfo, mouseInfo), 0)
}

fun createPlayerInfoForPreview() : GamePlayerInfo {
    val cat = createNewPlayer(PlayerId.CAT, PlayerType.PLAYER1, 3, 3, 0)
    cat.addEffect(StatusModifier(-50, 40, -20, 10, 0, 100))
    val firstTeam = Team(listOf(cat), TeamId.BALL_GREEN)
    val penguin = createNewPlayer(PlayerId.PENGUIN, PlayerType.PLAYER2, teamRatio = 1, opponentCount = 1, startTime = 105)
    val rabbit = createNewPlayer(PlayerId.RABBIT, PlayerType.PLAYER3, teamRatio = 1, opponentCount = 1, startTime = 17)
    val mouse = createNewPlayer(PlayerId.MOUSE, PlayerType.PLAYER4, teamRatio = 1, opponentCount = 1, startTime = 10)
    mouse.applyDamage(1200)
    val secondTeam = Team(listOf(penguin, rabbit, mouse), TeamId.FISH_BLUE)
    val catInfo = PlayerState(cat, firstTeam)
    val penguinInfo = PlayerState(penguin, secondTeam)
    val rabbitInfo = PlayerState(rabbit, secondTeam)
    val mouseInfo = PlayerState(mouse, secondTeam)
    return GamePlayerInfo(catInfo, listOf(catInfo, penguinInfo, rabbitInfo, mouseInfo), 0)
}
