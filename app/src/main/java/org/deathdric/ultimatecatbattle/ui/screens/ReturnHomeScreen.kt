package org.deathdric.ultimatecatbattle.ui.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.ui.ScreenConstraints
import org.deathdric.ultimatecatbattle.ui.screens.help.toHelpContextScreenDisplayOptions
import org.deathdric.ultimatecatbattle.ui.toolkit.ImageButton
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText
import org.deathdric.ultimatecatbattle.ui.toolkit.TitleText
import org.deathdric.ultimatecatbattle.vm.UltimateCatBattleViewModel

@Composable
fun ReturnHomeScreen(
    screenConstraints: ScreenConstraints, viewModel: UltimateCatBattleViewModel,
    modifier: Modifier = Modifier
) {
    val displayOptions = screenConstraints.toHelpContextScreenDisplayOptions()
    Column(modifier = modifier.background(colorResource(id = R.color.title_background_color))) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(1f)) {
                Spacer(modifier = Modifier.fillMaxHeight())
            }
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(bottom = displayOptions.bottomPadding)
            ) {
                Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .border(1.dp, Color.Black)
                        .background(Color.White)
                        .padding(4.dp)
                        .fillMaxWidth()) {
                    TitleText(
                        text = stringResource(id = R.string.go_home),
                        sizeMode = displayOptions.titleSizeMode
                    )
                }
                Row (horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 4.dp)
                        .border(1.dp, Color.Black)
                        .background(Color.White)
                        .padding(4.dp)
                        .weight(1f)
                        .fillMaxWidth()) {
                    Column (modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center) {
                        StandardText(
                            text = stringResource(id = R.string.go_home_confirm),
                            fontSize = displayOptions.textSize,
                            fontWeight = FontWeight.Bold,
                            lineHeight = displayOptions.lineHeight,
                            modifier = Modifier.padding(4.dp)
                        )
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
                        onClick = { viewModel.toggleReturnHomeScreen(false) },
                        text = stringResource(id = R.string.cancel),
                        image = painterResource(
                            id = R.drawable.cancel
                        ),
                        sizeMode = displayOptions.buttonSizeMode,
                        modifier = Modifier.weight(1f)
                    )
                    ImageButton(
                        onClick = { viewModel.resetToMainScreen() },
                        text = stringResource(id = R.string.confirm),
                        image = painterResource(
                            id = R.drawable.confirm,
                        ),
                        sizeMode = displayOptions.buttonSizeMode,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Spacer(modifier = Modifier.fillMaxHeight())
            }
        }
    }
}