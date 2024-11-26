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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.PlayerId
import org.deathdric.ultimatecatbattle.model.PlayerType
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.screens.ActivePlayerPanelDisplayOptions
import org.deathdric.ultimatecatbattle.ui.screens.createPlayerInfoForPreview
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText
import org.deathdric.ultimatecatbattle.vm.GameStatus
import org.deathdric.ultimatecatbattle.vm.GameUiState
import org.deathdric.ultimatecatbattle.vm.RootUiStatus
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

@Composable
fun StartTurnContextScreen(helpContextScreenDisplayOptions: HelpContextScreenDisplayOptions,
                           activePlayerPanelDisplayOptions: ActivePlayerPanelDisplayOptions,
                           modifier: Modifier = Modifier
) {
    Column (modifier = modifier) {
        StandardText(text = stringResource(id = R.string.help_game_start_turn_screen_paragraph_1), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        StandardText(text = stringResource(id = R.string.help_game_start_turn_screen_paragraph_2), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        StandardText(text = stringResource(id = R.string.help_game_start_turn_screen_paragraph_3), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
            Image(painter = painterResource(id = R.drawable.hitpoints2), contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(activePlayerPanelDisplayOptions.iconSize))
            Spacer(modifier = Modifier.size(16.dp))
            StandardText(text = stringResource(id = R.string.help_stat_hit_points_description), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        }
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
            Image(painter = painterResource(id = R.drawable.clock), contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(activePlayerPanelDisplayOptions.iconSize))
            Spacer(modifier = Modifier.size(16.dp))
            StandardText(text = stringResource(id = R.string.help_stat_time_description), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        }
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
            Image(painter = painterResource(id = R.drawable.attack), contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(activePlayerPanelDisplayOptions.iconSize))
            Spacer(modifier = Modifier.size(16.dp))
            StandardText(text = stringResource(id = R.string.help_stat_attack_description), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        }
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
            Image(painter = painterResource(id = R.drawable.defense), contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(activePlayerPanelDisplayOptions.iconSize))
            Spacer(modifier = Modifier.size(16.dp))
            StandardText(text = stringResource(id = R.string.help_stat_defense_description), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        }
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
            Image(painter = painterResource(id = R.drawable.hit), contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(activePlayerPanelDisplayOptions.iconSize))
            Spacer(modifier = Modifier.size(16.dp))
            StandardText(text = stringResource(id = R.string.help_stat_hit_description), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        }
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
            Image(painter = painterResource(id = R.drawable.avoid2), contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(activePlayerPanelDisplayOptions.iconSize))
            Spacer(modifier = Modifier.size(16.dp))
            StandardText(text = stringResource(id = R.string.help_stat_avoid_description), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        }
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
            Image(painter = painterResource(id = R.drawable.critical), contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(activePlayerPanelDisplayOptions.iconSize))
            Spacer(modifier = Modifier.size(16.dp))
            StandardText(text = stringResource(id = R.string.help_stat_critical_description), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        }
        StandardText(text = stringResource(id = R.string.help_game_start_turn_screen_paragraph_4), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
            Image(painter = painterResource(id = R.drawable.hitpoints2), contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(activePlayerPanelDisplayOptions.iconSize))
            Spacer(modifier = Modifier.size(16.dp))
            StandardText(text = stringResource(id = R.string.help_stat_hit_points_description), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        }
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
            Image(painter = painterResource(id = R.drawable.confirm), contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(activePlayerPanelDisplayOptions.iconSize))
            Spacer(modifier = Modifier.size(16.dp))
            StandardText(text = stringResource(id = R.string.help_stat_active_description), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        }
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
            Image(painter = painterResource(id = R.drawable.hourglass), contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(activePlayerPanelDisplayOptions.iconSize))
            Spacer(modifier = Modifier.size(16.dp))
            StandardText(text = stringResource(id = R.string.help_stat_waiting_time_description), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        }
        StandardText(text = stringResource(id = R.string.help_game_start_turn_screen_paragraph_5), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))

    }
}

@Composable
fun GenericStartTurnContextScreenPreview(screenConstraints: ScreenConstraints) {
    val playerInfo = createPlayerInfoForPreview()
    val gameState = GameUiState(playerInfo = playerInfo, GameStatus.PLAYER_TURN)
    val viewModel = UltimateCatBattleViewModel()
    val uiState = viewModel.uiState.collectAsState().value.copy(
        rootUiStatus = RootUiStatus.MAIN_GAME,
        gameState = gameState
    )
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
fun StartTurnContextScreenPreviewVerySmall() {
    GenericStartTurnContextScreenPreview(screenConstraints = ScreenConstraints(560.dp, 300.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=740dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun StartTurnContextScreenPreviewSmallUnderLimit() {
    GenericStartTurnContextScreenPreview(screenConstraints = ScreenConstraints(740.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=750dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun StartTurnContextScreenPreviewMediumWidthOverLimit() {
    GenericStartTurnContextScreenPreview(screenConstraints = ScreenConstraints(750.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun StartTurnContextScreenPreviewMediumWidthHigherPadding() {
    GenericStartTurnContextScreenPreview(screenConstraints = ScreenConstraints(800.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=900dp,height=330dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun StartTurnContextScreenPreviewLargeWidth() {
    GenericStartTurnContextScreenPreview(screenConstraints = ScreenConstraints(900.dp, 330.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=700dp,height=500dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun StartTurnContextScreenPreviewMediumWidthMediumHeight() {
    GenericStartTurnContextScreenPreview(screenConstraints = ScreenConstraints(700.dp, 500.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=500dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun StartTurnContextScreenPreviewMediumWidthMediumHeightMaxPadding() {
    GenericStartTurnContextScreenPreview(screenConstraints = ScreenConstraints(800.dp, 500.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=800dp,height=630dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun StartTurnContextScreenPreviewMediumWidthLargeHeight() {
    GenericStartTurnContextScreenPreview(screenConstraints = ScreenConstraints(800.dp, 630.dp))
}

@Composable
@Preview(
    showSystemUi = false,
    device = "spec:width=1280dp,height=701dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun StartTurnContextScreenPreviewLargeSize() {
    GenericStartTurnContextScreenPreview(screenConstraints = ScreenConstraints(1280.dp, 701.dp))
}