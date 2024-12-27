package org.deathdric.ultimatecatbattle.ui.screens.help

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.ui.screens.PlayerSelectScreenIconArrangeMode
import org.deathdric.ultimatecatbattle.ui.screens.PowerSelectScreenDisplayOptions
import org.deathdric.ultimatecatbattle.ui.screens.PowerSelectScreenIconArrangeMode
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText

@Composable
fun PowerSelectContextScreen(helpContextScreenDisplayOptions: HelpContextScreenDisplayOptions,
                             powerSelectScreenDisplayOptions: PowerSelectScreenDisplayOptions,
                             modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        StandardText(text = stringResource(id = R.string.help_power_select_screen_paragraph_1), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        StandardText(text = stringResource(id = R.string.help_power_select_screen_paragraph_2), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        StandardText(text = stringResource(id = R.string.help_power_select_screen_paragraph_3), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        Row (modifier = Modifier.padding(4.dp)) {
            Column (Modifier.weight(1f)) {
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.easy), contentDescription = null, modifier = Modifier
                        .size(powerSelectScreenDisplayOptions.playerIconSize)
                        .padding(powerSelectScreenDisplayOptions.playerIconPadding))
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(text = stringResource(id = R.string.power_weak), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }
            Column (modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.medium), contentDescription = null, modifier = Modifier
                        .size(powerSelectScreenDisplayOptions.playerIconSize)
                        .padding(powerSelectScreenDisplayOptions.playerIconPadding))
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(text = stringResource(id = R.string.power_medium), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }

        }
        Row (modifier = Modifier.padding(4.dp)) {
            Column (modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.hard), contentDescription = null, modifier = Modifier
                        .size(powerSelectScreenDisplayOptions.playerIconSize)
                        .padding(powerSelectScreenDisplayOptions.playerIconPadding))
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(text = stringResource(id = R.string.power_strong), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }
            Column (modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.very_hard), contentDescription = null, modifier = Modifier
                        .size(powerSelectScreenDisplayOptions.playerIconSize)
                        .padding(powerSelectScreenDisplayOptions.playerIconPadding))
                    Spacer(modifier = Modifier.size(16.dp))
                    StandardText(text = stringResource(id = R.string.power_very_strong), fontWeight = FontWeight.Bold, fontSize = helpContextScreenDisplayOptions.textSize)
                }
            }
        }
        StandardText(text = stringResource(id = R.string.help_power_select_screen_paragraph_4), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        if (powerSelectScreenDisplayOptions.iconArrangeMode == PowerSelectScreenIconArrangeMode.ARROW_KEYS) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = null,
                    modifier = Modifier
                        .size(powerSelectScreenDisplayOptions.playerIconSize)
                        .padding(powerSelectScreenDisplayOptions.playerIconPadding)
                )
                Image(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = null,
                    modifier = Modifier
                        .size(powerSelectScreenDisplayOptions.playerIconSize)
                        .padding(powerSelectScreenDisplayOptions.playerIconPadding)
                )
                Spacer(modifier = Modifier.size(16.dp))
                StandardText(
                    text = stringResource(id = R.string.help_power_select_screen_paragraph_6),
                    fontWeight = FontWeight.Bold,
                    fontSize = helpContextScreenDisplayOptions.textSize
                )
            }
        }
        else {
            StandardText(
                text = stringResource(id = R.string.help_power_select_screen_paragraph_5),
                fontWeight = FontWeight.Bold,
                fontSize = helpContextScreenDisplayOptions.textSize
            )
        }
        StandardText(text = stringResource(id = R.string.help_power_select_screen_paragraph_7), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))

    }
}