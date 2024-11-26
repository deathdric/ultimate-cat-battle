package org.deathdric.ultimatecatbattle.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.toolkit.ElementSizeMode
import org.deathdric.ultimatecatbattle.ui.toolkit.HelpIcon
import org.deathdric.ultimatecatbattle.ui.toolkit.SimpleButton
import org.deathdric.ultimatecatbattle.ui.toolkit.ToggledImage
import org.deathdric.ultimatecatbattle.ui.toolkit.screenModifier
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleUiState
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel


data class StartScreenDisplayOptions (
    val helpIconSize: Dp,
    val buttonSizeMode: ElementSizeMode,
    val mainPanelRatio: Float
)

fun ScreenConstraints.getStartScreenDisplayOptions() : StartScreenDisplayOptions {
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

    return StartScreenDisplayOptions (
        helpIconSize = menuIconSize,
        buttonSizeMode = buttonSizeMode,
        mainPanelRatio = if (this.maxWidth > 800.dp) 3f else 2f
    )
}

@Composable
fun StartScreen(screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel, uiState: UltimateCatBattleUiState, modifier: Modifier = Modifier) {
    val displayOptions = screenConstraints.getStartScreenDisplayOptions();
    Row (modifier = modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
        Column (modifier = Modifier
            .weight(1f)
            .border(1.dp, Color.Black)
            .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                HelpIcon(onClick = { viewModel.toggleContextHelp(true) }, size = displayOptions.helpIconSize, modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp))
            }
            Column(modifier = Modifier.fillMaxHeight().defaultMinSize(minWidth = 200.dp), verticalArrangement = Arrangement.SpaceEvenly) {
                SimpleButton(onClick = { viewModel.startPlayerSelection(1) }, sizeMode = displayOptions.buttonSizeMode, text = stringResource(id = R.string.one_player))
                SimpleButton(onClick = { viewModel.startPlayerSelection(2) }, sizeMode = displayOptions.buttonSizeMode, text = stringResource(id = R.string.two_players))
                SimpleButton(onClick = { viewModel.startPlayerSelection(3) }, sizeMode = displayOptions.buttonSizeMode, text = stringResource(id = R.string.three_players))
                SimpleButton(onClick = { viewModel.startPlayerSelection(4) }, sizeMode = displayOptions.buttonSizeMode, text = stringResource(id = R.string.four_players))
            }

        }
        Column (modifier = Modifier
            .fillMaxHeight()
            .weight(displayOptions.mainPanelRatio)
            .background(colorResource(id = R.color.title_background_color)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            Text (
                text = stringResource(id = R.string.main_title),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 36.sp,
                fontStyle = FontStyle.Italic,
                style = TextStyle(
                    shadow = Shadow(Color.Black, offset = Offset(-2f, 2f))
                ),
                color = Color.Yellow,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(24.dp)
            )
            Row (horizontalArrangement = Arrangement.Center) {
                ToggledImage(active = uiState.startScreenState.catClicked,
                    activeImageId = R.drawable.cat_loss, inactiveImageId = R.drawable.cat2,
                    modifier = Modifier.size(100.dp),
                    onClick = { viewModel.startupSwitchCat() })
                Image(painter = painterResource(id = R.drawable.damage), contentDescription = null,
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .size(100.dp))
                ToggledImage(active = uiState.startScreenState.penguinClicked,
                    activeImageId = R.drawable.penguin_loss, inactiveImageId = R.drawable.penguin1,
                    modifier = Modifier.size(100.dp),
                    onClick = { viewModel.startupSwitchPenguin() })
            }
            Row(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {
                Text(text = stringResource(id = R.string.copyright_notice),
                    color = Color.Black,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .background(Color.White)
                        .border(1.dp, Color.Black)
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true,
    device = "spec:width=800dp,height=400dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape")
fun StartScreenPreview() {
    val viewModel = UltimateCatBattleViewModel()
    val uiState = viewModel.uiState.collectAsState().value
    StartScreen(ScreenConstraints(720.dp, 360.dp), UltimateCatBattleViewModel(), uiState, Modifier.screenModifier())
}