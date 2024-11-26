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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.PlayerId
import org.deathdric.ultimatecatbattle.model.PlayerType
import org.deathdric.ultimatecatbattle.model.Team
import org.deathdric.ultimatecatbattle.model.TeamId
import org.deathdric.ultimatecatbattle.model.createNewPlayer
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.fullImage
import org.deathdric.ultimatecatbattle.ui.name
import org.deathdric.ultimatecatbattle.ui.toolkit.SimpleButton
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText
import org.deathdric.ultimatecatbattle.ui.toolkit.TitleText
import org.deathdric.ultimatecatbattle.vm.GameOverInfo
import org.deathdric.ultimatecatbattle.vm.PlayerState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

data class GameOverScreenDisplayOptions (
    val sharedOptions: SharedGameScreenDisplayOptions,
    val playerImageSize: Dp,
    val quoteTextSize: TextUnit
)

fun ScreenConstraints.toGameOverScreenDisplayOptions() : GameOverScreenDisplayOptions {
    val playerImageSize = ((this.maxWidth - 20.dp)/6).coerceAtMost((this.maxHeight - 20.dp)/6)
        .coerceAtMost(100.dp)
    val quoteTextSize = if (this.maxHeight < 320.dp) {
        12.sp
    } else if (maxHeight < 490.dp){
        14.sp
    } else if (maxWidth < 700.dp){
        16.sp
    } else {
        18.sp
    }

    return GameOverScreenDisplayOptions(sharedOptions = this.toSharedGameScreenDisplayOptions(),
        playerImageSize = playerImageSize, quoteTextSize = quoteTextSize)
}

@Composable
fun GameOverScreen(screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
                   gameOverInfo: GameOverInfo, modifier: Modifier = Modifier
) {
    val displayOptions = screenConstraints.toGameOverScreenDisplayOptions()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TitleText(
            text = "GAME OVER", modifier = Modifier
                .fillMaxWidth()
                .padding(displayOptions.sharedOptions.titlePadding),
            sizeMode = displayOptions.sharedOptions.titleSizeMode
        )
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
                for ((index, player) in gameOverInfo.aliveWinners.withIndex())
                {
                    val topOffset = if (index % 2 == 0) {
                        (displayOptions.playerImageSize * 2) / 3
                    } else {
                        0.dp
                    }
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
                            .scale(1f, 1f)
                    )
                }

                Image(painter = painterResource(id = R.drawable.trophy), contentDescription = null,
                    modifier = Modifier.padding(top = displayOptions.playerImageSize / 2).size(displayOptions.playerImageSize * 2))
                for ((index, player) in gameOverInfo.defeatedWinners.withIndex())
                {
                    val topOffset = if (index % 2 == 0) {
                        (displayOptions.playerImageSize * 2) / 3
                    } else {
                        0.dp
                    }
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
                            .scale(-1f, 1f)
                    )
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
            val firstPlayer = gameOverInfo.aliveWinners[0]
            AnimatedVisibility(visibleState = visibilityState, enter = fadeIn(tween(1000)), exit = fadeOut()) {
                StandardText(text = stringResource(id = gameOverInfo.gameOverQuote).format(stringResource(
                    id = firstPlayer.player.id.name())), fontSize = displayOptions.quoteTextSize,
                    fontStyle = FontStyle.Italic, textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
        SimpleButton(onClick = { viewModel.resetToMainScreen() }, sizeMode = displayOptions.sharedOptions.buttonSizeMode, text = stringResource(id = R.string.back_to_title_screen),
            modifier = Modifier.padding(bottom = displayOptions.sharedOptions.bottomPadding))
    }
}

@Composable
fun GenericGameOverScreenPreview(screenConstraints: ScreenConstraints) {
    val cat = createNewPlayer(PlayerId.CAT, PlayerType.PLAYER1, 3, 3, 0)
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
    val gameOverInfo = GameOverInfo(listOf(catInfo, penguinInfo, rabbitInfo), listOf(mouseInfo), R.string.game_over_quote_1)
    GameOverScreen(screenConstraints = screenConstraints, viewModel = UltimateCatBattleViewModel(), gameOverInfo = gameOverInfo,
        modifier = Modifier.background(Color.White).fillMaxSize())
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=330dp,height=295dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericGameOverScreenSmallSize() {
    GenericGameOverScreenPreview(screenConstraints = ScreenConstraints(330.dp, 295.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericGameOverScreenMediumSize() {
    GenericGameOverScreenPreview(screenConstraints = ScreenConstraints(450.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericGameOverScreenHigherWidth() {
    GenericGameOverScreenPreview(screenConstraints = ScreenConstraints(530.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=400dp,height=495dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericGameOverScreenSmallPortrait() {
    GenericGameOverScreenPreview(screenConstraints = ScreenConstraints(400.dp, 495.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=625dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericGameOverScreenMediumPortrait() {
    GenericGameOverScreenPreview(screenConstraints = ScreenConstraints(450.dp, 625.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=695dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericGameOverScreenLargePortrait() {
    GenericGameOverScreenPreview(screenConstraints = ScreenConstraints(530.dp, 695.dp))
}


@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=780dp,height=720dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericGameOverScreenVeryLargeSize() {
    GenericGameOverScreenPreview(screenConstraints = ScreenConstraints(780.dp, 720.dp))
}