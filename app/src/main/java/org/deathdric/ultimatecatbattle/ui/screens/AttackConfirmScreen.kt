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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.AttackAction
import org.deathdric.ultimatecatbattle.model.AttackActionId
import org.deathdric.ultimatecatbattle.model.DelayEffect
import org.deathdric.ultimatecatbattle.model.TargetType
import org.deathdric.ultimatecatbattle.model.TargetedAttackActionPreview
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.aliveIcon
import org.deathdric.ultimatecatbattle.ui.icon
import org.deathdric.ultimatecatbattle.ui.name
import org.deathdric.ultimatecatbattle.ui.toolkit.ImageButton
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText
import org.deathdric.ultimatecatbattle.ui.toolkit.TitleText
import org.deathdric.ultimatecatbattle.vm.AttackSelectionState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel


data class AttackConfirmScreenDisplayOptions (
    val sharedOptions: SharedGameScreenDisplayOptions,
    val descriptionTextSize: TextUnit,
    val attackIconSize: Dp,
    val standardIconSize: Dp,
    val mainTextSize: TextUnit,
    val targetPlayerIconSize: Dp,
    val showTimeDescriptions : Boolean,
    val statPreviewDisplayOptions: StatPreviewDisplayOptions,
    val attackConfirmStatLayoutMode: AttackConfirmStatLayoutMode
)

enum class AttackConfirmStatLayoutMode {
    SINGLE_COLUMN,
    TWO_COLUMNS_INSIDE,
    TWO_COLUMNS_OUTSIDE
}

fun ScreenConstraints.toAttackConfirmScreenDisplayOptions() : AttackConfirmScreenDisplayOptions {
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

    val attackConfirmStatLayoutMode = if ((this.maxWidth < 400.dp) || (this.maxHeight > this.maxWidth && this.maxWidth < 530.dp)) {
        AttackConfirmStatLayoutMode.SINGLE_COLUMN
    } else if (this.maxHeight >= 690.dp) {
        AttackConfirmStatLayoutMode.TWO_COLUMNS_OUTSIDE
    }  else {
        AttackConfirmStatLayoutMode.TWO_COLUMNS_INSIDE
    }



    val previewIconSize = if (attackConfirmStatLayoutMode != AttackConfirmStatLayoutMode.TWO_COLUMNS_INSIDE) {
        standardIconSize
    } else if(maxWidth < 350.dp) {
        16.dp
    } else if (maxWidth < 450.dp) {
        20.dp
    } else if (maxWidth < 690.dp) {
        24.dp
    } else {
        28.dp
    }

    val previewTextSize = if (attackConfirmStatLayoutMode == AttackConfirmStatLayoutMode.TWO_COLUMNS_INSIDE) {
        descriptionTextSize
    } else {
        mainTextSize
    }

    return AttackConfirmScreenDisplayOptions(this.toSharedGameScreenDisplayOptions(),
        descriptionTextSize, attackIconSize, standardIconSize, mainTextSize, targetPlayerIconSize,
        showTimeDescriptions = showTimeDescriptions, statPreviewDisplayOptions = StatPreviewDisplayOptions(previewIconSize = previewIconSize,
        previewTextSize = previewTextSize),
        attackConfirmStatLayoutMode = attackConfirmStatLayoutMode)
}

@Composable
fun AttackConfirmScreen(screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
                        attackSelectionState: AttackSelectionState, modifier: Modifier = Modifier
) {
    val displayOptions = screenConstraints.toAttackConfirmScreenDisplayOptions()
    Column (modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        TitleText(
            text = stringResource(id = attackSelectionState.preview.attackAction.id.name()),
            modifier = Modifier
                .fillMaxWidth()
                .padding(displayOptions.sharedOptions.titlePadding),
            sizeMode = displayOptions.sharedOptions.titleSizeMode
        )
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .background(Color.White)
            .padding(4.dp)
            .border(width = 1.dp, color = colorResource(id = R.color.attack_border_color))
            .fillMaxWidth()) {
            Column (verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(colorResource(id = R.color.attack_border_color))) {
                Image(painter = painterResource(id = attackSelectionState.preview.attackAction.id.icon()),
                    contentDescription = stringResource(id = attackSelectionState.preview.attackAction.id.name()),
                    modifier = Modifier.size(displayOptions.attackIconSize)
                )
                StandardText(text = stringResource(id = R.string.attack_move).uppercase(), color = Color.White, modifier = Modifier
                    .padding(4.dp),
                    fontSize = displayOptions.descriptionTextSize, fontWeight = FontWeight.Bold)
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)) {
                Row (horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                    for (targetPlayer in attackSelectionState.preview.targetedPreviews) {
                        val borderColor = if (targetPlayer.target.id == attackSelectionState.selectedTarget) Color.Red else Color.Black;
                        val borderWidth = if (targetPlayer.target.id == attackSelectionState.selectedTarget) 2.dp else 1.dp;
                        val backgroundColor = if (attackSelectionState.preview.attackAction.targetType == TargetType.ALL_ENEMIES || targetPlayer.target.id == attackSelectionState.selectedTarget) {
                            colorResource(id = R.color.active_avatar_color)
                        } else {
                            colorResource(id = R.color.inactive_avatar_color)
                        }
                        Image(painter = painterResource(id = targetPlayer.target.id.aliveIcon()), contentDescription = stringResource(
                            id = targetPlayer.target.id.name()
                        ), modifier = Modifier
                            .padding(4.dp)
                            .border(borderWidth, borderColor)
                            .background(backgroundColor)
                            .size(displayOptions.targetPlayerIconSize)
                            .clickable { viewModel.changeAttackTarget(targetPlayer.target.id) })
                    }
                }
                Row (horizontalArrangement = Arrangement.Center) {
                    val enemyTargetText = if (attackSelectionState.preview.attackAction.targetType == TargetType.ALL_ENEMIES) {
                        R.string.all_enemies_target
                    } else {
                        R.string.single_enemy_target
                    }
                    StandardText(text = stringResource(id = enemyTargetText), fontSize = displayOptions.descriptionTextSize, fontWeight = FontWeight.Bold,
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
                    StandardText(text = attackSelectionState.preview.attackAction.delay.toString(), color = Color.Black,
                        fontSize = displayOptions.mainTextSize, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
                if (attackSelectionState.preview.attackAction.coolDown > 0) {
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
                        StandardText(text = attackSelectionState.preview.attackAction.coolDown.toString(), color = Color.Black,
                            fontSize = displayOptions.mainTextSize, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                }
            }
        }
        val attackInfo = attackSelectionState.preview.targetedPreviews.first { it.target.id == attackSelectionState.selectedTarget }
        val attackAction = attackSelectionState.preview.attackAction
        Row (modifier = Modifier
            .weight(1f)
            .border(width = 1.dp, color = colorResource(id = R.color.attack_border_color))
            .background(
                colorResource(id = R.color.attack_container_color)
            )
            .fillMaxWidth()
            .verticalScroll(ScrollState(0), true)) {
            if (displayOptions.attackConfirmStatLayoutMode == AttackConfirmStatLayoutMode.TWO_COLUMNS_OUTSIDE) {
                Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    AllStatPreviewPanel(displayOptions, attackInfo)
                }
                Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    AttackExtraEffectPreviewPanel(displayOptions, attackAction)
                }
            } else {
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    AllStatPreviewPanel(displayOptions, attackInfo)
                    AttackExtraEffectPreviewPanel(displayOptions, attackAction)
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
                onClick = { viewModel.cancelAttack() },
                text = stringResource(id = R.string.cancel),
                image = painterResource(
                    id = R.drawable.cancel
                ),
                containerColor = colorResource(id = R.color.attack_border_color),
                contentColor = colorResource(id = R.color.attack_container_color),
                sizeMode = displayOptions.sharedOptions.buttonSizeMode,
                modifier = Modifier.weight(1f)
            )
            ImageButton(
                onClick = { viewModel.confirmAttack() },
                text = stringResource(id = R.string.confirm),
                image = painterResource(
                    id = R.drawable.confirm,
                ),
                containerColor = colorResource(id = R.color.attack_border_color),
                contentColor = colorResource(id = R.color.attack_container_color),
                sizeMode = displayOptions.sharedOptions.buttonSizeMode,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun AttackExtraEffectPreviewPanel(displayOptions: AttackConfirmScreenDisplayOptions, attackAction: AttackAction, modifier: Modifier = Modifier) {
    Row (modifier = modifier
        .padding(4.dp)
        .border(1.dp, colorResource(id = R.color.attack_border_color))
        .fillMaxWidth()
        .background(Color.White)) {
        if (attackAction.statusEffect != null) {
            Column {
                if (displayOptions.attackConfirmStatLayoutMode != AttackConfirmStatLayoutMode.TWO_COLUMNS_OUTSIDE) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        StandardText(
                            text = stringResource(id = R.string.status_effect),
                            color = Color.White,
                            modifier = Modifier
                                .background(colorResource(id = R.color.attack_border_color))
                                .padding(4.dp), fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Column(verticalArrangement = Arrangement.Center) {
                            ChancePreviewPanel(
                                displayOptions = displayOptions,
                                chanceValue = attackAction.statusEffect.chance,
                                expand = false
                            )
                        }
                    }
                } else {
                    StandardText(
                        text = stringResource(id = R.string.status_effect),
                        color = Color.White,
                        modifier = Modifier
                            .background(colorResource(id = R.color.attack_border_color))
                            .fillMaxWidth()
                            .padding(8.dp), fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }


                if (displayOptions.attackConfirmStatLayoutMode == AttackConfirmStatLayoutMode.TWO_COLUMNS_INSIDE) {
                    val hasEvenEffects = attackAction.statusEffect.effects.size % 2 == 0
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        val effectsPerColumn = (attackAction.statusEffect.effects.size + 1)/2
                        Column (modifier = Modifier.weight(1f)) {
                            for (effIndex in 0 until effectsPerColumn) {
                                StatusEffectPreviewPanel(displayOptions = displayOptions.statPreviewDisplayOptions, statusEffect = attackAction.statusEffect.effects[effIndex])
                            }
                        }
                        Column (modifier = Modifier.weight(1f)) {
                            for (effIndex in effectsPerColumn until attackAction.statusEffect.effects.size) {
                                StatusEffectPreviewPanel(displayOptions = displayOptions.statPreviewDisplayOptions, statusEffect = attackAction.statusEffect.effects[effIndex])
                            }
                            if (!hasEvenEffects) {
                                StatusDurationPreviewPanel(displayOptions = displayOptions.statPreviewDisplayOptions, duration = attackAction.statusEffect.duration)
                            }
                        }
                    }
                    if (hasEvenEffects) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column (modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                                StatusDurationPreviewPanel(
                                    displayOptions = displayOptions.statPreviewDisplayOptions,
                                    duration = attackAction.statusEffect.duration
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                } else {
                    for (statusEffectDetail in attackAction.statusEffect.effects) {
                        StatusEffectPreviewPanel(displayOptions = displayOptions.statPreviewDisplayOptions, statusEffect = statusEffectDetail)
                    }
                    StatusDurationPreviewPanel(displayOptions = displayOptions.statPreviewDisplayOptions, duration = attackAction.statusEffect.duration)
                    if (displayOptions.attackConfirmStatLayoutMode == AttackConfirmStatLayoutMode.TWO_COLUMNS_OUTSIDE) {
                        ChancePreviewPanel(
                            displayOptions = displayOptions,
                            chanceValue = attackAction.statusEffect.chance,
                            expand = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

        } else if (attackAction.delayEffect != null) {
            Column {
                if (displayOptions.attackConfirmStatLayoutMode != AttackConfirmStatLayoutMode.TWO_COLUMNS_OUTSIDE) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        StandardText(
                            text = stringResource(id = R.string.delay_effect),
                            color = Color.White,
                            modifier = Modifier
                                .background(colorResource(id = R.color.attack_border_color))
                                .padding(4.dp), fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Column(verticalArrangement = Arrangement.Center) {
                            ChancePreviewPanel(
                                displayOptions = displayOptions,
                                chanceValue = attackAction.delayEffect.chance,
                                expand = false
                            )
                        }
                    }
                } else {
                    StandardText(
                        text = stringResource(id = R.string.delay_effect),
                        color = Color.White,
                        modifier = Modifier
                            .background(colorResource(id = R.color.attack_border_color))
                            .fillMaxWidth()
                            .padding(8.dp), fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                if (displayOptions.attackConfirmStatLayoutMode == AttackConfirmStatLayoutMode.TWO_COLUMNS_INSIDE) {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Column (modifier = Modifier.weight(1f)) {
                            DelayValuePreviewPanel(displayOptions = displayOptions, delayEffect = attackAction.delayEffect)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                } else {
                    DelayValuePreviewPanel(
                        displayOptions = displayOptions,
                        delayEffect = attackAction.delayEffect
                    )
                    if (displayOptions.attackConfirmStatLayoutMode == AttackConfirmStatLayoutMode.TWO_COLUMNS_OUTSIDE) {
                        ChancePreviewPanel(
                            displayOptions = displayOptions,
                            chanceValue = attackAction.delayEffect.chance,
                            true
                        )
                    }
                }
            }


        } else {
            StandardText(text = stringResource(id = R.string.no_status_effects),
                modifier = Modifier.padding(8.dp), fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DelayValuePreviewPanel(displayOptions: AttackConfirmScreenDisplayOptions, delayEffect: DelayEffect, modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically) {
        StandardText(
            text = stringResource(id = R.string.delay),
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
            fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.hourglass),
            contentDescription = null,
            modifier = modifier.size(displayOptions.statPreviewDisplayOptions.previewIconSize)
        )
        StandardText(
            text = "%d".format(delayEffect.delay),
            modifier = Modifier.padding(end = 16.dp),
            fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ChancePreviewPanel(displayOptions: AttackConfirmScreenDisplayOptions, chanceValue: Int, expand: Boolean, modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .padding(4.dp)) {
        StandardText(
            text = stringResource(id = R.string.chance),
            modifier = Modifier.padding(start = 4.dp, end = 16.dp),
            fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
            fontWeight = FontWeight.Bold
        )
        if (expand) {
            Spacer(modifier = Modifier.weight(1f))
        }
        StandardText(
            text = "%d %%".format(chanceValue),
            modifier = Modifier.padding(end = 16.dp),
            fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
            fontWeight = FontWeight.Bold)

    }
}

@Composable
fun AllStatPreviewPanel(displayOptions: AttackConfirmScreenDisplayOptions, attackInfo: TargetedAttackActionPreview, modifier: Modifier = Modifier) {
    Row (modifier = modifier
        .padding(4.dp)
        .border(1.dp, colorResource(id = R.color.attack_border_color))
        .background(Color.White)) {

        if (displayOptions.attackConfirmStatLayoutMode == AttackConfirmStatLayoutMode.TWO_COLUMNS_INSIDE) {
            Column (modifier = Modifier.weight(1f)) {
                MinDamagePreviewPanel(displayOptions, attackInfo)
                MaxDamagePreviewPanel(displayOptions, attackInfo)
            }
            Column (modifier = Modifier.weight(1f)) {
                HitChancePreviewPanel(displayOptions, attackInfo)
                CritChancePreviewPanel(displayOptions, attackInfo)
            }
        } else {
            Column (modifier = Modifier.fillMaxWidth()) {
                MinDamagePreviewPanel(displayOptions, attackInfo)
                MaxDamagePreviewPanel(displayOptions, attackInfo)
                HitChancePreviewPanel(displayOptions, attackInfo)
                CritChancePreviewPanel(displayOptions, attackInfo)

            }
        }

    }
}

@Composable
fun MinDamagePreviewPanel(displayOptions: AttackConfirmScreenDisplayOptions, attackInfo: TargetedAttackActionPreview, modifier: Modifier = Modifier) {
    Row (modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically){
        Image(painter = painterResource(id = R.drawable.damage), contentDescription = null, modifier = modifier.size(displayOptions.statPreviewDisplayOptions.previewIconSize))
        StandardText(text = stringResource(id = R.string.min_damage),
            modifier = Modifier.padding(start = 4.dp, end = 4.dp), fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        if (attackInfo.minCanKill) {
            Image(painter = painterResource(id = R.drawable.trophy), contentDescription = null,
                modifier = Modifier.size(displayOptions.statPreviewDisplayOptions.previewIconSize))
        }
        StandardText(text = attackInfo.minDamage.toString(),
            modifier = Modifier.padding(end = 16.dp), fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun MaxDamagePreviewPanel(displayOptions: AttackConfirmScreenDisplayOptions, attackInfo: TargetedAttackActionPreview, modifier: Modifier = Modifier) {
    Row (modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically){
        Image(painter = painterResource(id = R.drawable.damage), contentDescription = null, modifier = modifier.size(displayOptions.statPreviewDisplayOptions.previewIconSize))
        StandardText(text = stringResource(id = R.string.max_damage),
            modifier = Modifier.padding(start = 4.dp, end = 4.dp), fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        if (attackInfo.maxCanKill) {
            Image(painter = painterResource(id = R.drawable.trophy), contentDescription = null,
                modifier = Modifier.size(displayOptions.statPreviewDisplayOptions.previewIconSize))
        }
        StandardText(text = attackInfo.maxDamage.toString(),
            modifier = Modifier.padding(end = 16.dp), fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun HitChancePreviewPanel(displayOptions: AttackConfirmScreenDisplayOptions, attackInfo: TargetedAttackActionPreview, modifier: Modifier = Modifier) {
    Row (modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically){
        Image(painter = painterResource(id = R.drawable.hit_chance), contentDescription = null, modifier = modifier.size(displayOptions.statPreviewDisplayOptions.previewIconSize))
        if (attackInfo.hit >= 100) {
            StandardText(text = stringResource(id = R.string.success_guaranteed), color = colorResource(
                id = R.color.stat_bonus_color
            ),
                fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .weight(1f))
        } else {

            StandardText(text = stringResource(id = R.string.chance_to_hit),
                modifier = Modifier.padding(start = 4.dp, end = 4.dp), fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            StandardText(text = "%d %%".format(attackInfo.hit),
                modifier = Modifier.padding(end = 16.dp), fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
                fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun CritChancePreviewPanel(displayOptions: AttackConfirmScreenDisplayOptions, attackInfo: TargetedAttackActionPreview, modifier: Modifier = Modifier) {
    Row (modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically){
        Image(painter = painterResource(id = R.drawable.critical), contentDescription = null, modifier = modifier.size(displayOptions.statPreviewDisplayOptions.previewIconSize))
        StandardText(text = stringResource(id = R.string.chance_to_crit),
            modifier = Modifier.padding(start = 4.dp, end = 4.dp), fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        StandardText(text = "%d %%".format(attackInfo.critical),
            modifier = Modifier.padding(end = 16.dp), fontSize = displayOptions.statPreviewDisplayOptions.previewTextSize,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun GenericSingleAttackConfirmScreenPreview(screenConstraints: ScreenConstraints) {
    val playerInfo = createAllAlivePlayerInfoForPreview()
    val opponents = playerInfo.players.filter { it.player.id != playerInfo.activePlayer.player.id }.map { it.player }.toList()
    val attackPreview = playerInfo.activePlayer.player.attackActions.filter { it.id == AttackActionId.HEART_STRIKE }[0]
        .simulate(playerInfo.activePlayer.player, playerInfo.players.filter { it.player.id != playerInfo.activePlayer.player.id }.map { it.player }.toList())
    val attackSelectionState = AttackSelectionState(attackPreview, opponents[0].id)

    AttackConfirmScreen(screenConstraints = screenConstraints, viewModel = UltimateCatBattleViewModel(),
        attackSelectionState = attackSelectionState,
        modifier = Modifier
            .background(Color.White)
            .requiredSize(screenConstraints.maxWidth, screenConstraints.maxHeight))
}

@Composable
fun GenericMultiAttackConfirmScreenPreview(screenConstraints: ScreenConstraints) {
    val playerInfo = createAllAlivePlayerInfoForPreview()
    val opponents = playerInfo.players.filter { it.player.id != playerInfo.activePlayer.player.id }.map { it.player }.toList()
    val attackPreview = playerInfo.activePlayer.player.attackActions.filter { it.id == AttackActionId.SWEEPING_KICK }[0]
        .simulate(playerInfo.activePlayer.player, playerInfo.players.filter { it.player.id != playerInfo.activePlayer.player.id }.map { it.player }.toList())
    val attackSelectionState = AttackSelectionState(attackPreview, opponents[0].id)

    AttackConfirmScreen(screenConstraints = screenConstraints, viewModel = UltimateCatBattleViewModel(),
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
fun GenericSingleAttackConfirmScreenSmallSize() {
    GenericSingleAttackConfirmScreenPreview(screenConstraints = ScreenConstraints(330.dp, 295.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericSingleAttackConfirmScreenMediumSize() {
    GenericSingleAttackConfirmScreenPreview(screenConstraints = ScreenConstraints(450.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericSingleAttackConfirmScreenHigherWidth() {
    GenericSingleAttackConfirmScreenPreview(screenConstraints = ScreenConstraints(530.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericMultiAttackConfirmScreenHigherWidth() {
    GenericMultiAttackConfirmScreenPreview(screenConstraints = ScreenConstraints(530.dp, 325.dp))
}


@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=400dp,height=495dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericSingleAttackConfirmScreenSmallPortrait() {
    GenericSingleAttackConfirmScreenPreview(screenConstraints = ScreenConstraints(400.dp, 495.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=625dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericSingleAttackConfirmScreenMediumPortrait() {
    GenericSingleAttackConfirmScreenPreview(screenConstraints = ScreenConstraints(450.dp, 625.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=695dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericSingleAttackConfirmScreenLargePortrait() {
    GenericSingleAttackConfirmScreenPreview(screenConstraints = ScreenConstraints(530.dp, 695.dp))
}
@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=691dp,height=700dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericSingleAttackConfirmScreenVeryLargePortrait() {
    GenericSingleAttackConfirmScreenPreview(screenConstraints = ScreenConstraints(691.dp, 700.dp))
}


@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=780dp,height=720dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericSingleAttackConfirmScreenVeryLargeSize() {
    GenericSingleAttackConfirmScreenPreview(screenConstraints = ScreenConstraints(780.dp, 720.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=780dp,height=720dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericMultiAttackConfirmScreenVeryLargeSize() {
    GenericMultiAttackConfirmScreenPreview(screenConstraints = ScreenConstraints(780.dp, 720.dp))
}