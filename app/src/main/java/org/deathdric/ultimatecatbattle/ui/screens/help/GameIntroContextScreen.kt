package org.deathdric.ultimatecatbattle.ui.screens.help

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.ui.toolkit.StandardText

@Composable
fun GameIntroContextScreen(helpContextScreenDisplayOptions: HelpContextScreenDisplayOptions,
                           modifier: Modifier = Modifier) {
    Column (modifier = modifier) {
        StandardText(text = stringResource(id = R.string.help_game_intro_screen_paragraph_1), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        StandardText(text = stringResource(id = R.string.help_game_intro_screen_paragraph_2), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
        StandardText(text = stringResource(id = R.string.help_game_intro_screen_paragraph_3), fontSize = helpContextScreenDisplayOptions.textSize, lineHeight = helpContextScreenDisplayOptions.lineHeight, modifier = Modifier.padding(4.dp))
    }
}