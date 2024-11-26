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
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.PlayerId
import org.deathdric.ultimatecatbattle.model.PlayerType
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.aliveIcon
import org.deathdric.ultimatecatbattle.ui.icon
import org.deathdric.ultimatecatbattle.ui.name
import org.deathdric.ultimatecatbattle.ui.toolkit.ElementSizeMode
import org.deathdric.ultimatecatbattle.ui.toolkit.HelpIcon
import org.deathdric.ultimatecatbattle.ui.toolkit.ImageButton
import org.deathdric.ultimatecatbattle.ui.toolkit.MessageText
import org.deathdric.ultimatecatbattle.ui.toolkit.TitleText
import org.deathdric.ultimatecatbattle.vm.CharacterAllocation
import org.deathdric.ultimatecatbattle.vm.CharacterAllocationState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleUiState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

enum class PlayerSelectScreenIconArrangeMode {
    ARROW_KEYS,
    SINGLE_LINE,
    TWO_LINES
}

data class PlayerSelectScreenDisplayOptions (
    val helpIconSize: Dp,
    val playerIconSize: Dp,
    val playerIconPadding: Dp,
    val iconArrangeMode: PlayerSelectScreenIconArrangeMode,
    val playerColumnWeight: Float,
    val titleSizeMode: ElementSizeMode,
    val buttonSizeMode: ElementSizeMode,
    val horizontalCharacterPaddingValue: Dp,
    val verticalCharacterPaddingValue: Dp,
    val displayNames: Boolean
)

fun ScreenConstraints.getPlayerScreenDisplayOptions() : PlayerSelectScreenDisplayOptions {
    var iconArrangeMode = PlayerSelectScreenIconArrangeMode.SINGLE_LINE;
    if (this.maxHeight >= 500.dp) {
        iconArrangeMode = PlayerSelectScreenIconArrangeMode.TWO_LINES;
    } else if (maxWidth < 750.dp) {
        iconArrangeMode = PlayerSelectScreenIconArrangeMode.ARROW_KEYS;
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
    
    return PlayerSelectScreenDisplayOptions(
        helpIconSize = menuIconSize,
        playerIconSize = if(this.maxHeight < 500.dp) 50.dp else 75.dp,
        playerIconPadding = if(this.maxWidth < 700.dp) 2.dp else if(this.maxWidth < 900.dp || iconArrangeMode == PlayerSelectScreenIconArrangeMode.TWO_LINES) 4.dp else 6.dp,
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
fun PlayerSelectScreen(
    screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
    uiState: UltimateCatBattleUiState, modifier: Modifier = Modifier
) {
    val screenDisplayOptions = screenConstraints.getPlayerScreenDisplayOptions();
    Column(modifier = modifier.background(colorResource(id = R.color.title_background_color))) {
        Box (modifier = Modifier
            .background(Color.White)
            .border(1.dp, Color.Black)
            .fillMaxWidth()){
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
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)) {
                TitleText(text = stringResource(id = R.string.character_assign_title), sizeMode = screenDisplayOptions.titleSizeMode)
            }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                CharacterAllocationPanel(
                    characterAllocationState = uiState.characterAllocationState.characters[PlayerId.CAT]!!,
                    ultimateCatBattleViewModel = viewModel,
                    screenDisplayOptions = screenDisplayOptions,
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
                CharacterAllocationPanel(
                    characterAllocationState = uiState.characterAllocationState.characters[PlayerId.PENGUIN]!!,
                    ultimateCatBattleViewModel = viewModel,
                    screenDisplayOptions = screenDisplayOptions,
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
                CharacterAllocationPanel(
                    characterAllocationState = uiState.characterAllocationState.characters[PlayerId.RABBIT]!!,
                    ultimateCatBattleViewModel = viewModel,
                    screenDisplayOptions = screenDisplayOptions,
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
                CharacterAllocationPanel(
                    characterAllocationState = uiState.characterAllocationState.characters[PlayerId.MOUSE]!!,
                    ultimateCatBattleViewModel = viewModel,
                    screenDisplayOptions = screenDisplayOptions,
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
            val validation = uiState.characterAllocationState.validation
            if (!validation.validated && validation.missingPlayerCharacters != null) {
                val messageTemplate = stringResource(id = R.string.player_must_have_characters)
                val errorMessage = messageTemplate.format(stringResource(id = validation.missingPlayerCharacters.name()))
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
                onClick = { viewModel.resetToMainScreen() },
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
                enabled = uiState.characterAllocationState.validation.validated,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun AvailablePlayerImage(imageSize: Dp, imagePadding: Dp, availablePlayer: PlayerType,
                          characterAllocationState: CharacterAllocationState,
                          onClick: () -> Unit) {
    var background = Color.Transparent
    if (characterAllocationState.characterAllocation.enable && characterAllocationState.characterAllocation.playerType == availablePlayer) {
        background = colorResource(id = R.color.selected_icon_background)
    }
    Image(painter = painterResource(id = availablePlayer.icon()),
        contentDescription = stringResource(id = availablePlayer.name()),
        modifier = Modifier
            .background(background)
            .padding(imagePadding)
            .size(imageSize)
            .clickable {
                onClick()
            })
}

@Composable
fun CharacterAllocationPanel(
    characterAllocationState: CharacterAllocationState,
    ultimateCatBattleViewModel: UltimateCatBattleViewModel,
    screenDisplayOptions: PlayerSelectScreenDisplayOptions,
    modifier: Modifier = Modifier
) {
    val playerId = characterAllocationState.characterAllocation.playerId
    val panelBackground = if (characterAllocationState.characterAllocation.enable) Color.Transparent else Color(0xFFE0E0E0)
    Row(modifier = modifier
        .background(panelBackground)
        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        if (characterAllocationState.characterAllocation.enable) {
            Column(modifier = Modifier
                .weight(1f)
                .background(colorResource(id = R.color.panel_second_color))
                .padding(2.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                    Image(
                        painter = painterResource(id = playerId.aliveIcon()),
                        contentDescription = stringResource(id = playerId.name()),
                        modifier = Modifier
                            .padding(start = if (screenDisplayOptions.iconArrangeMode == PlayerSelectScreenIconArrangeMode.TWO_LINES) 0.dp else 4.dp)
                            .size(screenDisplayOptions.playerIconSize)
                    )
                    if (characterAllocationState.canDisable && screenDisplayOptions.iconArrangeMode != PlayerSelectScreenIconArrangeMode.TWO_LINES) {
                        Image(
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = stringResource(id = R.string.remove),
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(screenDisplayOptions.playerIconSize)
                                .clickable {
                                    ultimateCatBattleViewModel.toggleCharacter(
                                        playerId,
                                        false
                                    )
                                }
                        )
                    }
                }

                if (screenDisplayOptions.iconArrangeMode == PlayerSelectScreenIconArrangeMode.TWO_LINES
                    && characterAllocationState.canDisable) {
                    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = stringResource(id = R.string.remove),
                            modifier = Modifier
                                .size(screenDisplayOptions.playerIconSize)
                                .clickable {
                                    ultimateCatBattleViewModel.toggleCharacter(
                                        playerId,
                                        false
                                    )
                                }
                        )
                    }
                }

                if (screenDisplayOptions.displayNames) {
                    MessageText(text = stringResource(id = characterAllocationState.characterAllocation.playerId.name()),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp), sizeMode = ElementSizeMode.NORMAL, textAlign = TextAlign.Center)
                }
            }
            Column(modifier = Modifier
                .weight(screenDisplayOptions.playerColumnWeight)
                .fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly) {
                when(screenDisplayOptions.iconArrangeMode) {
                    PlayerSelectScreenIconArrangeMode.ARROW_KEYS -> {
                        var playerIndex = characterAllocationState.availablePlayers.indexOf(characterAllocationState.characterAllocation.playerType);
                        if (playerIndex < 0) {
                            playerIndex = 0;
                        }
                        val previousIndex = (playerIndex - 1 + characterAllocationState.availablePlayers.size) % characterAllocationState.availablePlayers.size;
                        val nextIndex = (playerIndex + 1) % characterAllocationState.availablePlayers.size;
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                            Image(painter = painterResource(id = R.drawable.arrow_left), contentDescription = "<",
                                modifier = Modifier
                                    .padding(screenDisplayOptions.playerIconPadding)
                                    .size(screenDisplayOptions.playerIconSize)
                                    .clickable {
                                        ultimateCatBattleViewModel.selectPlayerAllocation(
                                            playerId,
                                            characterAllocationState.availablePlayers[previousIndex]
                                        );
                                    })
                            Image(painter = painterResource(id = characterAllocationState.characterAllocation.playerType.icon()),
                                contentDescription = stringResource(id = characterAllocationState.characterAllocation.playerType.name()),
                                modifier = Modifier
                                    .padding(screenDisplayOptions.playerIconPadding)
                                    .size(screenDisplayOptions.playerIconSize)
                                    )
                            Image(painter = painterResource(id = R.drawable.arrow_right),
                                contentDescription = ">",
                                modifier = Modifier
                                    .padding(screenDisplayOptions.playerIconPadding)
                                    .size(screenDisplayOptions.playerIconSize)
                                    .clickable {
                                        ultimateCatBattleViewModel.selectPlayerAllocation(
                                            playerId,
                                            characterAllocationState.availablePlayers[nextIndex]
                                        );
                                    })
                        }
                    }
                    PlayerSelectScreenIconArrangeMode.SINGLE_LINE -> {
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {

                            for (availablePlayer in characterAllocationState.availablePlayers) {
                                AvailablePlayerImage(
                                    imageSize = screenDisplayOptions.playerIconSize,
                                    imagePadding = screenDisplayOptions.playerIconPadding,
                                    availablePlayer = availablePlayer,
                                    characterAllocationState = characterAllocationState,
                                    onClick = {
                                        ultimateCatBattleViewModel.selectPlayerAllocation(
                                            playerId,
                                            availablePlayer
                                        )
                                    }
                                )
                            }
                        }
                    }
                    PlayerSelectScreenIconArrangeMode.TWO_LINES -> {
                        val playersPerRow = (characterAllocationState.availablePlayers.size + 1) / 2
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()) {
                            for (playerIndex in 0 until playersPerRow) {
                                val availablePlayer = characterAllocationState.availablePlayers[playerIndex]
                                AvailablePlayerImage(
                                    imageSize = screenDisplayOptions.playerIconSize,
                                    imagePadding = screenDisplayOptions.playerIconPadding,
                                    availablePlayer = availablePlayer,
                                    characterAllocationState = characterAllocationState,
                                    onClick = {
                                        ultimateCatBattleViewModel.selectPlayerAllocation(
                                            playerId,
                                            availablePlayer
                                        )
                                    }
                                )
                            }
                        }
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()) {
                            for (playerIndex in playersPerRow until 2 * playersPerRow) {
                                if (characterAllocationState.availablePlayers.size > playerIndex) {
                                    val availablePlayer = characterAllocationState.availablePlayers[playerIndex]
                                    AvailablePlayerImage(
                                        imageSize = screenDisplayOptions.playerIconSize,
                                        imagePadding = screenDisplayOptions.playerIconPadding,
                                        availablePlayer = availablePlayer,
                                        characterAllocationState = characterAllocationState,
                                        onClick = {
                                            ultimateCatBattleViewModel.selectPlayerAllocation(
                                                playerId,
                                                availablePlayer
                                            )
                                        }
                                    )
                                } else {
                                    Spacer(modifier = Modifier.size(screenDisplayOptions.playerIconSize + screenDisplayOptions.playerIconPadding))
                                }
                            }
                        }
                        if (screenDisplayOptions.displayNames) {
                            MessageText(text = stringResource(id = characterAllocationState.characterAllocation.playerType.name()),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp), sizeMode = ElementSizeMode.NORMAL, textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        } else {
            Image(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = stringResource(id = R.string.add),
                modifier = Modifier
                    .padding(screenDisplayOptions.playerIconPadding)
                    .size(screenDisplayOptions.playerIconSize)
                    .clickable { ultimateCatBattleViewModel.toggleCharacter(playerId, true) }
            )
        }
    }
}


@Composable
@Preview(
    showSystemUi = true,
    device = "spec:width=330dp,height=150dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun CharacterAllocationPanelPreviewSmall() {
    var allocationState = CharacterAllocationState(
        characterAllocation = CharacterAllocation(PlayerId.CAT, true, PlayerType.PLAYER1),
        canDisable = true,
        availablePlayers = listOf(
            PlayerType.PLAYER1,
            PlayerType.PLAYER2,
            PlayerType.PLAYER3,
            PlayerType.COMPUTER
        )
    )
    CharacterAllocationPanel(
        characterAllocationState = allocationState,
        ultimateCatBattleViewModel = UltimateCatBattleViewModel(),
        modifier = Modifier.background(Color.White),
        screenDisplayOptions = ScreenConstraints(580.dp, 300.dp).getPlayerScreenDisplayOptions()
    )
}

@Composable
@Preview(
    showSystemUi = true,
    device = "spec:width=400dp,height=180dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun CharacterAllocationPanelPreview() {
    var allocationState = CharacterAllocationState(
        characterAllocation = CharacterAllocation(PlayerId.CAT, true, PlayerType.PLAYER1),
        canDisable = true,
        availablePlayers = listOf(
            PlayerType.PLAYER1,
            PlayerType.PLAYER2,
            PlayerType.PLAYER3,
            PlayerType.COMPUTER
        )
    )
    CharacterAllocationPanel(
        characterAllocationState = allocationState,
        ultimateCatBattleViewModel = UltimateCatBattleViewModel(),
        modifier = Modifier.background(Color.White),
        screenDisplayOptions = ScreenConstraints(800.dp, 360.dp).getPlayerScreenDisplayOptions()
    )
}

@Composable
@Preview(
    showSystemUi = true,
    device = "spec:width=500dp,height=300dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun CharacterAllocationPanelPreviewLarge() {
    var allocationState = CharacterAllocationState(
        characterAllocation = CharacterAllocation(PlayerId.CAT, true, PlayerType.PLAYER1),
        canDisable = true,
        availablePlayers = listOf(
            PlayerType.PLAYER1,
            PlayerType.PLAYER2,
            PlayerType.PLAYER3,
            PlayerType.COMPUTER
        )
    )
    CharacterAllocationPanel(
        characterAllocationState = allocationState,
        ultimateCatBattleViewModel = UltimateCatBattleViewModel(),
        modifier = Modifier.background(Color.White),
        screenDisplayOptions = ScreenConstraints(1000.dp, 600.dp).getPlayerScreenDisplayOptions()
    )
}


@Composable
fun GenericPlayerSelectScreenPreview(screenConstraints: ScreenConstraints) {
    val viewModel = UltimateCatBattleViewModel()
    viewModel.startPlayerSelection(3)
    viewModel.toggleCharacter(PlayerId.MOUSE, true)
    viewModel.selectPlayerAllocation(PlayerId.CAT, PlayerType.COMPUTER)
    val uiState = viewModel.uiState.collectAsState().value
    PlayerSelectScreen(
        screenConstraints,
        UltimateCatBattleViewModel(),
        uiState,
        Modifier
            .safeContentPadding()
            .statusBarsPadding()
            .systemBarsPadding()
            .navigationBarsPadding()
            .captionBarPadding()
            .background(Color.White)
            .requiredSize(screenConstraints.maxWidth, screenConstraints.maxHeight)
    )
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=560dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectScreenPreviewVerySmall() {
    GenericPlayerSelectScreenPreview(screenConstraints = ScreenConstraints(560.dp, 300.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=740dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectScreenPreviewSmallUnderLimit() {
    GenericPlayerSelectScreenPreview(screenConstraints = ScreenConstraints(740.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=750dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectScreenPreviewMediumWidthOverLimit() {
    GenericPlayerSelectScreenPreview(screenConstraints = ScreenConstraints(750.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectScreenPreviewMediumWidthHigherPadding() {
    GenericPlayerSelectScreenPreview(screenConstraints = ScreenConstraints(800.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=900dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectScreenPreviewLargeWidth() {
    GenericPlayerSelectScreenPreview(screenConstraints = ScreenConstraints(900.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=700dp,height=500dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectScreenPreviewMediumWidthMediumHeight() {
    GenericPlayerSelectScreenPreview(screenConstraints = ScreenConstraints(700.dp, 500.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=500dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectScreenPreviewMediumWidthMediumHeightMaxPadding() {
    GenericPlayerSelectScreenPreview(screenConstraints = ScreenConstraints(800.dp, 500.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=630dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectScreenPreviewMediumWidthLargeHeight() {
    GenericPlayerSelectScreenPreview(screenConstraints = ScreenConstraints(800.dp, 630.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=1280dp,height=701dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectScreenPreviewLargeSize() {
    GenericPlayerSelectScreenPreview(screenConstraints = ScreenConstraints(1280.dp, 701.dp))
}