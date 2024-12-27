package org.deathdric.ultimatecatbattle.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.PlayerType
import org.deathdric.ultimatecatbattle.model.Power
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.icon
import org.deathdric.ultimatecatbattle.ui.name
import org.deathdric.ultimatecatbattle.ui.toolkit.ElementSizeMode
import org.deathdric.ultimatecatbattle.ui.toolkit.HelpIcon
import org.deathdric.ultimatecatbattle.ui.toolkit.ImageButton
import org.deathdric.ultimatecatbattle.ui.toolkit.MessageText
import org.deathdric.ultimatecatbattle.ui.toolkit.TitleText
import org.deathdric.ultimatecatbattle.vm.PowerAllocationState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleUiState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

enum class PowerSelectScreenIconArrangeMode {
    ARROW_KEYS,
    SINGLE_LINE,
    TWO_LINES
}

data class PowerSelectScreenDisplayOptions (
    val helpIconSize: Dp,
    val playerIconSize: Dp,
    val playerIconPadding: Dp,
    val iconArrangeMode: PowerSelectScreenIconArrangeMode,
    val playerColumnWeight: Float,
    val titleSizeMode: ElementSizeMode,
    val buttonSizeMode: ElementSizeMode,
    val horizontalCharacterPaddingValue: Dp,
    val verticalCharacterPaddingValue: Dp,
    val displayNames: Boolean
)

fun ScreenConstraints.getPowerScreenDisplayOptions() : PowerSelectScreenDisplayOptions {
    var iconArrangeMode = PowerSelectScreenIconArrangeMode.SINGLE_LINE;
    if (this.maxHeight >= 500.dp) {
        iconArrangeMode = PowerSelectScreenIconArrangeMode.TWO_LINES;
    } else if (maxWidth < 750.dp) {
        iconArrangeMode = PowerSelectScreenIconArrangeMode.ARROW_KEYS;
    }

    val titleSizeMode = if (this.maxHeight < 480.dp) {
        ElementSizeMode.NORMAL
    } else if (this.maxHeight < 700.dp) {
        ElementSizeMode.LARGE
    } else {
        ElementSizeMode.VERY_LARGE
    }

    val menuIconSize = if (maxHeight < 500.dp) {
        50.dp
    } else if (maxWidth < 800.dp) {
        55.dp
    } else if (maxWidth < 950.dp) {
        60.dp
    } else {
        80.dp
    }

    val buttonSizeMode = if (this.maxHeight < 400.dp) {
        ElementSizeMode.NORMAL
    } else if (this.maxHeight < 650.dp) {
        ElementSizeMode.LARGE
    } else {
        ElementSizeMode.VERY_LARGE
    }

    return PowerSelectScreenDisplayOptions(
        helpIconSize = menuIconSize,
        playerIconSize = if(this.maxHeight < 500.dp) 50.dp else 75.dp,
        playerIconPadding = if(this.maxWidth < 700.dp) 2.dp else if(this.maxWidth < 900.dp || iconArrangeMode == PowerSelectScreenIconArrangeMode.TWO_LINES) 4.dp else 6.dp,
        iconArrangeMode = iconArrangeMode,
        playerColumnWeight =  if(this.maxWidth < 700.dp) 1.5f else 2f,
        titleSizeMode = titleSizeMode,
        buttonSizeMode = buttonSizeMode,
        horizontalCharacterPaddingValue = if(this.maxWidth < 800.dp) 4.dp else 8.dp,
        verticalCharacterPaddingValue = if (this.maxHeight >= 550.dp) 8.dp else 4.dp,
        displayNames = this.maxHeight >= 630.dp
    )
}

@Composable
fun PowerSelectScreen(
    screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
    uiState: UltimateCatBattleUiState, modifier: Modifier = Modifier
) {
    val screenDisplayOptions = screenConstraints.getPowerScreenDisplayOptions()
    Column(modifier = modifier.background(colorResource(id = R.color.title_background_color))) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .border(1.dp, Color.Black)
                .fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                HelpIcon(
                    onClick = { viewModel.toggleContextHelp(true) },
                    size = screenDisplayOptions.helpIconSize,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 4.dp,
                        bottom = 4.dp
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
            ) {
                TitleText(
                    text = stringResource(id = R.string.power_assign_title),
                    sizeMode = screenDisplayOptions.titleSizeMode
                )
            }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                PowerAllocationPanel(
                    ultimateCatBattleViewModel = viewModel,
                    screenDisplayOptions = screenDisplayOptions,
                    playerIndex = 0,
                    powerAllocationState = uiState.powerAllocationState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            start = screenDisplayOptions.horizontalCharacterPaddingValue,
                            top = screenDisplayOptions.verticalCharacterPaddingValue
                        )
                        .background(Color.White)
                        .border(1.dp, Color.Black)
                        .padding(4.dp)
                )
                PowerAllocationPanel(
                    ultimateCatBattleViewModel = viewModel,
                    screenDisplayOptions = screenDisplayOptions,
                    playerIndex = 2,
                    powerAllocationState = uiState.powerAllocationState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            start = screenDisplayOptions.horizontalCharacterPaddingValue,
                            top = screenDisplayOptions.verticalCharacterPaddingValue
                        )
                        .background(Color.White)
                        .border(1.dp, Color.Black)
                        .padding(4.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                PowerAllocationPanel(
                    ultimateCatBattleViewModel = viewModel,
                    screenDisplayOptions = screenDisplayOptions,
                    playerIndex = 1,
                    powerAllocationState = uiState.powerAllocationState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            start = screenDisplayOptions.horizontalCharacterPaddingValue,
                            top = screenDisplayOptions.verticalCharacterPaddingValue,
                            end = screenDisplayOptions.horizontalCharacterPaddingValue
                        )
                        .background(Color.White)
                        .border(1.dp, Color.Black)
                        .padding(4.dp)
                )
                PowerAllocationPanel(
                    ultimateCatBattleViewModel = viewModel,
                    screenDisplayOptions = screenDisplayOptions,
                    playerIndex = 3,
                    powerAllocationState = uiState.powerAllocationState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            start = screenDisplayOptions.horizontalCharacterPaddingValue,
                            top = screenDisplayOptions.verticalCharacterPaddingValue,
                            end = screenDisplayOptions.horizontalCharacterPaddingValue
                        )
                        .background(Color.White)
                        .border(1.dp, Color.Black)
                        .padding(4.dp)
                )
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = screenDisplayOptions.verticalCharacterPaddingValue / 2,
                bottom = screenDisplayOptions.verticalCharacterPaddingValue / 2
            ), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            val validation = uiState.teamAllocationState.validation
            if (!validation.validated) {
                val errorMessage = stringResource(id = R.string.must_have_more_than_one_team)
                MessageText(text = errorMessage, color = Color.Red, modifier = Modifier
                    .border(1.dp, Color.Black)
                    .background(Color.White)
                    .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp))
            } else {
                MessageText(text = "", modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp))
            }

        }
        Row(
            modifier = Modifier
                .background(Color.White)
                .border(1.dp, Color.Black)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ImageButton(
                onClick = { viewModel.cancelPowerAllocation() },
                text = stringResource(id = R.string.cancel),
                image = painterResource(
                    id = R.drawable.cancel
                ),
                sizeMode = screenDisplayOptions.buttonSizeMode,
                modifier = Modifier.weight(1f)
            )
            ImageButton(
                onClick = { viewModel.startTeamSelection() },
                text = stringResource(id = R.string.confirm),
                image = painterResource(
                    id = R.drawable.confirm,
                ),
                sizeMode = screenDisplayOptions.buttonSizeMode,
                enabled = uiState.teamAllocationState.validation.validated,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun AvailablePowerImage(imageSize: Dp, imagePadding: Dp, playerType: PlayerType, power: Power,
                        powerAllocationState: PowerAllocationState,
                        onClick: () -> Unit) {
    var background = Color.Transparent
    var alpha = 0.6f
    val powerLevel = powerAllocationState.players[playerType] ?: Power.NORMAL
    if (powerLevel == power) {
        background = colorResource(id = R.color.selected_icon_background)
        alpha = 1f
    }
    Image(painter = painterResource(id = power.icon()),
        contentDescription = stringResource(id = power.name()),
        modifier = Modifier
            .background(background)
            .padding(imagePadding)
            .alpha(alpha)
            .size(imageSize)
            .clickable {
                onClick()
            })
}

@Composable
fun PowerAllocationPanel(powerAllocationState: PowerAllocationState,
                         playerIndex: Int,
                         ultimateCatBattleViewModel: UltimateCatBattleViewModel,
                         screenDisplayOptions: PowerSelectScreenDisplayOptions,
                         modifier: Modifier = Modifier) {
    val playerType = powerAllocationState.findAtIndex(playerIndex)
    val availableLevels = Power.entries
    val panelBackground = if (playerType != null) Color.Transparent else Color(0xFFE0E0E0)
    Row(modifier = modifier
        .background(panelBackground)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        if (playerType != null) {
            val powerLevel = powerAllocationState.players[playerType] ?: Power.NORMAL
            Column(modifier = Modifier
                .weight(1f)
                .background(colorResource(id = R.color.panel_second_color))
                .padding(2.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = playerType.icon()),
                        contentDescription = stringResource(id = playerType.name()),
                        modifier = Modifier
                            .padding(start = if (screenDisplayOptions.iconArrangeMode == PowerSelectScreenIconArrangeMode.TWO_LINES) 0.dp else 4.dp)
                            .size(screenDisplayOptions.playerIconSize)
                    )
                }
                if (screenDisplayOptions.displayNames) {
                    val messageText = stringResource(id = playerType.name())


                    MessageText(text = messageText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp), sizeMode = ElementSizeMode.NORMAL, textAlign = TextAlign.Center)
                }
            }
            Column(modifier = Modifier
                .weight(screenDisplayOptions.playerColumnWeight)
                .fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly) {
                when(screenDisplayOptions.iconArrangeMode) {
                    PowerSelectScreenIconArrangeMode.ARROW_KEYS -> {

                        var teamIndex = availableLevels.indexOf(powerLevel);
                        if (teamIndex < 0) {
                            teamIndex = 0;
                        }
                        val previousIndex = (teamIndex - 1 + availableLevels.size) % availableLevels.size;
                        val nextIndex = (teamIndex + 1) % availableLevels.size;
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                            if (availableLevels.size > 1 ) {
                                Image(painter = painterResource(id = R.drawable.arrow_left),
                                    contentDescription = "<",
                                    modifier = Modifier
                                        .padding(screenDisplayOptions.playerIconPadding)
                                        .size(screenDisplayOptions.playerIconSize)
                                        .clickable {
                                            ultimateCatBattleViewModel.selectPowerAllocation(
                                                playerType,
                                                availableLevels[previousIndex]
                                            );
                                        })
                            } else {
                                Spacer(modifier = Modifier.size(screenDisplayOptions.playerIconSize + screenDisplayOptions.playerIconPadding))
                            }
                            Image(painter = painterResource(id = powerLevel.icon()),
                                contentDescription = stringResource(id = powerLevel.name()),
                                modifier = Modifier
                                    .padding(screenDisplayOptions.playerIconPadding)
                                    .size(screenDisplayOptions.playerIconSize)
                            )
                            if (availableLevels.size > 1 ) {
                                Image(painter = painterResource(id = R.drawable.arrow_right),
                                    contentDescription = ">",
                                    modifier = Modifier
                                        .padding(screenDisplayOptions.playerIconPadding)
                                        .size(screenDisplayOptions.playerIconSize)
                                        .clickable {
                                            ultimateCatBattleViewModel.selectPowerAllocation(
                                                playerType,
                                                availableLevels[nextIndex]
                                            );
                                        })
                            } else {
                                Spacer(modifier = Modifier.size(screenDisplayOptions.playerIconSize + screenDisplayOptions.playerIconPadding))
                            }
                        }
                    }
                    PowerSelectScreenIconArrangeMode.SINGLE_LINE -> {
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {

                            for (availableLevel in availableLevels) {
                                AvailablePowerImage(
                                    imageSize = screenDisplayOptions.playerIconSize,
                                    imagePadding = screenDisplayOptions.playerIconPadding,
                                    playerType = playerType,
                                    power = availableLevel,
                                    powerAllocationState = powerAllocationState,
                                    onClick = {
                                        ultimateCatBattleViewModel.selectPowerAllocation(
                                            playerType,
                                            availableLevel
                                        )
                                    }
                                )
                            }
                        }
                    }
                    PowerSelectScreenIconArrangeMode.TWO_LINES -> {
                        val levelsPerRow = (availableLevels.size + 1) / 2
                        Row(horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()) {
                            for (powerIndex in 0 until levelsPerRow) {
                                if (availableLevels.size > powerIndex) {
                                    val availablePower = availableLevels[powerIndex]
                                    AvailablePowerImage (
                                        imageSize = screenDisplayOptions.playerIconSize,
                                        imagePadding = screenDisplayOptions.playerIconPadding,
                                        power = availablePower,
                                        powerAllocationState = powerAllocationState,
                                        playerType = playerType,
                                        onClick = {
                                            ultimateCatBattleViewModel.selectPowerAllocation(
                                                playerType,
                                                availablePower
                                            )
                                        }
                                    )
                                } else {
                                    Spacer(modifier = Modifier.size(screenDisplayOptions.playerIconSize + screenDisplayOptions.playerIconPadding))
                                }
                            }
                        }
                        if (availableLevels.size > 1) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            ) {
                                for (powerIndex in levelsPerRow until 2 * levelsPerRow) {
                                    if (availableLevels.size > powerIndex) {
                                        val availablePower = availableLevels[powerIndex]
                                        AvailablePowerImage (
                                            imageSize = screenDisplayOptions.playerIconSize,
                                            imagePadding = screenDisplayOptions.playerIconPadding,
                                            power = availablePower,
                                            powerAllocationState = powerAllocationState,
                                            playerType = playerType,
                                            onClick = {
                                                ultimateCatBattleViewModel.selectPowerAllocation(
                                                    playerType,
                                                    availablePower
                                                )
                                            }
                                        )
                                    } else {
                                        Spacer(modifier = Modifier.size(screenDisplayOptions.playerIconSize + screenDisplayOptions.playerIconPadding))
                                    }
                                }
                            }
                        }
                        if (screenDisplayOptions.displayNames) {
                            MessageText(text = stringResource(powerLevel.name()),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp), sizeMode = ElementSizeMode.NORMAL, textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }

}