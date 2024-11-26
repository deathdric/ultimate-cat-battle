package org.deathdric.ultimatecatbattle.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.fullImage
import org.deathdric.ultimatecatbattle.ui.name
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText
import org.deathdric.ultimatecatbattle.ui.toolkit.SimpleButton
import org.deathdric.ultimatecatbattle.ui.toolkit.TitleText
import org.deathdric.ultimatecatbattle.vm.GameStatus
import org.deathdric.ultimatecatbattle.vm.GameUiState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

data class GameIntroScreenDisplayOptions(
    val sharedOptions: SharedGameScreenDisplayOptions,
    val playerImageSize: Dp,
    val textSize: TextUnit,
    val lineHeight: TextUnit,
    val iconSize: Dp
)

fun ScreenConstraints.toGameIntroScreenDisplayOptions() : GameIntroScreenDisplayOptions {

    val playerImageSize = ((this.maxWidth - 20.dp)/4).coerceAtMost((this.maxHeight - 20.dp)/6)
        .coerceAtMost(100.dp)

    val textSize = if (this.maxHeight < 320.dp) {
        12.sp
    } else if (this.maxHeight < 460.dp) {
        14.sp
    } else if (this.maxHeight < 600.dp) {
        16.sp
    } else if (this.maxWidth < 650.dp) {
        18.sp
    } else {
        20.sp
    }

    val iconSize = if (this.maxHeight < 320.dp) {
        17.dp
    } else if (this.maxHeight < 460.dp) {
        19.dp
    } else if (this.maxHeight < 600.dp) {
        22.dp
    } else if (this.maxWidth < 650.dp) {
        25.dp
    } else {
        28.dp
    }


    return GameIntroScreenDisplayOptions(this.toSharedGameScreenDisplayOptions(), playerImageSize,
        textSize = textSize, iconSize = iconSize, lineHeight = (textSize * 5)/4)
}

@Composable
fun GameIntroScreen(screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
                    gameState: GameUiState, modifier: Modifier = Modifier
) {
    val displayOptions = screenConstraints.toGameIntroScreenDisplayOptions()
    Column (modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        TitleText(text = stringResource(id = R.string.new_game), modifier = Modifier
            .fillMaxWidth()
            .padding(displayOptions.sharedOptions.titlePadding),
            sizeMode = displayOptions.sharedOptions.titleSizeMode)
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF00C000))) {
            Box (modifier = Modifier
                .height(displayOptions.playerImageSize)
                .fillMaxWidth()
                .background(Color(0xFFC0C0FF))) {

            }
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top){
                var index = 0;
                for (player in gameState.playerInfo.players) {
                    val revert = index >= (gameState.playerInfo.players.size + 1) / 2
                    val topOffset = if (index == 0 || index == gameState.playerInfo.players.size -1) {
                        (displayOptions.playerImageSize * 2) / 3
                    } else {
                        0.dp
                    }
                    val scaleX = if (revert) -1f else 1f
                    Image(
                        painter = painterResource(id = player.player.id.fullImage()),
                        contentDescription = stringResource(
                            id = player.player.id.name()
                        ),
                        modifier = Modifier
                            .padding(top = topOffset)
                            .size(
                                width = displayOptions.playerImageSize,
                                height = displayOptions.playerImageSize * 2
                            )
                            .scale(scaleX, 1f)
                    )
                    index++;
                }
            }

        }
        Row (modifier = Modifier
            .padding(top = 8.dp)
            .weight(1f)){
            Column (modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
                Row {
                    StandardText(text = stringResource(id = R.string.objective_description), fontWeight = FontWeight.Bold, fontSize = displayOptions.textSize, modifier =
                    Modifier.padding(start = 8.dp, end = 4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.hitpoints2),
                        contentDescription = null,
                        modifier = Modifier.size(displayOptions.iconSize)
                    )
                }
                StandardText(text = stringResource(id = R.string.objective_description2), fontWeight = FontWeight.Bold, fontSize = displayOptions.textSize, modifier =
                Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp), lineHeight = displayOptions.lineHeight)
            }
        }
        SimpleButton(onClick = { viewModel.displayNewTurn() }, sizeMode = displayOptions.sharedOptions.buttonSizeMode, text = stringResource(id = R.string.start_game),
            modifier = Modifier.padding(bottom = displayOptions.sharedOptions.bottomPadding))
    }
}

@Composable
fun GenericGameIntroScreenPreview(screenConstraints: ScreenConstraints) {
    val playerInfo = createPlayerInfoForPreview()
    val gameState = GameUiState(playerInfo = playerInfo, GameStatus.INTRO)
    GameIntroScreen(screenConstraints = screenConstraints, viewModel = UltimateCatBattleViewModel(), gameState = gameState,
        modifier = Modifier
            .background(Color.White)
            .requiredSize(screenConstraints.maxWidth, screenConstraints.maxHeight))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=330dp,height=295dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericGameIntroScreenSmallSize() {
    GenericGameIntroScreenPreview(screenConstraints = ScreenConstraints(330.dp, 295.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericGameIntroScreenMediumSize() {
    GenericGameIntroScreenPreview(screenConstraints = ScreenConstraints(450.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericGameIntroScreenHigherWidth() {
    GenericGameIntroScreenPreview(screenConstraints = ScreenConstraints(530.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=400dp,height=495dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericGameIntroScreenSmallPortrait() {
    GenericGameIntroScreenPreview(screenConstraints = ScreenConstraints(400.dp, 495.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=625dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericGameIntroScreenMediumPortrait() {
    GenericGameIntroScreenPreview(screenConstraints = ScreenConstraints(450.dp, 625.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=695dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericGameIntroScreenLargePortrait() {
    GenericGameIntroScreenPreview(screenConstraints = ScreenConstraints(530.dp, 695.dp))
}


@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=780dp,height=720dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericGameIntroScreenVeryLargeSize() {
    GenericGameIntroScreenPreview(screenConstraints = ScreenConstraints(780.dp, 720.dp))
}