package org.deathdric.ultimatecatbattle.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.TargetType
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.aliveIcon
import org.deathdric.ultimatecatbattle.ui.icon
import org.deathdric.ultimatecatbattle.ui.name
import org.deathdric.ultimatecatbattle.ui.toolkit.ImageButton
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText
import org.deathdric.ultimatecatbattle.ui.toolkit.TitleText
import org.deathdric.ultimatecatbattle.vm.GamePlayerInfo
import org.deathdric.ultimatecatbattle.vm.SupportSelectionState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

data class SupportConfirmScreenDisplayOptions (
    val sharedOptions: SharedGameScreenDisplayOptions,
    val descriptionTextSize: TextUnit,
    val attackIconSize: Dp,
    val standardIconSize: Dp,
    val mainTextSize: TextUnit,
    val targetPlayerIconSize: Dp,
    val showTimeDescriptions : Boolean,
    val statPreviewDisplayOptions: StatPreviewDisplayOptions,
    val supportConfirmStatLayoutMode: SupportConfirmStatLayoutMode
)

enum class SupportConfirmStatLayoutMode {
    SINGLE_COLUMN,
    TWO_COLUMNS
}

fun ScreenConstraints.toSupportConfirmScreenDisplayOptions() : SupportConfirmScreenDisplayOptions {
    val descriptionTextSize = if (maxWidth < 350.dp) {
        10.sp
    } else if(maxWidth < 450.dp) {
        11.sp
    } else if (maxWidth < 690.dp) {
        12.sp
    } else {
        14.sp
    }

    val mainTextSize = if (maxWidth < 350.dp) {
        12.sp
    } else if(maxWidth < 450.dp) {
        13.sp
    } else if (maxWidth < 690.dp) {
        14.sp
    } else {
        16.sp
    }

    val attackIconSize = if(maxWidth < 350.dp) {
        60.dp
    } else if (maxWidth < 450.dp) {
        70.dp
    } else if (maxWidth < 690.dp) {
        80.dp
    } else {
        100.dp
    }

    val standardIconSize = if(maxWidth < 350.dp) {
        20.dp
    } else if (maxWidth < 450.dp) {
        25.dp
    } else if (maxWidth < 690.dp) {
        30.dp
    } else {
        35.dp
    }

    val targetPlayerIconSize = if(maxWidth < 350.dp) {
        50.dp
    } else if (maxWidth < 450.dp) {
        60.dp
    } else if (maxWidth < 690.dp) {
        70.dp
    } else {
        80.dp
    }

    val showTimeDescriptions = this.maxWidth > 550.dp

    val supportConfirmStatLayoutMode = if (this.maxHeight > this.maxWidth && this.maxWidth < 530.dp) {
        SupportConfirmStatLayoutMode.SINGLE_COLUMN
    } else {
        SupportConfirmStatLayoutMode.TWO_COLUMNS
    }



    val previewIconSize = if(maxWidth < 350.dp) {
        16.dp
    } else if (maxWidth < 450.dp) {
        20.dp
    } else if (maxWidth < 690.dp) {
        24.dp
    } else {
        28.dp
    }

    return SupportConfirmScreenDisplayOptions(
        this.toSharedGameScreenDisplayOptions(),
        descriptionTextSize, attackIconSize, standardIconSize, mainTextSize, targetPlayerIconSize,
        showTimeDescriptions = showTimeDescriptions,
        statPreviewDisplayOptions = StatPreviewDisplayOptions(previewIconSize = previewIconSize,
        previewTextSize = mainTextSize),
        supportConfirmStatLayoutMode = supportConfirmStatLayoutMode
    )
}

@Composable
fun SupportConfirmScreen(screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
                         playerInfo: GamePlayerInfo,
                        supportSelectionState: SupportSelectionState, modifier: Modifier = Modifier
) {
    val displayOptions = screenConstraints.toSupportConfirmScreenDisplayOptions()
    val supportEffects = if (supportSelectionState.selectedTarget == playerInfo.activePlayer.player.id
        && supportSelectionState.supportAction.targetType == TargetType.ONE_ALLY) supportSelectionState.supportAction.selfEffects else supportSelectionState.supportAction.otherEffects
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TitleText(
            text = stringResource(id = supportSelectionState.supportAction.id.name()),
            modifier = Modifier
                .fillMaxWidth()
                .padding(displayOptions.sharedOptions.titlePadding),
            sizeMode = displayOptions.sharedOptions.titleSizeMode
        )
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .background(Color.White)
            .padding(4.dp)
            .border(width = 1.dp, color = colorResource(id = R.color.support_border_color))
            .fillMaxWidth()) {
            Column (verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(colorResource(id = R.color.support_border_color))) {
                Image(painter = painterResource(id = supportSelectionState.supportAction.id.icon()),
                    contentDescription = stringResource(id = supportSelectionState.supportAction.id.name()),
                    modifier = Modifier.size(displayOptions.attackIconSize)
                )
                StandardText(text = stringResource(id = R.string.support_move).uppercase(), color = Color.White, modifier = Modifier
                    .padding(4.dp),
                    fontSize = displayOptions.descriptionTextSize, fontWeight = FontWeight.Bold)
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)) {
                Row (horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                    for (targetPlayer in supportSelectionState.availableTargets) {
                        val borderColor = if (targetPlayer == supportSelectionState.selectedTarget) Color.Red else Color.Black;
                        val borderWidth = if (targetPlayer == supportSelectionState.selectedTarget) 2.dp else 1.dp;
                        val backgroundColor = if (supportSelectionState.supportAction.targetType == TargetType.ALL_ALLIES || targetPlayer == supportSelectionState.selectedTarget) {
                            colorResource(id = R.color.active_avatar_color)
                        } else {
                            colorResource(id = R.color.inactive_avatar_color)
                        }
                        Image(painter = painterResource(id = targetPlayer.aliveIcon()), contentDescription = stringResource(
                            id = targetPlayer.name()
                        ), modifier = Modifier
                            .padding(4.dp)
                            .border(borderWidth, borderColor)
                            .background(backgroundColor)
                            .size(displayOptions.targetPlayerIconSize)
                            .clickable { viewModel.changeSupportTarget(targetPlayer) })
                    }
                }
                Row (horizontalArrangement = Arrangement.Center) {
                    val allyTargetText = if (supportSelectionState.supportAction.targetType == TargetType.ALL_ALLIES) {
                        R.string.all_allies_target
                    } else {
                        R.string.single_ally_target
                    }
                    StandardText(text = stringResource(id = allyTargetText), fontSize = displayOptions.descriptionTextSize, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp))
                }
            }
            Column (verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(4.dp)) {
                Row (verticalAlignment = Alignment.CenterVertically){
                    if (displayOptions.showTimeDescriptions) {
                        StandardText(text = stringResource(id = R.string.time_cost), color = Color.Black,
                            fontSize = displayOptions.mainTextSize, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                    Image(painter = painterResource(id = R.drawable.clock), contentDescription = null,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(displayOptions.standardIconSize))
                    StandardText(text = supportSelectionState.supportAction.delay.toString(), color = Color.Black,
                        fontSize = displayOptions.mainTextSize, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
                if (supportSelectionState.supportAction.coolDown > 0) {
                    Row (verticalAlignment = Alignment.CenterVertically){
                        if (displayOptions.showTimeDescriptions) {
                            StandardText(text = stringResource(id = R.string.cooldown_time), color = Color.Black,
                                fontSize = displayOptions.mainTextSize, fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                        }
                        Image(painter = painterResource(id = R.drawable.cooldown), contentDescription = null,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .size(displayOptions.standardIconSize))
                        StandardText(text = supportSelectionState.supportAction.coolDown.toString(), color = Color.Black,
                            fontSize = displayOptions.mainTextSize, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                }
            }
        }

        Row (modifier = Modifier
            .weight(1f)
            .border(width = 1.dp, color = colorResource(id = R.color.support_border_color))
            .background(
                colorResource(id = R.color.support_container_color)
            )
            .fillMaxWidth()
            .verticalScroll(ScrollState(0), true)) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Row (
                    modifier = modifier
                        .padding(4.dp)
                        .border(1.dp, colorResource(id = R.color.support_border_color))
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Column {
                        if (displayOptions.supportConfirmStatLayoutMode == SupportConfirmStatLayoutMode.TWO_COLUMNS) {

                            val hasEvenEffects = supportEffects.size % 2 == 0
                            val effectsPerColumn = (supportEffects.size + 1)/2
                            Row (verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.weight(1f)) {
                                    for (effIndex in 0 until effectsPerColumn) {
                                        StatusEffectPreviewPanel(
                                            displayOptions = displayOptions.statPreviewDisplayOptions,
                                            statusEffect = supportEffects[effIndex]
                                        )
                                    }
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    for (effIndex in effectsPerColumn until supportEffects.size) {
                                        StatusEffectPreviewPanel(
                                            displayOptions = displayOptions.statPreviewDisplayOptions,
                                            statusEffect = supportEffects[effIndex]
                                        )
                                    }
                                    if (!hasEvenEffects) {
                                        StatusDurationPreviewPanel(
                                            displayOptions = displayOptions.statPreviewDisplayOptions,
                                            supportSelectionState.supportAction.duration
                                        )
                                    }
                                }
                            }
                            if (hasEvenEffects) {
                                Row {
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        StatusDurationPreviewPanel(
                                            displayOptions = displayOptions.statPreviewDisplayOptions,
                                            duration = supportSelectionState.supportAction.duration
                                        )
                                    }

                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        } else {
                            Row (verticalAlignment = Alignment.CenterVertically) {
                                Column {
                                    for (statusEffectDetail in supportEffects) {
                                        StatusEffectPreviewPanel(
                                            displayOptions = displayOptions.statPreviewDisplayOptions,
                                            statusEffect = statusEffectDetail
                                        )
                                    }
                                    StatusDurationPreviewPanel(
                                        displayOptions = displayOptions.statPreviewDisplayOptions,
                                        duration = supportSelectionState.supportAction.duration
                                    )
                                }
                            }
                        }
                    }

                }

            }

        }
        Row (modifier = Modifier
            .padding(bottom = displayOptions.sharedOptions.bottomPadding)
            .background(Color.White)
            .border(1.dp, Color.Black)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            ImageButton(
                onClick = { viewModel.cancelSupport() },
                text = stringResource(id = R.string.cancel),
                image = painterResource(
                    id = R.drawable.cancel
                ),
                containerColor = colorResource(id = R.color.support_border_color),
                contentColor = colorResource(id = R.color.support_container_color),
                sizeMode = displayOptions.sharedOptions.buttonSizeMode,
                modifier = Modifier.weight(1f)
            )
            ImageButton(
                onClick = { viewModel.confirmSupport() },
                text = stringResource(id = R.string.confirm),
                image = painterResource(
                    id = R.drawable.confirm,
                ),
                containerColor = colorResource(id = R.color.support_border_color),
                contentColor = colorResource(id = R.color.support_container_color),
                sizeMode = displayOptions.sharedOptions.buttonSizeMode,
                modifier = Modifier.weight(1f)
            )
        }
    }

}

@Composable
fun GenericSupportConfirmScreenPreview(screenConstraints: ScreenConstraints) {
    val playerInfo = createTeamPlayerInfoForPreview()
    val supportAction = playerInfo.activePlayer.player.supportActions[0]
    val teamIds = playerInfo.players.filter { it.team.id == playerInfo.activePlayer.team.id }.map { it.player.id }
    val supportState = SupportSelectionState(teamIds, playerInfo.activePlayer.player.id, supportAction)

    SupportConfirmScreen(screenConstraints = screenConstraints, viewModel = UltimateCatBattleViewModel(),
        supportSelectionState = supportState,
        playerInfo = playerInfo,
        modifier = Modifier
            .background(Color.White)
            .requiredSize(screenConstraints.maxWidth, screenConstraints.maxHeight))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=330dp,height=295dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericSupportConfirmScreenSmallSize() {
    GenericSupportConfirmScreenPreview(screenConstraints = ScreenConstraints(330.dp, 295.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericSupportConfirmScreenMediumSize() {
    GenericSupportConfirmScreenPreview(screenConstraints = ScreenConstraints(450.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericSupportConfirmScreenHigherWidth() {
    GenericSupportConfirmScreenPreview(screenConstraints = ScreenConstraints(530.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=400dp,height=495dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericSupportConfirmScreenSmallPortrait() {
    GenericSupportConfirmScreenPreview(screenConstraints = ScreenConstraints(400.dp, 495.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=625dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericSupportConfirmScreenMediumPortrait() {
    GenericSupportConfirmScreenPreview(screenConstraints = ScreenConstraints(450.dp, 625.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=695dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericSupportConfirmScreenLargePortrait() {
    GenericSupportConfirmScreenPreview(screenConstraints = ScreenConstraints(530.dp, 695.dp))
}


@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=780dp,height=720dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericSupportConfirmScreenVeryLargeSize() {
    GenericSupportConfirmScreenPreview(screenConstraints = ScreenConstraints(780.dp, 720.dp))
}