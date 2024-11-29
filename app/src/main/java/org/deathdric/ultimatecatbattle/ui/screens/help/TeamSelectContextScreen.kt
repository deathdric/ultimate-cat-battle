package org.deathdric.ultimatecatbattle.ui.screens.help

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.captionBarPadding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.PlayerId
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.screens.PlayerSelectScreenIconArrangeMode
import org.deathdric.ultimatecatbattle.ui.screens.TeamSelectScreenDisplayOptions
import org.deathdric.ultimatecatbattle.ui.screens.TeamSelectScreenIconArrangeMode
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

@Composable
fun TeamSelectContextScreen(helpContextScreenDisplayOptions: HelpContextScreenDisplayOptions,
                            teamSelectScreenDisplayOptions: TeamSelectScreenDisplayOptions,
                            modifier: Modifier = Modifier
) {
    Column (modifier = modifier) {
        StandardText(text = stringResource(id = R.string.help_team_select_screen_paragraph_1), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        StandardText(text = stringResource(id = R.string.help_team_select_screen_paragraph_2), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        StandardText(text = stringResource(id = R.string.help_team_select_screen_paragraph_3), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        StandardText(text = stringResource(id = R.string.help_team_select_screen_paragraph_4), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        StandardText(text = stringResource(id = R.string.help_team_select_screen_paragraph_5), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        Row (modifier = Modifier.padding(4.dp)) {
            Column (Modifier.weight(1f)) {
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.ball_green), contentDescription = null, modifier = Modifier
                        .size(teamSelectScreenDisplayOptions.teamIconSize)
                        .padding(teamSelectScreenDisplayOptions.teamIconPadding))
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(text = stringResource(id = R.string.ball_green), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }
            Column (modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.fish_blue), contentDescription = null, modifier = Modifier
                        .size(teamSelectScreenDisplayOptions.teamIconSize)
                        .padding(teamSelectScreenDisplayOptions.teamIconPadding))
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(text = stringResource(id = R.string.fish_blue), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }
        }
        Row (modifier = Modifier.padding(4.dp)) {
            Column (Modifier.weight(1f)) {
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.carrot_yellow), contentDescription = null, modifier = Modifier
                        .size(teamSelectScreenDisplayOptions.teamIconSize)
                        .padding(teamSelectScreenDisplayOptions.teamIconPadding))
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(text = stringResource(id = R.string.carrot_yellow), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }
            Column (modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.cheese_red), contentDescription = null, modifier = Modifier
                        .size(teamSelectScreenDisplayOptions.teamIconSize)
                        .padding(teamSelectScreenDisplayOptions.teamIconPadding))
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(text = stringResource(id = R.string.cheese_red), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }
        }
        StandardText(text = stringResource(id = R.string.help_team_select_screen_paragraph_6), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        Row (modifier = Modifier.padding(4.dp)) {
            Column (Modifier.weight(1f)) {
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.star_purple), contentDescription = null, modifier = Modifier
                        .size(teamSelectScreenDisplayOptions.teamIconSize)
                        .padding(teamSelectScreenDisplayOptions.teamIconPadding))
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(text = stringResource(id = R.string.star_purple), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }
            Column (modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.moon_brown), contentDescription = null, modifier = Modifier
                        .size(teamSelectScreenDisplayOptions.teamIconSize)
                        .padding(teamSelectScreenDisplayOptions.teamIconPadding))
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(text = stringResource(id = R.string.moon_brown), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }
        }
        Row (modifier = Modifier.padding(4.dp)) {
            Column (Modifier.weight(1f)) {
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.sun_orange), contentDescription = null, modifier = Modifier
                        .size(teamSelectScreenDisplayOptions.teamIconSize)
                        .padding(teamSelectScreenDisplayOptions.teamIconPadding))
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(text = stringResource(id = R.string.sun_orange), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }
            Column (modifier = Modifier.weight(1f)) {
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
        StandardText(text = stringResource(id = R.string.help_team_select_screen_paragraph_7), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        if (teamSelectScreenDisplayOptions.iconArrangeMode == TeamSelectScreenIconArrangeMode.ARROW_KEYS) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = null,
                    modifier = Modifier
                        .size(teamSelectScreenDisplayOptions.teamIconSize)
                        .padding(teamSelectScreenDisplayOptions.teamIconPadding)
                )
                Image(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = null,
                    modifier = Modifier
                        .size(teamSelectScreenDisplayOptions.teamIconSize)
                        .padding(teamSelectScreenDisplayOptions.teamIconPadding)
                )
                Spacer(modifier = Modifier.size(16.dp))
                StandardText(
                    text = stringResource(id = R.string.help_team_select_screen_paragraph_9),
                    fontWeight = FontWeight.Bold,
                    fontSize = helpContextScreenDisplayOptions.textSize
                )
            }
        }
        else {
            StandardText(
                text = stringResource(id = R.string.help_team_select_screen_paragraph_8),
                fontWeight = FontWeight.Bold,
                fontSize = helpContextScreenDisplayOptions.textSize
            )
        }

        StandardText(text = stringResource(id = R.string.help_team_select_screen_paragraph_10), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))

    }
}

@Composable
fun GenericTeamSelectContextScreenPreview(screenConstraints: ScreenConstraints) {
    val viewModel = UltimateCatBattleViewModel()
    viewModel.startPlayerSelection(2)
    viewModel.toggleCharacter(PlayerId.RABBIT, true)
    viewModel.startTeamSelection()
    val uiState = viewModel.uiState.collectAsState().value
    HelpContextScreen(
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
fun TeamSelectContextScreenPreviewVerySmall() {
    GenericTeamSelectContextScreenPreview(screenConstraints = ScreenConstraints(560.dp, 300.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=740dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectContextScreenPreviewSmallUnderLimit() {
    GenericTeamSelectContextScreenPreview(screenConstraints = ScreenConstraints(740.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=750dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectContextScreenPreviewMediumWidthOverLimit() {
    GenericTeamSelectContextScreenPreview(screenConstraints = ScreenConstraints(750.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectContextScreenPreviewMediumWidthHigherPadding() {
    GenericTeamSelectContextScreenPreview(screenConstraints = ScreenConstraints(800.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=900dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectContextScreenPreviewLargeWidth() {
    GenericTeamSelectContextScreenPreview(screenConstraints = ScreenConstraints(900.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=700dp,height=500dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectContextScreenPreviewMediumWidthMediumHeight() {
    GenericTeamSelectContextScreenPreview(screenConstraints = ScreenConstraints(700.dp, 500.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=500dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectContextScreenPreviewMediumWidthMediumHeightMaxPadding() {
    GenericTeamSelectContextScreenPreview(screenConstraints = ScreenConstraints(800.dp, 500.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=630dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectContextScreenPreviewMediumWidthLargeHeight() {
    GenericTeamSelectContextScreenPreview(screenConstraints = ScreenConstraints(800.dp, 630.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=1280dp,height=701dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun TeamSelectContextScreenPreviewLargeSize() {
    GenericTeamSelectContextScreenPreview(screenConstraints = ScreenConstraints(1280.dp, 701.dp))
}