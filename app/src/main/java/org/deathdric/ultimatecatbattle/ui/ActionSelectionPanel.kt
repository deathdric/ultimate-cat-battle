package org.deathdric.ultimatecatbattle.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.data.AttackRepository
import org.deathdric.ultimatecatbattle.data.SupportRepository
import org.deathdric.ultimatecatbattle.model.AttackAction
import org.deathdric.ultimatecatbattle.model.SupportAction

@Composable
fun StatDetailPanel(
    @DrawableRes
    iconResource: Int,
    data : String,
    modifier: Modifier = Modifier
) {
    Row (modifier = modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically){
        Image(
            painter = painterResource(id = iconResource),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .padding(end = 4.dp)
        )
        MessageText(text = data)
    }
}

@Composable
fun SupportActionButton(windowWidthSizeClass: WindowWidthSizeClass,
                        windowHeightSizeClass: WindowHeightSizeClass,
                        supportAction: SupportAction,
                       showDetails: Boolean,
                       onClick: () -> Unit,
                       modifier: Modifier = Modifier) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEEFFEE),
            contentColor = Color.Black),
        shape = if (showDetails || windowWidthSizeClass != WindowWidthSizeClass.Expanded) RoundedCornerShape(10) else RoundedCornerShape(50),
        onClick = onClick,
        border = BorderStroke(1.dp, Color(0xFF008000)),
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()) {
        if (showDetails) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (windowWidthSizeClass == WindowWidthSizeClass.Expanded) {
                        TitleText(text = stringResource(id = supportAction.name))
                    } else {
                        MessageText(text = stringResource(id = supportAction.name), color = Color.Black)
                    }
                }
                if (windowWidthSizeClass == WindowWidthSizeClass.Expanded) {
                    Row(horizontalArrangement = Arrangement.Center) {
                        StatDetailPanel(
                            iconResource = R.drawable.clock,
                            data = supportAction.delay.toString()
                        )
                        StatDetailPanel(
                            iconResource = R.drawable.attack,
                            data = supportAction.attackBonus.toString()
                        )
                        StatDetailPanel(
                            iconResource = R.drawable.defense,
                            data = supportAction.defenseBonus.toString()
                        )
                    }
                    Row(horizontalArrangement = Arrangement.Center) {

                        StatDetailPanel(
                            iconResource = R.drawable.hit,
                            data = supportAction.hitBonus.toString()
                        )
                        StatDetailPanel(
                            iconResource = R.drawable.avoid2,
                            data = supportAction.avoidBonus.toString()
                        )
                        StatDetailPanel(
                            iconResource = R.drawable.critical,
                            data = supportAction.criticalBonus.toString()
                        )
                    }
                } else {
                    Row(horizontalArrangement = Arrangement.Center) {
                        StatDetailPanel(
                            iconResource = R.drawable.clock,
                            data = supportAction.delay.toString()
                        )
                        StatDetailPanel(
                            iconResource = R.drawable.attack,
                            data = supportAction.attackBonus.toString()
                        )
                    }
                    Row(horizontalArrangement = Arrangement.Center) {
                        StatDetailPanel(
                            iconResource = R.drawable.defense,
                            data = supportAction.defenseBonus.toString()
                        )
                        StatDetailPanel(
                            iconResource = R.drawable.hit,
                            data = supportAction.hitBonus.toString()
                        )
                    }
                    Row(horizontalArrangement = Arrangement.Center) {

                        StatDetailPanel(
                            iconResource = R.drawable.avoid2,
                            data = supportAction.avoidBonus.toString()
                        )
                        StatDetailPanel(
                            iconResource = R.drawable.critical,
                            data = supportAction.criticalBonus.toString()
                        )
                    }
                }
            }
        } else {
            if (windowWidthSizeClass == WindowWidthSizeClass.Expanded) {
                Row {
                    MessageText(
                        text = "%s  (%s)".format(
                            stringResource(id = supportAction.name),
                            supportAction.delay
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Row (modifier = Modifier.defaultMinSize(minHeight = 60.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    MessageText(
                        text = stringResource(id = supportAction.name),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    MessageText(
                        text = "(%s)".format(
                            supportAction.delay
                        ),
                        color = Color.Black,
                        modifier = Modifier
                            .defaultMinSize(minWidth = 50.dp)
                            .padding(start = 4.dp),
                        textAlign = TextAlign.Right
                    )
                }
            }
        }
    }
}

@Composable
fun AttackActionButton(windowWidthSizeClass: WindowWidthSizeClass,
                       windowHeightSizeClass: WindowHeightSizeClass,
                       attackAction: AttackAction,
                       showDetails: Boolean,
                       onClick: () -> Unit,
                       modifier: Modifier = Modifier) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.attack_container_color),
            contentColor = Color.Black),
        onClick = onClick,
        border = BorderStroke(1.dp, colorResource(id = R.color.attack_border_color)),
        shape = if (showDetails || windowWidthSizeClass != WindowWidthSizeClass.Expanded) RoundedCornerShape(10) else RoundedCornerShape(50),
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()) {
        if (showDetails) {
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Row (verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween){
                    if (windowWidthSizeClass == WindowWidthSizeClass.Expanded) {
                        TitleText(text = stringResource(id = attackAction.name))
                    } else {
                        MessageText(text = stringResource(id = attackAction.name), color = Color.Black)
                    }
                }
                if (windowWidthSizeClass != WindowWidthSizeClass.Expanded) {
                    Spacer(modifier = Modifier.size(22.dp))
                }
                Row (horizontalArrangement = Arrangement.Center) {
                    StatDetailPanel(
                        iconResource = R.drawable.clock,
                        data = attackAction.delay.toString()
                    )
                    StatDetailPanel(
                        iconResource = R.drawable.damage,
                        data = "%d - %d".format(attackAction.minDamage, attackAction.maxDamage)
                    )
                }
                Row (horizontalArrangement = Arrangement.Center) {

                    StatDetailPanel(
                        iconResource = R.drawable.hit_chance,
                        data = attackAction.hit.toString()
                    )
                    StatDetailPanel(
                        iconResource = R.drawable.critical,
                        data = attackAction.critical.toString()
                    )
                }
                if (windowWidthSizeClass != WindowWidthSizeClass.Expanded) {
                    Spacer(modifier = Modifier.size(22.dp))
                }
            }
        } else {
            if (windowWidthSizeClass == WindowWidthSizeClass.Expanded) {
                Row {
                    MessageText(
                        text = "%s  (%s)".format(
                            stringResource(id = attackAction.name),
                            attackAction.delay
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Row (modifier = Modifier.defaultMinSize(minHeight = 60.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    MessageText(
                        text = stringResource(id = attackAction.name),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    MessageText(
                        text = "(%s)".format(
                            attackAction.delay
                        ),
                        color = Color.Black,
                        modifier = Modifier
                            .defaultMinSize(minWidth = 50.dp)
                            .padding(start = 4.dp),
                        textAlign = TextAlign.Right
                    )
                }
            }
        }


    }
}

@Composable
fun ActionSelectionPanel(windowWidthSizeClass: WindowWidthSizeClass, windowHeightSizeClass: WindowHeightSizeClass, viewModel: UltimateCatBattleViewModel, uiState: UltimateCatBattleUiState, modifier: Modifier = Modifier) {
    val columnSize = if (windowWidthSizeClass == WindowWidthSizeClass.Expanded) 270.dp else 200.dp
    LazyVerticalGrid(columns = GridCells.Adaptive(columnSize), modifier = modifier) {
        items(uiState.availableAttacks) { attackAction ->
            AttackActionButton(attackAction = attackAction,
                windowHeightSizeClass = windowHeightSizeClass,
                windowWidthSizeClass = windowWidthSizeClass,
                showDetails = uiState.showActionDetails,
                onClick = { viewModel.selectAttack(attackAction = attackAction) },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth())
        }
        items(uiState.availableSupports) { supportAction ->
            SupportActionButton(
                windowWidthSizeClass = windowWidthSizeClass,
                windowHeightSizeClass = windowHeightSizeClass,
                supportAction = supportAction,
                showDetails = uiState.showActionDetails,
                onClick = { viewModel.selectSupport(supportAction) },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth())
        }
    }
}

@Composable
@Preview
fun DetailedAttackButtonPreview() {
    AttackActionButton(attackAction = AttackRepository.dragonFist, showDetails = true, onClick = { }, windowWidthSizeClass = WindowWidthSizeClass.Expanded, windowHeightSizeClass = WindowHeightSizeClass.Compact)
}

@Composable
@Preview
fun DetailedAttackButtonMediumPreview() {
    AttackActionButton(attackAction = AttackRepository.dragonFist, showDetails = true, onClick = { }, windowWidthSizeClass = WindowWidthSizeClass.Medium, windowHeightSizeClass = WindowHeightSizeClass.Compact)
}

@Composable
@Preview
fun AttackButtonPreview() {
    AttackActionButton(attackAction = AttackRepository.dragonFist, showDetails = false, onClick = { }, windowWidthSizeClass = WindowWidthSizeClass.Expanded, windowHeightSizeClass = WindowHeightSizeClass.Compact)
}

@Composable
@Preview
fun AttackButtonMediumPreview() {
    AttackActionButton(attackAction = AttackRepository.dragonFist, showDetails = false, onClick = { }, windowWidthSizeClass = WindowWidthSizeClass.Medium, windowHeightSizeClass = WindowHeightSizeClass.Compact)
}

@Composable
@Preview
fun DetailedSupportButtonPreview() {
    SupportActionButton(supportAction = SupportRepository.speed, showDetails = true, onClick = { }, windowWidthSizeClass = WindowWidthSizeClass.Expanded, windowHeightSizeClass = WindowHeightSizeClass.Compact)
}

@Composable
@Preview
fun DetailedSupportMediumButtonPreview() {
    SupportActionButton(supportAction = SupportRepository.speed, showDetails = true, onClick = { }, windowWidthSizeClass = WindowWidthSizeClass.Medium, windowHeightSizeClass = WindowHeightSizeClass.Compact)
}

@Composable
@Preview
fun SupportButtonPreview() {
    SupportActionButton(supportAction = SupportRepository.speed, showDetails = false, onClick = { }, windowWidthSizeClass = WindowWidthSizeClass.Expanded, windowHeightSizeClass = WindowHeightSizeClass.Compact)
}