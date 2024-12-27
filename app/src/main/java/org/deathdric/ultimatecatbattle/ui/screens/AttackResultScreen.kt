package org.deathdric.ultimatecatbattle.ui.screens

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
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
import org.deathdric.ultimatecatbattle.model.AttackActionId
import org.deathdric.ultimatecatbattle.model.AttackActionResult
import org.deathdric.ultimatecatbattle.model.AttackSuccessMode
import org.deathdric.ultimatecatbattle.model.FakeNumberGenerator
import org.deathdric.ultimatecatbattle.model.toStatusEffects
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
import java.util.stream.Collectors.toList
import kotlin.math.absoluteValue

data class ActionResultScreenDisplayOptions(
    val sharedOptions: SharedGameScreenDisplayOptions,
    val playerImageSize: Dp,
    val damageFontSize: TextUnit,
    val resultTextSize: TextUnit
)

fun ScreenConstraints.toActionResultScreenDisplayOptions(): ActionResultScreenDisplayOptions {
    val playerImageSize = ((this.maxWidth - 20.dp) / 6).coerceAtMost((this.maxHeight - 20.dp) / 8)
        .coerceAtMost(100.dp)

    val damageFontSize = if (maxWidth < 350.dp || maxHeight < 320.dp) {
        10.sp
    } else if (maxWidth < 450.dp || maxHeight < 490.dp) {
        11.sp
    } else if (maxWidth < 690.dp) {
        12.sp
    } else {
        14.sp
    }

    val resultTextSize = if (this.maxHeight < 320.dp) {
        12.sp
    } else if (maxHeight < 490.dp){
        14.sp
    } else if (maxWidth < 700.dp){
        16.sp
    } else {
        18.sp
    }

    return ActionResultScreenDisplayOptions(
        this.toSharedGameScreenDisplayOptions(), playerImageSize,
        damageFontSize = damageFontSize,
        resultTextSize = resultTextSize
    )
}

@Composable
fun AttackResultScreen(
    screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
    playerInfo: GamePlayerInfo,
    attackActionResult: AttackActionResult,
    attackSelectionState: AttackSelectionState, modifier: Modifier = Modifier
) {
    val displayOptions = screenConstraints.toActionResultScreenDisplayOptions()
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
                    Image(
                        painter = painterResource(id = attackSelectionState.preview.attackAction.id.icon()),
                        contentDescription = stringResource(id = attackSelectionState.preview.attackAction.id.name()),
                        modifier = Modifier
                            .padding(top = (displayOptions.playerImageSize * 3) / 4)
                            .size(displayOptions.playerImageSize)
                    )
                }
                Column {
                    Spacer(modifier = Modifier.height((displayOptions.playerImageSize * 7) / 2))
                }
                var index = 0
                Column {
                    Row {
                        for (targetResult in attackActionResult.targetedResults) {
                            val damageText =
                                if (targetResult.successMode == AttackSuccessMode.FAILURE) " X " else targetResult.damage.toString()

                            val topOffset =
                                displayOptions.playerImageSize * (index + 1) / (attackActionResult.targetedResults.size + 1)


                            if (!targetResult.target.isAlive) {
                                val playerVisibilityState = MutableTransitionState(true).apply {
                                    targetState = false
                                }
                                AnimatedVisibility(
                                    visibleState = playerVisibilityState, enter = fadeIn(),
                                    exit = fadeOut(tween(1000, 500))
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Image(

                                            painter = painterResource(id = targetResult.target.id.fullImage()),
                                            contentDescription = stringResource(
                                                id = targetResult.target.id.name()
                                            ),
                                            modifier = Modifier
                                                .padding(top = topOffset)
                                                .size(
                                                    width = displayOptions.playerImageSize,
                                                    height = displayOptions.playerImageSize * 2
                                                )
                                                .scale(-1f, 1f)
                                        )
                                        DamageDoneText(
                                            damageText,
                                            targetResult.successMode,
                                            displayOptions
                                        )
                                    }
                                }
                            } else {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(

                                        painter = painterResource(id = targetResult.target.id.fullImage()),
                                        contentDescription = stringResource(
                                            id = targetResult.target.id.name()
                                        ),
                                        modifier = Modifier
                                            .padding(top = topOffset)
                                            .size(
                                                width = displayOptions.playerImageSize,
                                                height = displayOptions.playerImageSize * 2
                                            )
                                            .scale(-1f, 1f)
                                    )
                                    DamageDoneText(damageText, targetResult.successMode, displayOptions)
                                }
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

            AnimatedVisibility(visibleState = visibilityState, enter = fadeIn(tween(1000)), exit = fadeOut()) {
                Column {
                    var resultText = ""
                    for ((index, playerResult) in attackActionResult.targetedResults.withIndex()) {
                        val playerName = stringResource(id = playerResult.target.id.name())
                        if (playerResult.successMode == AttackSuccessMode.FAILURE) {
                            resultText += stringResource(id = R.string.result_attack_missed).format(playerName)
                        } else {
                            if (playerResult.successMode == AttackSuccessMode.SUCCESS) {
                                resultText += stringResource(id = R.string.result_damage_standard).format(playerName, playerResult.damage)
                            } else {
                                resultText += stringResource(id = R.string.result_damage_critical).format(playerName, playerResult.damage)
                            }
                            if (!playerResult.target.isAlive) {
                                resultText += " "
                                resultText += stringResource(id = R.string.result_player_defeated).format(playerName)
                            } else {
                                if (playerResult.delay > 0) {
                                    resultText += " "
                                    resultText += stringResource(id = R.string.result_delay).format(
                                        playerResult.delay
                                    )
                                }
                                if (playerResult.statusEffect != null) {
                                    val appliedEffects = playerResult.statusEffect.toStatusEffects()
                                    for (appliedEffect in appliedEffects) {
                                        resultText += " "
                                        resultText += stringResource(id = R.string.result_stat_down_effect)
                                            .format(
                                                stringResource(id = appliedEffect.effectType.name()),
                                                appliedEffect.effectValue.absoluteValue
                                            )
                                    }

                                }
                            }
                        }
                        if (index < attackActionResult.targetedResults.size - 1) {
                            resultText += "\n"
                        }
                    }
                    StandardText(
                        text = resultText, fontSize = displayOptions.resultTextSize,
                        fontStyle = FontStyle.Normal, textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

            }
        }
        SimpleButton(
            onClick = { viewModel.endAction() },
            sizeMode = displayOptions.sharedOptions.buttonSizeMode,
            text = stringResource(id = R.string.next),
            modifier = Modifier.padding(bottom = displayOptions.sharedOptions.bottomPadding)
        )
    }
}

@Composable
fun DamageDoneText(
    damageText: String, attackSuccessMode: AttackSuccessMode, displayOptions: ActionResultScreenDisplayOptions,
    modifier: Modifier = Modifier
) {
    val textColor = when(attackSuccessMode) {
        AttackSuccessMode.CRITICAL -> Color(0xFFFFFFC0)
        AttackSuccessMode.SUCCESS -> Color.Red
        AttackSuccessMode.FAILURE -> Color.Blue
    }
    val textBackground = when(attackSuccessMode) {
        AttackSuccessMode.CRITICAL -> Color.Red
        AttackSuccessMode.SUCCESS -> Color.White
        AttackSuccessMode.FAILURE -> Color.White
    }

    val fontWeight = if (attackSuccessMode == AttackSuccessMode.CRITICAL) {
        FontWeight.ExtraBold
    } else {
        FontWeight.Bold
    }

    Text(
        text = damageText,
        modifier = modifier
            .padding(2.dp)
            .widthIn(displayOptions.playerImageSize / 2, (displayOptions.playerImageSize * 4) / 5)
            .border(1.dp, Color.Black)
            .background(textBackground)
            .padding(2.dp),
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.SansSerif,
        fontWeight = fontWeight,
        fontSize = displayOptions.damageFontSize,
        lineHeight = (displayOptions.damageFontSize * 6)/4,
        color = textColor
    )
}

@Composable
fun GenericAttackResultScreenPreview(screenConstraints: ScreenConstraints) {
    val playerInfo = createAllAlivePlayerInfoForPreview()
    val opponents = playerInfo.players.filter { it.player.id != playerInfo.activePlayer.player.id }
        .map { it.player }.toList()
    val attackAction =
        playerInfo.activePlayer.player.attackActions.filter { it.id == AttackActionId.SWEEPING_KICK }[0]
    val attackPreview = attackAction
        .simulate(playerInfo.activePlayer.player,
            playerInfo.players.filter { it.player.id != playerInfo.activePlayer.player.id }
                .map { it.player }.toList()
        )
    val attackSelectionState = AttackSelectionState(attackPreview, opponents[0].id)
    val numberGenerator = FakeNumberGenerator(listOf(0, 40, 99, 99, 99, 0, 50, 0, 0))
    val attackActionResult = attackAction.apply(
        0,
        playerInfo.activePlayer.player,
        attackPreview.targetedPreviews.stream().map { it.target }.collect(toList()),
        numberGenerator
    )

    AttackResultScreen(
        screenConstraints = screenConstraints, viewModel = UltimateCatBattleViewModel(),
        playerInfo = playerInfo,
        attackSelectionState = attackSelectionState,
        attackActionResult = attackActionResult,
        modifier = Modifier
            .background(Color.White)
            .requiredSize(screenConstraints.maxWidth, screenConstraints.maxHeight)
    )

}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=330dp,height=295dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericAttackResultScreenSmallSize() {
    GenericAttackResultScreenPreview(screenConstraints = ScreenConstraints(330.dp, 295.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericAttackResultScreenMediumSize() {
    GenericAttackResultScreenPreview(screenConstraints = ScreenConstraints(450.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericAttackResultScreenHigherWidth() {
    GenericAttackResultScreenPreview(screenConstraints = ScreenConstraints(530.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=400dp,height=495dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericAttackResultScreenSmallPortrait() {
    GenericAttackResultScreenPreview(screenConstraints = ScreenConstraints(400.dp, 495.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=625dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericAttackResultScreenMediumPortrait() {
    GenericAttackResultScreenPreview(screenConstraints = ScreenConstraints(450.dp, 625.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=695dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericAttackResultScreenLargePortrait() {
    GenericAttackResultScreenPreview(screenConstraints = ScreenConstraints(530.dp, 695.dp))
}


@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=780dp,height=720dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericAttackResultScreenVeryLargeSize() {
    GenericAttackResultScreenPreview(screenConstraints = ScreenConstraints(780.dp, 720.dp))
}