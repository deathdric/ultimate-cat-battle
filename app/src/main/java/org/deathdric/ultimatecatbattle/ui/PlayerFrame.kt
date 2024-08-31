package org.deathdric.ultimatecatbattle.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R

@Composable
fun StatDisplay(
    statValue: Int,
    @DrawableRes
    statIcon: Int,
    modifier: Modifier = Modifier
) {
    val statColor = if (statValue > 0) Color(0xFF008000)
    else if (statValue < 0) Color.Red
    else Color.Black

    val statWeight = if (statValue > 25 || statValue < -25) FontWeight.Bold
    else FontWeight.Normal

    Row (modifier = modifier
        .padding(end = 4.dp)
        .size(width = 96.dp, height = 28.dp), horizontalArrangement = Arrangement.Start) {
        Image(
            painter = painterResource(id = statIcon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(24.dp)
        )
        Text (
            text = "${statValue}",
            textAlign = TextAlign.Right,
            color = statColor,
            fontWeight = statWeight,
            modifier = Modifier
                .size(width = 48.dp, height = 24.dp)
                .padding(end = 16.dp)
        )

    }
}

@Composable
fun PlayerFrame(isGameOver: Boolean, player: PlayerStatus, modifier: Modifier = Modifier) {
    val avatarBackgroundColor = if (player.active) Color(0xFFFFFF80) else Color(0xFFC0C0C0);
    val statBackgroundColor = if (player.active) Color(0xFFFFFFF8) else Color.White
    val healthColor = when(player.healthState) {
        HealthState.OK -> Color(0xFF008000)
        HealthState.WARNING -> Color(0xFF906000)
        HealthState.CRITICAL -> Color.Red
    }
    Column (modifier = modifier
        .background(Color.Black)
        .fillMaxHeight(),
        horizontalAlignment = Alignment.End) {
        Box (modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = player.icon),
                contentDescription = stringResource(id = player.name),
                modifier = Modifier
                    .size(100.dp)
                    .background(color = avatarBackgroundColor)
            )
        }
        Column (modifier = Modifier
            .padding(2.dp)
            .background(statBackgroundColor)
            .fillMaxHeight()) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hitpoints2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    modifier = Modifier.size(width = 64.dp, height = 30.dp),
                    text = "${player.hitPoints}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = healthColor
                )

            }
            if (isGameOver) {
                Row(horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top) {
                    val finalPlayerIcon = if (player.alive) R.drawable.trophy else R.drawable.white_flag
                    Image(painter = painterResource(id = finalPlayerIcon), contentDescription = null,
                        modifier = Modifier.size(100.dp))
                }
            } else {
                StatDisplay(statValue = player.attack, R.drawable.attack)
                StatDisplay(statValue = player.defense, statIcon = R.drawable.defense)
                StatDisplay(statValue = player.hit, statIcon = R.drawable.hit)
                StatDisplay(statValue = player.avoid, statIcon = R.drawable.avoid2)
                StatDisplay(statValue = player.critical, statIcon = R.drawable.critical)
            }

        }
    }
}

@Composable
@Preview
fun PlayerFrameActivePreview() {
    val playerStatus = PlayerStatus(
        name = R.string.cat,
        icon = R.drawable.cat2,
        active = true,
        healthState = HealthState.OK,
        attack = -10,
        defense = -30,
        avoid = 30,
        hit = 10,
        critical = 0,
        alive = true,
        hitPoints = 200
        )
    PlayerFrame(isGameOver = false, player = playerStatus)
}

@Composable
@Preview
fun PlayerFrameInactivePreview() {
    val playerStatus = PlayerStatus(
        name = R.string.cat,
        icon = R.drawable.cat2,
        active = false,
        healthState = HealthState.OK,
        attack = -10,
        defense = -30,
        avoid = 30,
        hit = 10,
        critical = 0,
        alive = true,
        hitPoints = 200
    )
    PlayerFrame(isGameOver = false, player = playerStatus)
}

@Composable
@Preview
fun PlayerFrameLoserPreview() {
    val playerStatus = PlayerStatus(
        name = R.string.cat,
        icon = R.drawable.cat_loss,
        active = false,
        healthState = HealthState.CRITICAL,
        attack = -10,
        defense = -30,
        avoid = 30,
        hit = 10,
        critical = 0,
        alive = false,
        hitPoints = 0
    )
    PlayerFrame(isGameOver = true, player = playerStatus)
}

@Composable
@Preview
fun PlayerFrameWinnerPreview() {
    val playerStatus = PlayerStatus(
        name = R.string.cat,
        icon = R.drawable.cat_loss,
        active = false,
        healthState = HealthState.CRITICAL,
        attack = -10,
        defense = -30,
        avoid = 30,
        hit = 10,
        critical = 0,
        alive = true,
        hitPoints = 50
    )
    PlayerFrame(isGameOver = true, player = playerStatus)
}