package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R

@Composable
fun StartScreen(windowWidthSizeClass: WindowWidthSizeClass, windowHeightSizeClass: WindowHeightSizeClass, viewModel: UltimateCatBattleViewModel) {
    Column (modifier = Modifier
        .background(Color.White)
        .safeContentPadding()
        .statusBarsPadding()
        .systemBarsPadding()
        .navigationBarsPadding()
        .captionBarPadding()
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text (
            text = stringResource(id = R.string.main_title),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            fontSize = 36.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .border(width = 5.dp, color = Color.Red, shape = RoundedCornerShape(5.dp))
                .background(color = Color(0xFFFF8000))
                .padding(24.dp)
        )
        Row (modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            Image(painter = painterResource(id = R.drawable.cat2), contentDescription = null)
            Column (verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally){
                Button(
                    onClick = { viewModel.startGame(true)},
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(8.dp).size(width = 180.dp, height = 40.dp)
                ) {
                    ButtonText(text = stringResource(id = R.string.one_player), textAlign = TextAlign.Center)
                }
                Button(
                    onClick = { viewModel.startGame(false)},
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(8.dp).size(width = 180.dp, height = 40.dp)
                ) {
                    ButtonText(text = stringResource(id = R.string.two_players), textAlign = TextAlign.Center)
                }
            }

            Image(painter = painterResource(id = R.drawable.penguin1), contentDescription = null)
        }
    }
}

@Preview
@Composable
fun StartScreenPreview() {
    StartScreen(windowWidthSizeClass = WindowWidthSizeClass.Medium, windowHeightSizeClass = WindowHeightSizeClass.Compact, viewModel = UltimateCatBattleViewModel())
}