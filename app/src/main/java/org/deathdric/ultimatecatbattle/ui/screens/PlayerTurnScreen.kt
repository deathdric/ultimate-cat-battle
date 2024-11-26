package org.deathdric.ultimatecatbattle.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.fullImage
import org.deathdric.ultimatecatbattle.ui.icon
import org.deathdric.ultimatecatbattle.ui.name
import org.deathdric.ultimatecatbattle.ui.toolkit.SimpleButton
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText
import org.deathdric.ultimatecatbattle.ui.toolkit.TitleText
import org.deathdric.ultimatecatbattle.vm.GameStatus
import org.deathdric.ultimatecatbattle.vm.GameUiState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

data class PlayerTurnScreenDisplayOptions (
    val sharedOptions: SharedGameScreenDisplayOptions,
    val playerImageSize: Dp,
    val iconSize: Dp,
    val infoTextSize: TextUnit,
    val quoteTextSize: TextUnit
)

fun ScreenConstraints.toPlayerTurnScreenDisplayOptions() : PlayerTurnScreenDisplayOptions {
    val playerImageSize = ((this.maxWidth - 30.dp)/3).coerceAtMost((this.maxHeight - 20.dp)/5)
        .coerceAtMost(200.dp)
    val iconSize = if (this.maxHeight < 320.dp || this.maxWidth < 450.dp) {
        20.dp
    } else if (maxHeight < 490.dp || this.maxWidth < 450.dp){
        23.dp
    } else if (maxWidth < 700.dp){
        26.dp
    } else {
        29.dp
    }
    val textSize = if (this.maxHeight < 320.dp || this.maxWidth < 450.dp) {
        14.sp
    } else if (maxHeight < 490.dp || this.maxWidth < 450.dp){
        16.sp
    } else if (maxWidth < 700.dp){
        18.sp
    } else {
        20.sp
    }

    val quoteTextSize = if (this.maxHeight < 320.dp) {
        12.sp
    } else if (maxHeight < 490.dp){
        14.sp
    } else if (maxWidth < 700.dp){
        16.sp
    } else {
        18.sp
    }


    return PlayerTurnScreenDisplayOptions(this.toSharedGameScreenDisplayOptions(), playerImageSize,
        iconSize, textSize, quoteTextSize)
}

@Composable
fun PlayerTurnScreen(screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
                     gameState: GameUiState, modifier: Modifier = Modifier
) {
    val displayOptions = screenConstraints.toPlayerTurnScreenDisplayOptions()
    Column (modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        TitleText(
            text = stringResource(id = R.string.player_turn).format(stringResource(id = gameState.playerInfo.activePlayer.player.id.name())), modifier = Modifier
                .fillMaxWidth()
                .padding(displayOptions.sharedOptions.titlePadding),
            sizeMode = displayOptions.sharedOptions.titleSizeMode
        )
        Row (modifier = Modifier
            .padding(4.dp)
            .border(1.dp, Color.Black)
            .background(colorResource(id = R.color.panel_second_color))
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = gameState.playerInfo.activePlayer.player.id.fullImage()),
                contentDescription = stringResource(id = gameState.playerInfo.activePlayer.player.id.name()),
                modifier = Modifier.size(width = displayOptions.playerImageSize, height = displayOptions.playerImageSize*2))
            Column (modifier = Modifier
                .border(1.dp, Color.Black)
                .background(Color.White)
                .padding(4.dp),
                horizontalAlignment = Alignment.Start) {
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = gameState.playerInfo.activePlayer.player.playerType.icon()),
                        contentDescription = stringResource(id = gameState.playerInfo.activePlayer.player.playerType.name()),
                        modifier = Modifier
                            .padding(4.dp)
                            .size(displayOptions.iconSize)
                        )
                    StandardText(text = stringResource(id = gameState.playerInfo.activePlayer.player.playerType.name()),
                        fontSize = displayOptions.infoTextSize, fontWeight = FontWeight.Bold)
                }
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = gameState.playerInfo.activePlayer.team.id.icon()),
                        contentDescription = stringResource(id = gameState.playerInfo.activePlayer.team.id.name()),
                        modifier = Modifier
                            .padding(4.dp)
                            .size(displayOptions.iconSize)
                    )
                    val teamText = stringResource(id = R.string.team_name_pattern).format(stringResource(id = gameState.playerInfo.activePlayer.team.id.name()))
                    StandardText(text = teamText,
                        fontSize = displayOptions.infoTextSize, fontWeight = FontWeight.Bold)
                }
                Row (verticalAlignment = Alignment.CenterVertically) {
                    StandardText(text = "%s : ".format(stringResource(id = R.string.turn_time_units)),
                        fontSize = displayOptions.infoTextSize, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp))
                    Image(painter = painterResource(id = R.drawable.clock),
                        contentDescription = stringResource(id = R.string.turn_time_units),
                        modifier = Modifier
                            .padding(4.dp)
                            .size(displayOptions.iconSize)
                    )
                    StandardText(text = gameState.playerInfo.computeRemainingTime().toString(),
                        fontSize = displayOptions.infoTextSize, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end=4.dp))
                }
            }
        }
        Row (modifier = Modifier
            .weight(1f)
            .padding(4.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(10))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
            val visibilityState = MutableTransitionState(false).apply {
                targetState = true
            }
            AnimatedVisibility(visibleState = visibilityState, enter = fadeIn(tween(1000)), exit = fadeOut()) {
                StandardText(text = stringResource(id = gameState.startTurnQuote).format(stringResource(
                    id = gameState.playerInfo.activePlayer.player.id.name())), fontSize = displayOptions.quoteTextSize,
                    fontStyle = FontStyle.Italic, textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                        )
            }
        }
        SimpleButton(onClick = { viewModel.chooseNextMove() }, sizeMode = displayOptions.sharedOptions.titleSizeMode, text = stringResource(id = R.string.start_turn),
            modifier = Modifier.padding(bottom = displayOptions.sharedOptions.bottomPadding))
    }
}

@Composable
fun GenericPlayerTurnScreenPreview(screenConstraints: ScreenConstraints) {
    val playerInfo = createPlayerInfoForPreview()
    val gameState = GameUiState(playerInfo = playerInfo, GameStatus.PLAYER_TURN)
    PlayerTurnScreen(screenConstraints = screenConstraints, viewModel = UltimateCatBattleViewModel(), gameState = gameState,
        modifier = Modifier
            .background(Color.White)
            .requiredSize(screenConstraints.maxWidth, screenConstraints.maxHeight))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=330dp,height=295dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericPlayerTurnScreenSmallSize() {
    GenericPlayerTurnScreenPreview(screenConstraints = ScreenConstraints(330.dp, 295.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericPlayerTurnScreenMediumSize() {
    GenericPlayerTurnScreenPreview(screenConstraints = ScreenConstraints(450.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericPlayerTurnScreenHigherWidth() {
    GenericPlayerTurnScreenPreview(screenConstraints = ScreenConstraints(530.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=400dp,height=495dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericPlayerTurnScreenSmallPortrait() {
    GenericPlayerTurnScreenPreview(screenConstraints = ScreenConstraints(400.dp, 495.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=625dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericPlayerTurnScreenMediumPortrait() {
    GenericPlayerTurnScreenPreview(screenConstraints = ScreenConstraints(450.dp, 625.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=695dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericPlayerTurnScreenLargePortrait() {
    GenericPlayerTurnScreenPreview(screenConstraints = ScreenConstraints(530.dp, 695.dp))
}


@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=780dp,height=720dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericPlayerTurnScreenVeryLargeSize() {
    GenericPlayerTurnScreenPreview(screenConstraints = ScreenConstraints(780.dp, 720.dp))
}