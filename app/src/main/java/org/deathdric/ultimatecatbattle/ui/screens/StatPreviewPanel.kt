package org.deathdric.ultimatecatbattle.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.StatusEffect
import org.deathdric.ultimatecatbattle.ui.icon
import org.deathdric.ultimatecatbattle.ui.name
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText
import kotlin.math.abs

data class StatPreviewDisplayOptions (
    val previewIconSize: Dp,
    val previewTextSize: TextUnit
)

@Composable
fun StatusDurationPreviewPanel(displayOptions: StatPreviewDisplayOptions, duration: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically) {
        StandardText(
            text = stringResource(id = R.string.duration),
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
            fontSize = displayOptions.previewTextSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.clock),
            contentDescription = null,
            modifier = modifier
                .padding(end = 4.dp)
                .size(displayOptions.previewIconSize)
        )
        StandardText(
            text = "%d".format(duration),
            modifier = Modifier.padding(end = 16.dp),
            fontSize = displayOptions.previewTextSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun StatusEffectPreviewPanel(displayOptions: StatPreviewDisplayOptions, statusEffect: StatusEffect, modifier: Modifier = Modifier) {
    Row (modifier = modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = statusEffect.effectType.icon()),
            contentDescription = null,
            modifier = Modifier.size(displayOptions.previewIconSize)
        )
        StandardText(
            text = stringResource(id = statusEffect.effectType.name()),
            modifier = Modifier.padding(start = 4.dp, end = 4.dp), fontSize = displayOptions.previewTextSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        if (statusEffect.effectValue > 0) {
            Image(
                painter = painterResource(id = R.drawable.stat_up),
                contentDescription = null,
                modifier = Modifier.size(displayOptions.previewIconSize)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.stat_down),
                contentDescription = null,
                modifier = Modifier.size(displayOptions.previewIconSize)
            )
        }
        val messageTextColor =
            if (statusEffect.effectValue > 0) colorResource(id = R.color.stat_bonus_color) else Color.Red
        val signValue = if (statusEffect.effectValue > 0) "+" else "-"

        StandardText(
            text = "%s %d %%".format(signValue, abs(statusEffect.effectValue)),
            color = messageTextColor,
            fontWeight = FontWeight.Bold,
            fontSize = displayOptions.previewTextSize,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}