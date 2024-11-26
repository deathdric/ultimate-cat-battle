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
import org.deathdric.ultimatecatbattle.model.TeamId
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.aliveIcon
import org.deathdric.ultimatecatbattle.ui.icon
import org.deathdric.ultimatecatbattle.ui.name
import org.deathdric.ultimatecatbattle.ui.toolkit.ElementSizeMode
import org.deathdric.ultimatecatbattle.ui.toolkit.HelpIcon
import org.deathdric.ultimatecatbattle.ui.toolkit.ImageButton
import org.deathdric.ultimatecatbattle.ui.toolkit.MessageText
import org.deathdric.ultimatecatbattle.ui.toolkit.TitleText
import org.deathdric.ultimatecatbattle.vm.GlobalTeamAllocationState
import org.deathdric.ultimatecatbattle.vm.TeamAllocationState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleUiState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

enum class TeamSelectScreenIconArrangeMode {
    ARROW_KEYS,
    SINGLE_LINE,
    TWO_LINES
}

data class TeamSelectScreenDisplayOptions (
    val helpIconSize: Dp,
    val teamIconSize: Dp,
    val teamIconPadding: Dp,
    val iconArrangeMode: TeamSelectScreenIconArrangeMode,
    val teamColumnWeight: Float,
    val titleSizeMode: ElementSizeMode,
    val buttonSizeMode: ElementSizeMode,
    val horizontalCharacterPaddingValue: Dp,
    val verticalCharacterPaddingValue: Dp,
    val displayNames: Boolean
)

fun ScreenConstraints.getTeamScreenDisplayOptions() : TeamSelectScreenDisplayOptions {
    var iconArrangeMode = TeamSelectScreenIconArrangeMode.SINGLE_LINE;
    if (this.maxHeight >= 500.dp) {
        iconArrangeMode = TeamSelectScreenIconArrangeMode.TWO_LINES;
    } else if (maxWidth < 750.dp) {
        iconArrangeMode = TeamSelectScreenIconArrangeMode.ARROW_KEYS;
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

    return TeamSelectScreenDisplayOptions(
        helpIconSize = menuIconSize,
        teamIconSize = if(this.maxHeight < 500.dp) 50.dp else 75.dp,
        teamIconPadding = if(this.maxWidth < 700.dp) 2.dp else if(this.maxWidth < 900.dp  || iconArrangeMode == TeamSelectScreenIconArrangeMode.TWO_LINES) 4.dp else 6.dp,
        iconArrangeMode = iconArrangeMode,
        teamColumnWeight =  if(this.maxWidth < 700.dp) 1.5f else 2f,
        titleSizeMode = titleSizeMode,
        buttonSizeMode = buttonSizeMode,
        horizontalCharacterPaddingValue = if(this.maxWidth < 800.dp) 4.dp else 8.dp,
        verticalCharacterPaddingValue = if (this.maxHeight >= 550.dp) 8.dp else 4.dp,
        displayNames = this.maxHeight >= 630.dp
    )
}

@Composable
fun TeamSelectScreen(
    screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
    uiState: UltimateCatBattleUiState, modifier: Modifier = Modifier
) {
    val screenDisplayOptions = screenConstraints.getTeamScreenDisplayOptions()
    val maxTeamCount = uiState.teamAllocationState.teams.stream().map { it.availableTeams.size }.max { a, b -> a.compareTo(b) }.orElse(1)
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
                    text = stringResource(id = R.string.team_assign_title),
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
                TeamAllocationPanel(
                    globalTeamAllocationState = uiState.teamAllocationState,
                    ultimateCatBattleViewModel = viewModel,
                    screenDisplayOptions = screenDisplayOptions,
                    playerIndex = 0,
                    maxTeamCount = maxTeamCount,
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
                TeamAllocationPanel(
                    globalTeamAllocationState = uiState.teamAllocationState,
                    ultimateCatBattleViewModel = viewModel,
                    screenDisplayOptions = screenDisplayOptions,
                    playerIndex = 2,
                    maxTeamCount = maxTeamCount,
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
                TeamAllocationPanel(
                    globalTeamAllocationState = uiState.teamAllocationState,
                    ultimateCatBattleViewModel = viewModel,
                    screenDisplayOptions = screenDisplayOptions,
                    playerIndex = 1,
                    maxTeamCount = maxTeamCount,
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
                TeamAllocationPanel(
                    globalTeamAllocationState = uiState.teamAllocationState,
                    ultimateCatBattleViewModel = viewModel,
                    screenDisplayOptions = screenDisplayOptions,
                    playerIndex = 3,
                    maxTeamCount = maxTeamCount,
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
                onClick = { viewModel.cancelTeamAllocation() },
                text = stringResource(id = R.string.cancel),
                image = painterResource(
                    id = R.drawable.cancel
                ),
                sizeMode = screenDisplayOptions.buttonSizeMode,
                modifier = Modifier.weight(1f)
            )
            ImageButton(
                onClick = { viewModel.startGame() },
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
fun AvailableTeamImage(imageSize: Dp, imagePadding: Dp, availableTeam: TeamId,
                         teamAllocationState: TeamAllocationState,
                         onClick: () -> Unit) {
    var background = Color.Transparent
    if (teamAllocationState.teamAllocation.teamId == availableTeam) {
        background = colorResource(id = R.color.selected_icon_background)
    }
    Image(painter = painterResource(id = availableTeam.icon()),
        contentDescription = stringResource(id = availableTeam.name()),
        modifier = Modifier
            .background(background)
            .padding(imagePadding)
            .size(imageSize)
            .clickable {
                onClick()
            })
}

@Composable
fun TeamAllocationPanel(globalTeamAllocationState: GlobalTeamAllocationState,
                        playerIndex: Int,
                        maxTeamCount: Int,
                        ultimateCatBattleViewModel: UltimateCatBattleViewModel,
                        screenDisplayOptions: TeamSelectScreenDisplayOptions,
                        modifier: Modifier = Modifier) {
    var teamAllocationState: TeamAllocationState? = null
    if (playerIndex < globalTeamAllocationState.teams.size) {
        teamAllocationState = globalTeamAllocationState.teams[playerIndex]
    }
    val panelBackground = if (teamAllocationState != null) Color.Transparent else Color(0xFFE0E0E0)
    Row(modifier = modifier
        .background(panelBackground)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        if (teamAllocationState != null) {
            val playerKey = teamAllocationState.teamAllocation.playerKey
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
                        painter = painterResource(id = playerKey.playerType.icon()),
                        contentDescription = stringResource(id = playerKey.playerType.name()),
                        modifier = Modifier
                            .padding(start = if (screenDisplayOptions.iconArrangeMode == TeamSelectScreenIconArrangeMode.TWO_LINES) 0.dp else 4.dp)
                            .size(screenDisplayOptions.teamIconSize)
                    )
                    if (playerKey.playerId != null && screenDisplayOptions.iconArrangeMode != TeamSelectScreenIconArrangeMode.TWO_LINES) {
                        Image(
                            painter = painterResource(id = playerKey.playerId.aliveIcon()),
                            contentDescription = stringResource(id = playerKey.playerId.name()),
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(screenDisplayOptions.teamIconSize)
                        )
                    }
                }
                if (playerKey.playerId != null && screenDisplayOptions.iconArrangeMode == TeamSelectScreenIconArrangeMode.TWO_LINES) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            painter = painterResource(id = playerKey.playerId.aliveIcon()),
                            contentDescription = stringResource(id = playerKey.playerId.name()),
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(screenDisplayOptions.teamIconSize)
                        )
                    }
                }
                if (screenDisplayOptions.displayNames) {
                    val messageText = if (playerKey.playerId != null) "%s (%s)".format(
                        stringResource(id = playerKey.playerType.name()), stringResource(id = playerKey.playerId.name())) else stringResource(
                        id = playerKey.playerType.name())


                    MessageText(text = messageText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp), sizeMode = ElementSizeMode.NORMAL, textAlign = TextAlign.Center)
                }
            }
            Column(modifier = Modifier
                .weight(screenDisplayOptions.teamColumnWeight)
                .fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly) {
                when(screenDisplayOptions.iconArrangeMode) {
                    TeamSelectScreenIconArrangeMode.ARROW_KEYS -> {
                        var teamIndex = teamAllocationState.availableTeams.indexOf(teamAllocationState.teamAllocation.teamId);
                        if (teamIndex < 0) {
                            teamIndex = 0;
                        }
                        val previousIndex = (teamIndex - 1 + teamAllocationState.availableTeams.size) % teamAllocationState.availableTeams.size;
                        val nextIndex = (teamIndex + 1) % teamAllocationState.availableTeams.size;
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                            if (teamAllocationState.availableTeams.size > 1 ) {
                                Image(painter = painterResource(id = R.drawable.arrow_left),
                                    contentDescription = "<",
                                    modifier = Modifier
                                        .padding(screenDisplayOptions.teamIconPadding)
                                        .size(screenDisplayOptions.teamIconSize)
                                        .clickable {
                                            ultimateCatBattleViewModel.selectTeamAllocation(
                                                playerKey,
                                                teamAllocationState.availableTeams[previousIndex]
                                            );
                                        })
                            } else {
                                Spacer(modifier = Modifier.size(screenDisplayOptions.teamIconSize + screenDisplayOptions.teamIconPadding))
                            }
                            Image(painter = painterResource(id = teamAllocationState.teamAllocation.teamId.icon()),
                                contentDescription = stringResource(id = teamAllocationState.teamAllocation.teamId.name()),
                                modifier = Modifier
                                    .padding(screenDisplayOptions.teamIconPadding)
                                    .size(screenDisplayOptions.teamIconSize)
                            )
                            if (teamAllocationState.availableTeams.size > 1 ) {
                            Image(painter = painterResource(id = R.drawable.arrow_right),
                                contentDescription = ">",
                                modifier = Modifier
                                    .padding(screenDisplayOptions.teamIconPadding)
                                    .size(screenDisplayOptions.teamIconSize)
                                    .clickable {
                                        ultimateCatBattleViewModel.selectTeamAllocation(
                                            playerKey,
                                            teamAllocationState.availableTeams[nextIndex]
                                        );
                                    })
                            } else {
                                Spacer(modifier = Modifier.size(screenDisplayOptions.teamIconSize + screenDisplayOptions.teamIconPadding))
                            }
                        }
                    }
                    TeamSelectScreenIconArrangeMode.SINGLE_LINE -> {
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {

                            for (availableTeam in teamAllocationState.availableTeams) {
                                AvailableTeamImage(
                                    imageSize = screenDisplayOptions.teamIconSize,
                                    imagePadding = screenDisplayOptions.teamIconPadding,
                                    availableTeam = availableTeam,
                                    teamAllocationState = teamAllocationState,
                                    onClick = {
                                        ultimateCatBattleViewModel.selectTeamAllocation(
                                            playerKey,
                                            availableTeam
                                        )
                                    }
                                )
                            }
                            for (index in teamAllocationState.availableTeams.size..<maxTeamCount) {
                                Spacer(modifier = Modifier.size(screenDisplayOptions.teamIconSize + screenDisplayOptions.teamIconPadding))
                            }
                        }
                    }
                    TeamSelectScreenIconArrangeMode.TWO_LINES -> {
                        val teamsPerRow = (teamAllocationState.availableTeams.size + 1) / 2
                        Row(horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()) {
                            for (teamIndex in 0 until teamsPerRow) {
                                if (teamAllocationState.availableTeams.size > teamIndex) {
                                    val availableTeam =
                                        teamAllocationState.availableTeams[teamIndex]
                                    AvailableTeamImage(
                                        imageSize = screenDisplayOptions.teamIconSize,
                                        imagePadding = screenDisplayOptions.teamIconPadding,
                                        availableTeam = availableTeam,
                                        teamAllocationState = teamAllocationState,
                                        onClick = {
                                            ultimateCatBattleViewModel.selectTeamAllocation(
                                                playerKey,
                                                availableTeam
                                            )
                                        }
                                    )
                                } else {
                                    Spacer(modifier = Modifier.size(screenDisplayOptions.teamIconSize + screenDisplayOptions.teamIconPadding))
                                }
                            }
                        }
                        if (teamAllocationState.availableTeams.size > 1) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            ) {
                                for (teamIndex in teamsPerRow until 2 * teamsPerRow) {
                                    if (teamAllocationState.availableTeams.size > teamIndex) {
                                        val availableTeam =
                                            teamAllocationState.availableTeams[teamIndex]
                                        AvailableTeamImage(
                                            imageSize = screenDisplayOptions.teamIconSize,
                                            imagePadding = screenDisplayOptions.teamIconPadding,
                                            availableTeam = availableTeam,
                                            teamAllocationState = teamAllocationState,
                                            onClick = {
                                                ultimateCatBattleViewModel.selectTeamAllocation(
                                                    playerKey,
                                                    availableTeam
                                                )
                                            }
                                        )
                                    } else {
                                        Spacer(modifier = Modifier.size(screenDisplayOptions.teamIconSize + screenDisplayOptions.teamIconPadding))
                                    }
                                }
                            }
                        }
                        if (screenDisplayOptions.displayNames) {
                            MessageText(text = stringResource(id = R.string.team_name_pattern).format(stringResource(id = teamAllocationState.teamAllocation.teamId.name())),
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

@Composable
fun GenericTeamSelectScreenPreview(screenConstraints: ScreenConstraints) {
    val viewModel = UltimateCatBattleViewModel()
    viewModel.startPlayerSelection(2)
    viewModel.toggleCharacter(PlayerId.RABBIT, true)
    viewModel.startTeamSelection()
    val uiState = viewModel.uiState.collectAsState().value
    TeamSelectScreen(
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
fun TeamSelectScreenPreviewVerySmall() {
    GenericTeamSelectScreenPreview(screenConstraints = ScreenConstraints(560.dp, 300.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=740dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectScreenPreviewSmallUnderLimit() {
    GenericTeamSelectScreenPreview(screenConstraints = ScreenConstraints(740.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=750dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectScreenPreviewMediumWidthOverLimit() {
    GenericTeamSelectScreenPreview(screenConstraints = ScreenConstraints(750.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectScreenPreviewMediumWidthHigherPadding() {
    GenericTeamSelectScreenPreview(screenConstraints = ScreenConstraints(800.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=900dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectScreenPreviewLargeWidth() {
    GenericTeamSelectScreenPreview(screenConstraints = ScreenConstraints(900.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=700dp,height=500dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectScreenPreviewMediumWidthMediumHeight() {
    GenericTeamSelectScreenPreview(screenConstraints = ScreenConstraints(700.dp, 500.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=500dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectScreenPreviewMediumWidthMediumHeightMaxPadding() {
    GenericTeamSelectScreenPreview(screenConstraints = ScreenConstraints(800.dp, 500.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=630dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectScreenPreviewMediumWidthLargeHeight() {
    GenericTeamSelectScreenPreview(screenConstraints = ScreenConstraints(800.dp, 630.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=1280dp,height=720dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectScreenPreviewLargeSize() {
    GenericTeamSelectScreenPreview(screenConstraints = ScreenConstraints(1280.dp, 720.dp))
}