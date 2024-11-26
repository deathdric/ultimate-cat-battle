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
import org.deathdric.ultimatecatbattle.model.PlayerType
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.screens.PlayerSelectScreenDisplayOptions
import org.deathdric.ultimatecatbattle.ui.screens.PlayerSelectScreenIconArrangeMode
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

@Composable
fun PlayerSelectContextScreen(helpContextScreenDisplayOptions: HelpContextScreenDisplayOptions,
                              playerSelectScreenDisplayOptions: PlayerSelectScreenDisplayOptions,
                              modifier: Modifier = Modifier
) {
    Column (modifier = modifier) {
        StandardText(text = stringResource(id = R.string.help_player_select_screen_paragraph_1), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        StandardText(text = stringResource(id = R.string.help_player_select_screen_paragraph_2), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        StandardText(text = stringResource(id = R.string.help_player_select_screen_paragraph_3), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        StandardText(text = stringResource(id = R.string.help_player_select_screen_paragraph_4), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        Row (modifier = Modifier.padding(4.dp)) {
            Column (Modifier.weight(1f)) {
                Row (verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.cat2), contentDescription = null, modifier = Modifier
                    .size(playerSelectScreenDisplayOptions.playerIconSize)
                    .padding(playerSelectScreenDisplayOptions.playerIconPadding))
                Spacer(modifier = Modifier.size(16.dp))
                StandardText(text = stringResource(id = R.string.cat), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }
            Column (modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.penguin1), contentDescription = null, modifier = Modifier
                        .size(playerSelectScreenDisplayOptions.playerIconSize)
                        .padding(playerSelectScreenDisplayOptions.playerIconPadding))
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(text = stringResource(id = R.string.penguin), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }

        }
        Row (modifier = Modifier.padding(4.dp)) {
            Column (modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.rabbit), contentDescription = null, modifier = Modifier
                        .size(playerSelectScreenDisplayOptions.playerIconSize)
                        .padding(playerSelectScreenDisplayOptions.playerIconPadding))
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(text = stringResource(id = R.string.rabbit), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }
            Column (modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.mouse), contentDescription = null, modifier = Modifier
                        .size(playerSelectScreenDisplayOptions.playerIconSize)
                        .padding(playerSelectScreenDisplayOptions.playerIconPadding))
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(text = stringResource(id = R.string.mouse), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }
        }
        StandardText(text = stringResource(id = R.string.help_player_select_screen_paragraph_5), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        Row (modifier = Modifier.padding(4.dp)) {
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.player1),
                        contentDescription = null,
                        modifier = Modifier
                            .size(playerSelectScreenDisplayOptions.playerIconSize)
                            .padding(playerSelectScreenDisplayOptions.playerIconPadding)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(
                        text = stringResource(id = R.string.player1),
                        fontWeight = FontWeight.Bold,
                        fontSize = helpContextScreenDisplayOptions.textSize
                    )
                }
            }
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.player2),
                        contentDescription = null,
                        modifier = Modifier
                            .size(playerSelectScreenDisplayOptions.playerIconSize)
                            .padding(playerSelectScreenDisplayOptions.playerIconPadding)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(
                        text = stringResource(id = R.string.player2),
                        fontWeight = FontWeight.Bold,
                        fontSize = helpContextScreenDisplayOptions.textSize
                    )
                }
            }
        }
        Row (modifier = Modifier.padding(4.dp)) {
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.player3),
                        contentDescription = null,
                        modifier = Modifier
                            .size(playerSelectScreenDisplayOptions.playerIconSize)
                            .padding(playerSelectScreenDisplayOptions.playerIconPadding)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(
                        text = stringResource(id = R.string.player3),
                        fontWeight = FontWeight.Bold,
                        fontSize = helpContextScreenDisplayOptions.textSize
                    )
                }
            }
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.player4),
                        contentDescription = null,
                        modifier = Modifier
                            .size(playerSelectScreenDisplayOptions.playerIconSize)
                            .padding(playerSelectScreenDisplayOptions.playerIconPadding)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(
                        text = stringResource(id = R.string.player4),
                        fontWeight = FontWeight.Bold,
                        fontSize = helpContextScreenDisplayOptions.textSize
                    )
                }
            }
        }
        Row (modifier = Modifier.padding(4.dp)) {
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.computer),
                        contentDescription = null,
                        modifier = Modifier
                            .size(playerSelectScreenDisplayOptions.playerIconSize)
                            .padding(playerSelectScreenDisplayOptions.playerIconPadding)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(
                        text = stringResource(id = R.string.computer),
                        fontWeight = FontWeight.Bold,
                        fontSize = helpContextScreenDisplayOptions.textSize
                    )
                }
            }
            Column(Modifier.weight(1f)) {
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
        StandardText(text = stringResource(id = R.string.help_player_select_screen_paragraph_6), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        if (playerSelectScreenDisplayOptions.iconArrangeMode == PlayerSelectScreenIconArrangeMode.ARROW_KEYS) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = null,
                    modifier = Modifier
                        .size(playerSelectScreenDisplayOptions.playerIconSize)
                        .padding(playerSelectScreenDisplayOptions.playerIconPadding)
                )
                Image(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = null,
                    modifier = Modifier
                        .size(playerSelectScreenDisplayOptions.playerIconSize)
                        .padding(playerSelectScreenDisplayOptions.playerIconPadding)
                )
                Spacer(modifier = Modifier.size(16.dp))
                StandardText(
                    text = stringResource(id = R.string.help_player_select_screen_paragraph_8),
                    fontWeight = FontWeight.Bold,
                    fontSize = helpContextScreenDisplayOptions.textSize
                )
            }
        }
        else {
            StandardText(
                text = stringResource(id = R.string.help_player_select_screen_paragraph_7),
                fontWeight = FontWeight.Bold,
                fontSize = helpContextScreenDisplayOptions.textSize
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = null,
                modifier = Modifier
                    .size(playerSelectScreenDisplayOptions.playerIconSize)
                    .padding(playerSelectScreenDisplayOptions.playerIconPadding)
            )
            Spacer(modifier = Modifier.size(16.dp))
            StandardText(
                text = stringResource(id = R.string.help_player_select_screen_paragraph_9),
                fontWeight = FontWeight.Bold,
                fontSize = helpContextScreenDisplayOptions.textSize
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.cancel),
                contentDescription = null,
                modifier = Modifier
                    .size(playerSelectScreenDisplayOptions.playerIconSize)
                    .padding(playerSelectScreenDisplayOptions.playerIconPadding)
            )
            Spacer(modifier = Modifier.size(16.dp))
            StandardText(
                text = stringResource(id = R.string.help_player_select_screen_paragraph_10),
                fontWeight = FontWeight.Bold,
                fontSize = helpContextScreenDisplayOptions.textSize
            )
        }
        StandardText(text = stringResource(id = R.string.help_player_select_screen_paragraph_11), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
    }
}

@Composable
fun GenericPlayerSelectContextScreenPreview(screenConstraints: ScreenConstraints) {
    val viewModel = UltimateCatBattleViewModel()
    viewModel.startPlayerSelection(3)
    viewModel.toggleCharacter(PlayerId.MOUSE, true)
    viewModel.selectPlayerAllocation(PlayerId.CAT, PlayerType.COMPUTER)
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
fun PlayerSelectContextScreenPreviewVerySmall() {
    GenericPlayerSelectContextScreenPreview(screenConstraints = ScreenConstraints(560.dp, 300.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=740dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectContextScreenPreviewSmallUnderLimit() {
    GenericPlayerSelectContextScreenPreview(screenConstraints = ScreenConstraints(740.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=750dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectContextScreenPreviewMediumWidthOverLimit() {
    GenericPlayerSelectContextScreenPreview(screenConstraints = ScreenConstraints(750.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectContextScreenPreviewMediumWidthHigherPadding() {
    GenericPlayerSelectContextScreenPreview(screenConstraints = ScreenConstraints(800.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=900dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectContextScreenPreviewLargeWidth() {
    GenericPlayerSelectContextScreenPreview(screenConstraints = ScreenConstraints(900.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=700dp,height=500dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectContextScreenPreviewMediumWidthMediumHeight() {
    GenericPlayerSelectContextScreenPreview(screenConstraints = ScreenConstraints(700.dp, 500.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=500dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectContextScreenPreviewMediumWidthMediumHeightMaxPadding() {
    GenericPlayerSelectContextScreenPreview(screenConstraints = ScreenConstraints(800.dp, 500.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=630dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectContextScreenPreviewMediumWidthLargeHeight() {
    GenericPlayerSelectContextScreenPreview(screenConstraints = ScreenConstraints(800.dp, 630.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=1280dp,height=701dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PlayerSelectContextScreenPreviewLargeSize() {
    GenericPlayerSelectContextScreenPreview(screenConstraints = ScreenConstraints(1280.dp, 701.dp))
}