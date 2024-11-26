package org.deathdric.ultimatecatbattle.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.data.AttackRepository
import org.deathdric.ultimatecatbattle.data.SupportRepository
import org.deathdric.ultimatecatbattle.model.AttackAction
import org.deathdric.ultimatecatbattle.model.AttackActionId
import org.deathdric.ultimatecatbattle.model.SupportAction
import org.deathdric.ultimatecatbattle.model.SupportActionId
import org.deathdric.ultimatecatbattle.model.TargetType
import org.deathdric.ultimatecatbattle.model.toAttackAction
import org.deathdric.ultimatecatbattle.model.toSupportAction
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.icon
import org.deathdric.ultimatecatbattle.ui.mainEffectType
import org.deathdric.ultimatecatbattle.ui.name
import org.deathdric.ultimatecatbattle.ui.toolkit.SimpleButton
import org.deathdric.ultimatecatbattle.ui.toolkit.TitleText
import org.deathdric.ultimatecatbattle.vm.GameStatus
import org.deathdric.ultimatecatbattle.vm.GameUiState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

data class MoveSelectScreenDisplayOptions (
    val sharedOptions: SharedGameScreenDisplayOptions,
    val actionButtonDisplayOptions: ActionButtonDisplayOptions,
    val columnSize : Dp
)

data class ActionButtonDisplayOptions(
    val baseIconSize: Dp,
    val numberFontSize : TextUnit,
    val showActionName: Boolean
)

fun ScreenConstraints.toMoveSelectScreenDisplayOptions() : MoveSelectScreenDisplayOptions {
    val sharedOptions = this.toSharedGameScreenDisplayOptions()
    val columnSize = ((this.maxWidth / 2) - 20.dp).coerceAtMost(220.dp);
    val actionIconSize = if(columnSize < 180.dp) {
        18.dp
    } else if (columnSize < 200.dp) {
        20.dp
    } else if (maxWidth < 690.dp) {
        25.dp
    } else {
        30.dp
    }

    val actionTextSize = if (columnSize < 180.dp) {
        10.sp
    } else if(columnSize < 200.dp) {
        11.sp
    } else if (maxWidth < 690.dp) {
        12.sp
    } else {
        14.sp
    }

    val showActionName = (maxHeight > 490.dp)

    val actionButtonDisplayOptions = ActionButtonDisplayOptions(actionIconSize, actionTextSize, showActionName)
    return MoveSelectScreenDisplayOptions(sharedOptions,
        actionButtonDisplayOptions,
        columnSize)
}

@Composable
fun MoveSelectScreen(screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
                     gameState: GameUiState, modifier: Modifier = Modifier
) {
    val displayOptions = screenConstraints.toMoveSelectScreenDisplayOptions()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TitleText(
            text = stringResource(id = R.string.select_move),
            modifier = Modifier
                .fillMaxWidth()
                .padding(displayOptions.sharedOptions.titlePadding),
            sizeMode = displayOptions.sharedOptions.titleSizeMode
        )
        val attackActionList = gameState.playerInfo.activePlayer.player.attackActions
        val supportActionList = gameState.playerInfo.activePlayer.player.supportActions
        LazyVerticalGrid(columns = GridCells.Adaptive(displayOptions.columnSize), modifier = modifier) {
            items(attackActionList) { attackAction ->
                AttackActionButton(attackAction = attackAction,
                    onClick = { viewModel.selectAttack(attackAction.id )},
                    displayOptions = displayOptions.actionButtonDisplayOptions,
                    curTime = gameState.playerInfo.curTime,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth())
            }
            items(supportActionList) { supportAction ->

                SupportActionButton(supportAction = supportAction,
                    onClick = { viewModel.selectSupport(supportAction.id)},
                    displayOptions = displayOptions.actionButtonDisplayOptions,
                    curTime = gameState.playerInfo.curTime,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth())
            }
        }
        SimpleButton(onClick = { viewModel.displayNewTurn() }, sizeMode = displayOptions.sharedOptions.buttonSizeMode, text = stringResource(id = R.string.start_turn),
            modifier = Modifier.padding(bottom = displayOptions.sharedOptions.bottomPadding))
    }
}

@Composable
fun AttackActionButton(attackAction: AttackAction,
                       curTime: Int,
                       onClick: () -> Unit,
                       displayOptions: ActionButtonDisplayOptions,
                       modifier: Modifier = Modifier) {
    val targetsIconId = if (attackAction.targetType == TargetType.ALL_ENEMIES) R.drawable.multi_target else R.drawable.single_target
    val enabled = attackAction.isAvailable(curTime)
    val textColor = if (enabled) Color.Black else colorResource(id = R.color.disabled_foreground_color)
    val alpha = if (enabled) 1f else 0.5f
    val borderColor = if (enabled) colorResource(id = R.color.attack_border_color) else Color.Black
    Button(
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.attack_container_color),
            disabledContainerColor = colorResource(id = R.color.disabled_background_color),
            disabledContentColor = colorResource(id = R.color.disabled_foreground_color),
            contentColor = Color.Black),
        onClick = onClick,
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(10),
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            if (displayOptions.showActionName) {
                Text(
                    text = stringResource(
                        id = attackAction.id.name()
                    ),
                    fontFamily = FontFamily.SansSerif,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = displayOptions.numberFontSize,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Row (horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = attackAction.id.icon()), contentDescription = stringResource(
                    id = attackAction.id.name()
                ), modifier = Modifier.size(displayOptions.baseIconSize * 2),
                    alpha = alpha)
                Column {
                    if (enabled) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = targetsIconId),
                                contentDescription = null,
                                modifier = Modifier.size(displayOptions.baseIconSize)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.clock),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 4.dp)
                                    .size(displayOptions.baseIconSize)
                            )
                            Text(
                                text = attackAction.delay.toString(),
                                fontFamily = FontFamily.SansSerif,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = displayOptions.numberFontSize
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.damage),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .size(displayOptions.baseIconSize)
                            )
                            Text(
                                text = "%d - %d".format(
                                    attackAction.minDamage,
                                    attackAction.maxDamage
                                ),
                                fontFamily = FontFamily.SansSerif,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = displayOptions.numberFontSize
                            )
                        }
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(Color.White)
                            .padding(4.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.hourglass),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .size(displayOptions.baseIconSize)
                            )
                            Text(
                                text = "%d".format(
                                    attackAction.nextAvailable - curTime
                                ),
                                fontFamily = FontFamily.SansSerif,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = displayOptions.numberFontSize
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SupportActionButton(supportAction: SupportAction,
                        curTime: Int,
                        onClick: () -> Unit,
                        displayOptions: ActionButtonDisplayOptions,
                        modifier: Modifier = Modifier) {
    val targetsIconId = if (supportAction.targetType == TargetType.ALL_ALLIES) R.drawable.multi_target else R.drawable.single_target
    val enabled = supportAction.isAvailable(curTime)
    val textColor = if (enabled) Color.Black else colorResource(id = R.color.disabled_foreground_color)
    val alpha = if (enabled) 1f else 0.5f
    val borderColor = if (enabled) colorResource(id = R.color.support_border_color) else Color.Black
    Button(
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.support_container_color),
            disabledContainerColor = colorResource(id = R.color.disabled_background_color),
            disabledContentColor = colorResource(id = R.color.disabled_foreground_color),
            contentColor = Color.Black),
        onClick = onClick,
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(10),
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            if (displayOptions.showActionName) {
                Text(
                    text = stringResource(
                        id = supportAction.id.name()
                    ),
                    fontFamily = FontFamily.SansSerif,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = displayOptions.numberFontSize,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Row (horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = supportAction.id.icon()), contentDescription = stringResource(
                    id = supportAction.id.name()
                ), modifier = Modifier.size(displayOptions.baseIconSize * 2),
                    alpha = alpha)
                Column {
                    if (enabled) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = targetsIconId),
                                contentDescription = null,
                                modifier = Modifier.size(displayOptions.baseIconSize)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.clock),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 4.dp)
                                    .size(displayOptions.baseIconSize)
                            )
                            Text(
                                text = supportAction.delay.toString(),
                                fontFamily = FontFamily.SansSerif,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = displayOptions.numberFontSize
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = supportAction.id.mainEffectType().icon()),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(displayOptions.baseIconSize)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.stat_up),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .size(displayOptions.baseIconSize)
                            )
                        }
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(Color.White)
                            .padding(4.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.hourglass),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .size(displayOptions.baseIconSize)
                            )
                            Text(
                                text = "%d".format(
                                    supportAction.nextAvailable - curTime
                                ),
                                fontFamily = FontFamily.SansSerif,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = displayOptions.numberFontSize
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun SupportButtonPreview() {
    val supportAction = SupportRepository.getSupport(SupportActionId.BERSERK).toSupportAction()
    val displayOptions = ActionButtonDisplayOptions(baseIconSize = 25.dp, numberFontSize = 13.sp, true)
    SupportActionButton(supportAction = supportAction, onClick = { /*TODO*/ }, displayOptions = displayOptions, curTime = 0, modifier = Modifier.size(200.dp, 160.dp))
}

@Composable
@Preview
fun AttackButtonPreview() {
    val attackAction = AttackRepository.getAttack(AttackActionId.FINGER_OF_DEATH).toAttackAction(1, 1)
    val displayOptions = ActionButtonDisplayOptions(baseIconSize = 25.dp, numberFontSize = 13.sp, true)
    AttackActionButton(attackAction = attackAction, onClick = { /*TODO*/ }, displayOptions = displayOptions, curTime = 0, modifier = Modifier.size(200.dp, 160.dp))
}

@Composable
@Preview
fun DisabledAttackButtonPreview() {
    val attackAction = AttackRepository.getAttack(AttackActionId.FINGER_OF_DEATH).toAttackAction(1, 1)
    attackAction.updateAvailability(100)
    val displayOptions = ActionButtonDisplayOptions(baseIconSize = 25.dp, numberFontSize = 13.sp, true)
    AttackActionButton(attackAction = attackAction, onClick = { /*TODO*/ }, displayOptions = displayOptions, curTime = 0, modifier = Modifier.size(200.dp, 160.dp))
}

@Composable
fun GenericMoveSelectScreenPreview(screenConstraints: ScreenConstraints) {
    val playerInfo = createPlayerInfoForPreview()
    val gameState = GameUiState(playerInfo = playerInfo, GameStatus.PLAYER_TURN)
    MoveSelectScreen(screenConstraints = screenConstraints, viewModel = UltimateCatBattleViewModel(), gameState = gameState,
        modifier = Modifier
            .background(Color.White)
            .requiredSize(screenConstraints.maxWidth, screenConstraints.maxHeight))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=330dp,height=295dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericMoveSelectScreenSmallSize() {
    GenericMoveSelectScreenPreview(screenConstraints = ScreenConstraints(330.dp, 295.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericMoveSelectScreenMediumSize() {
    GenericMoveSelectScreenPreview(screenConstraints = ScreenConstraints(450.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=325dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericMoveSelectScreenHigherWidth() {
    GenericMoveSelectScreenPreview(screenConstraints = ScreenConstraints(530.dp, 325.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=400dp,height=495dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericMoveSelectScreenSmallPortrait() {
    GenericMoveSelectScreenPreview(screenConstraints = ScreenConstraints(400.dp, 495.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=450dp,height=625dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericMoveSelectScreenMediumPortrait() {
    GenericMoveSelectScreenPreview(screenConstraints = ScreenConstraints(450.dp, 625.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=530dp,height=695dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
fun GenericMoveSelectScreenLargePortrait() {
    GenericMoveSelectScreenPreview(screenConstraints = ScreenConstraints(530.dp, 695.dp))
}


@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=780dp,height=720dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun GenericMoveSelectScreenVeryLargeSize() {
    GenericMoveSelectScreenPreview(screenConstraints = ScreenConstraints(780.dp, 720.dp))
}