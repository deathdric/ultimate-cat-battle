package org.deathdric.ultimatecatbattle.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
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
import org.deathdric.ultimatecatbattle.model.AttackActionId
import org.deathdric.ultimatecatbattle.model.TargetType
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.fullImage
import org.deathdric.ultimatecatbattle.ui.icon
import org.deathdric.ultimatecatbattle.ui.name
import org.deathdric.ultimatecatbattle.ui.toolkit.SimpleButton
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText
import org.deathdric.ultimatecatbattle.ui.toolkit.TitleText
import org.deathdric.ultimatecatbattle.vm.AttackSelectionState
import org.deathdric.ultimatecatbattle.vm.GamePlayerInfo
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

data class ActionExecutionScreenDisplayOptions (
    val sharedOptions: SharedGameScreenDisplayOptions,
    val playerImageSize: Dp,
    val quoteTextSize: TextUnit
)

fun ScreenConstraints.toActionExecutionScreenDisplayOptions() : ActionExecutionScreenDisplayOptions {
    val playerImageSize = ((this.maxWidth - 20.dp)/6).coerceAtMost((this.maxHeight - 20.dp)/8)
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

    return ActionExecutionScreenDisplayOptions(this.toSharedGameScreenDisplayOptions(), playerImageSize,
        quoteTextSize)
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun AttackExecutionScreen(screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
                          playerInfo: GamePlayerInfo,
                        attackSelectionState: AttackSelectionState, modifier: Modifier = Modifier
) {
    val displayOptions = screenConstraints.toActionExecutionScreenDisplayOptions()
    val targets = if (attackSelectionState.preview.attackAction.targetType == TargetType.ALL_ENEMIES) {
        attackSelectionState.preview.targetedPreviews.map { it.target.id }
    } else {
        listOf(attackSelectionState.selectedTarget)
    }
    val currentPlayer = playerInfo.activePlayer.player
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TitleText(
            text = stringResource(id = R.string.used_skill_message).format(
                stringResource(id = currentPlayer.id.name()),
                stringResource(id = attackSelectionState.preview.attackAction.id.name())
            ), modifier = Modifier
                .fillMaxWidth()
                .padding(displayOptions.sharedOptions.titlePadding),
            sizeMode = displayOptions.sharedOptions.titleSizeMode
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF00C000))
        ) {
            Box(
                modifier = Modifier
                    .height(displayOptions.playerImageSize)
                    .fillMaxWidth()
                    .background(Color(0xFFC0C0FF))
            ) {

            }
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {

                Column {
                        Image(
                            painter = painterResource(id = currentPlayer.id.fullImage()),
                            contentDescription = stringResource(
                                id = currentPlayer.id.name()
                            ),
                            modifier = Modifier
                                .padding(
                                    top = displayOptions.playerImageSize / 2
                                )
                                .size(
                                    width = displayOptions.playerImageSize,
                                    height = displayOptions.playerImageSize * 2
                                )
                                .scale(1f, 1f)
                        )




                }
                Column {
                    val attackVisibilityState = MutableTransitionState(false).apply {
                        targetState = true
                    }
                    AnimatedVisibility(visibleState = attackVisibilityState, enter = fadeIn(tween(1000, 0)), exit = fadeOut()) {
                        Image(painter = painterResource(id = attackSelectionState.preview.attackAction.id.icon()),
                            contentDescription = stringResource(id = attackSelectionState.preview.attackAction.id.name()),
                            modifier = Modifier
                                .padding(top = (displayOptions.playerImageSize * 3) / 4)
                                .size(displayOptions.playerImageSize))
                    }
                }
                Column {
                    Spacer(modifier = Modifier.height((displayOptions.playerImageSize * 7)/2))
                }
                var index = 0
                Column {
                    Row {
                        for (target in targets) {
                            val topOffset = displayOptions.playerImageSize * (index + 1) / (targets.size + 1)
                            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(id = target.fullImage()),
                                    contentDescription = stringResource(
                                        id = target.name()
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
                            index++;
                        }
                    }
                }
            }

        }
        Row (modifier = Modifier
            .weight(1f)
            .padding(4.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(10))
            .padding(16.dp)
            .verticalScroll(ScrollState(0)), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
            val visibilityState = MutableTransitionState(false).apply {
                targetState = true
            }
            val fullQuote = "« %s »".format(stringResource(id = attackSelectionState.actionExecutionQuote))
            AnimatedVisibility(visibleState = visibilityState, enter = fadeIn(tween(1000)), exit = fadeOut()) {
                StandardText(text = fullQuote, fontSize = displayOptions.quoteTextSize,
                    fontStyle = FontStyle.Italic, textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        SimpleButton(onClick = { viewModel.executeAttack(attackActionId = attackSelectionState.preview.attackAction.id,
            target = attackSelectionState.selectedTarget) }, sizeMode = displayOptions.sharedOptions.buttonSizeMode, text = stringResource(id = R.string.next),
            modifier = Modifier.padding(bottom = displayOptions.sharedOptions.bottomPadding))
    }
}

@Composable
fun GenericAttackExecutionScreenPreview(screenConstraints: ScreenConstraints) {
    val playerInfo = createAllAlivePlayerInfoForPreview()
    val opponents = playerInfo.players.filter { it.player.id != playerInfo.activePlayer.player.id }.map { it.player }.toList()
    val attackAction = playerInfo.activePlayer.player.attackActions.filter { it.id == AttackActionId.SWEEPING_KICK }[0]
    val attackPreview = attackAction
        .simulate(playerInfo.activePlayer.player, playerInfo.players.filter { it.player.id != playerInfo.activePlayer.player.id }.map { it.player }.toList())
    val attackSelectionState = AttackSelectionState(attackPreview, opponents[0].id)
    AttackExecutionScreen(screenConstraints = screenConstraints, viewModel = UltimateCatBattleViewModel(),
        playerInfo = playerInfo,
        attackSelectionState = attackSelectionState,
        modifier = Modifier
            .background(Color.White)
            .requiredSize(screenConstraints.maxWidth, screenConstraints.maxHeight))

}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=330dp,height=295dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericAttackExecutionScreenSmallSize() {
    GenericAttackExecutionScreenPreview(screenConstraints = ScreenConstraints(330.dp, 295.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericAttackExecutionScreenMediumSize() {
    GenericAttackExecutionScreenPreview(screenConstraints = ScreenConstraints(450.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericAttackExecutionScreenHigherWidth() {
    GenericAttackExecutionScreenPreview(screenConstraints = ScreenConstraints(530.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=400dp,height=495dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericAttackExecutionScreenSmallPortrait() {
    GenericAttackExecutionScreenPreview(screenConstraints = ScreenConstraints(400.dp, 495.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=625dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericAttackExecutionScreenMediumPortrait() {
    GenericAttackExecutionScreenPreview(screenConstraints = ScreenConstraints(450.dp, 625.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=695dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericAttackExecutionScreenLargePortrait() {
    GenericAttackExecutionScreenPreview(screenConstraints = ScreenConstraints(530.dp, 695.dp))
}


@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=780dp,height=720dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericAttackExecutionScreenVeryLargeSize() {
    GenericAttackExecutionScreenPreview(screenConstraints = ScreenConstraints(780.dp, 720.dp))
}