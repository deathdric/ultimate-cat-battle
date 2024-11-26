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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.SupportAction
import org.deathdric.ultimatecatbattle.model.SupportActionResult
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.fullImage
import org.deathdric.ultimatecatbattle.ui.icon
import org.deathdric.ultimatecatbattle.ui.mainEffectType
import org.deathdric.ultimatecatbattle.ui.name
import org.deathdric.ultimatecatbattle.ui.toolkit.SimpleButton
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText
import org.deathdric.ultimatecatbattle.ui.toolkit.TitleText
import org.deathdric.ultimatecatbattle.vm.GamePlayerInfo
import org.deathdric.ultimatecatbattle.vm.SupportSelectionState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel
import kotlin.math.absoluteValue

data class SupportResultScreenDisplayOptions(
    val sharedOptions: SharedGameScreenDisplayOptions,
    val playerImageSize: Dp,
    val effectIconSize: Dp,
    val resultTextSize: TextUnit
)

fun ScreenConstraints.toSupportResultScreenDisplayOptions(): SupportResultScreenDisplayOptions {
    val playerImageSize = ((this.maxWidth - 20.dp) / 6).coerceAtMost((this.maxHeight - 20.dp) / 8)
        .coerceAtMost(100.dp)

    val effectIconSize = if (maxWidth < 350.dp || maxHeight < 320.dp) {
        15.dp
    } else if (maxWidth < 450.dp || maxHeight < 490.dp) {
        20.dp
    } else if (maxWidth < 690.dp) {
        25.dp
    } else {
        30.dp
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

    return SupportResultScreenDisplayOptions(
        this.toSharedGameScreenDisplayOptions(), playerImageSize,
        effectIconSize = effectIconSize,
        resultTextSize = resultTextSize
    )
}

@Composable
fun SupportResultScreen(screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
                           playerInfo: GamePlayerInfo,
                           supportResult: SupportActionResult,
                           supportSelectionState: SupportSelectionState, modifier: Modifier = Modifier
) {
    val displayOptions = screenConstraints.toSupportResultScreenDisplayOptions()
    val currentPlayer = playerInfo.activePlayer.player
    val otherTargets = supportResult.targets.filter { it.id != currentPlayer.id }

    val isSelfTarget = supportResult.targets.any { it.id == currentPlayer.id }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TitleText(
            text = stringResource(id = R.string.used_skill_message).format(
                stringResource(id = currentPlayer.id.name()),
                stringResource(id = supportSelectionState.supportAction.id.name())
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

                Column (horizontalAlignment = Alignment.CenterHorizontally) {
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
                    if (isSelfTarget) {
                        SupportResultIconPanel(supportSelectionState.supportAction, displayOptions)
                    }




                }
                Column {
                    Image(painter = painterResource(id = supportSelectionState.supportAction.id.icon()),
                        contentDescription = stringResource(id = supportSelectionState.supportAction.id.name()),
                        modifier = Modifier
                            .padding(top = (displayOptions.playerImageSize * 3) / 4)
                            .size(displayOptions.playerImageSize))
                }
                Column {
                    Spacer(modifier = Modifier.height((displayOptions.playerImageSize * 7)/2))
                }
                var index = 0
                Column {
                    Row {
                        for (target in otherTargets) {
                            val topOffset = displayOptions.playerImageSize * (index + 1) / (otherTargets.size + 1)
                            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(id = target.id.fullImage()),
                                    contentDescription = stringResource(
                                        id = target.id.name()
                                    ),
                                    modifier = Modifier
                                        .padding(top = topOffset)
                                        .size(
                                            width = displayOptions.playerImageSize,
                                            height = displayOptions.playerImageSize * 2
                                        )
                                        .scale(-1f, 1f)
                                )
                                SupportResultIconPanel(supportSelectionState.supportAction, displayOptions)
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
            var resultText = ""
            val targetNames = supportResult.targets.map { stringResource(id = it.id.name()) }
            resultText += targetNames.joinToString(",")
            resultText += " :"
            for (appliedEffect in supportResult.statusEffect) {
                resultText += " "
                val directionTemplate = if (appliedEffect.effectValue < 0) R.string.result_stat_down_effect else R.string.result_stat_up_effect
                resultText += stringResource(id = directionTemplate)
                    .format(
                        stringResource(id = appliedEffect.effectType.name()),
                        appliedEffect.effectValue.absoluteValue
                    )
            }

            AnimatedVisibility(visibleState = visibilityState, enter = fadeIn(tween(1000)), exit = fadeOut()) {
                StandardText(
                    text = resultText, fontSize = displayOptions.resultTextSize,
                    fontStyle = FontStyle.Normal, textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        SimpleButton(onClick = { viewModel.endAction() }, sizeMode = displayOptions.sharedOptions.buttonSizeMode, text = stringResource(id = R.string.next),
            modifier = Modifier.padding(bottom = displayOptions.sharedOptions.bottomPadding))
    }
}

@Composable
fun SupportResultIconPanel(supportAction: SupportAction, supportActionResultScreenDisplayOptions: SupportResultScreenDisplayOptions, modifier: Modifier = Modifier) {
    val mainEffect = supportAction.id.mainEffectType()
    Row (modifier = modifier
        .background(Color.White)
        .border(1.dp, Color.Black)
        .padding(2.dp)) {
        Image(painter = painterResource(id = mainEffect.icon()), contentDescription = stringResource(
            id = mainEffect.name()
        ), modifier = Modifier.size(supportActionResultScreenDisplayOptions.effectIconSize))
        Image(painter = painterResource(id = R.drawable.stat_up), contentDescription = null, modifier = Modifier.size(supportActionResultScreenDisplayOptions.effectIconSize))
    }
}