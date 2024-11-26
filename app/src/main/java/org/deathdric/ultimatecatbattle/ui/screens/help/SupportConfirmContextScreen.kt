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
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.ui.screens.ActivePlayerPanelDisplayOptions
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText

@Composable
fun SupportConfirmContextScreen(helpContextScreenDisplayOptions: HelpContextScreenDisplayOptions,
                               activePlayerPanelDisplayOptions: ActivePlayerPanelDisplayOptions,
                               modifier: Modifier = Modifier
) {
    Column (modifier = modifier) {
        StandardText(
            text = stringResource(id = R.string.help_game_support_confirm_screen_paragraph_1),
            fontSize = helpContextScreenDisplayOptions.textSize,
            lineHeight = helpContextScreenDisplayOptions.lineHeight,
            modifier = Modifier.padding(4.dp)
        )
        StandardText(
            text = stringResource(id = R.string.help_game_support_confirm_screen_paragraph_2),
            fontSize = helpContextScreenDisplayOptions.textSize,
            lineHeight = helpContextScreenDisplayOptions.lineHeight,
            modifier = Modifier.padding(4.dp)
        )
        StandardText(
            text = stringResource(id = R.string.help_game_support_confirm_screen_paragraph_3),
            fontSize = helpContextScreenDisplayOptions.textSize,
            lineHeight = helpContextScreenDisplayOptions.lineHeight,
            modifier = Modifier.padding(4.dp)
        )
        StandardText(
            text = stringResource(id = R.string.help_game_move_confirm_screen_icons),
            fontSize = helpContextScreenDisplayOptions.textSize,
            lineHeight = helpContextScreenDisplayOptions.lineHeight,
            modifier = Modifier.padding(4.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
            Image(
                painter = painterResource(id = R.drawable.clock), contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(activePlayerPanelDisplayOptions.iconSize)
            )
            Spacer(modifier = Modifier.size(16.dp))
            StandardText(
                text = stringResource(id = R.string.help_game_move_confirm_screen_time_cost),
                fontSize = helpContextScreenDisplayOptions.textSize,
                lineHeight = helpContextScreenDisplayOptions.lineHeight,
                modifier = Modifier.padding(4.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
            Image(
                painter = painterResource(id = R.drawable.cooldown), contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(activePlayerPanelDisplayOptions.iconSize)
            )
            Spacer(modifier = Modifier.size(16.dp))
            StandardText(
                text = stringResource(id = R.string.help_game_move_confirm_screen_cooldown),
                fontSize = helpContextScreenDisplayOptions.textSize,
                lineHeight = helpContextScreenDisplayOptions.lineHeight,
                modifier = Modifier.padding(4.dp)
            )
        }
        StandardText(
            text = stringResource(id = R.string.help_game_move_confirm_screen_action),
            fontSize = helpContextScreenDisplayOptions.textSize,
            lineHeight = helpContextScreenDisplayOptions.lineHeight,
            modifier = Modifier.padding(4.dp)
        )
    }
}