package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.data.AttackRepository
import org.deathdric.ultimatecatbattle.data.PlayerRepository
import org.deathdric.ultimatecatbattle.data.SupportRepository
import org.deathdric.ultimatecatbattle.model.AttackSelectedInfo
import org.deathdric.ultimatecatbattle.model.AttackSimulationResult
import org.deathdric.ultimatecatbattle.model.NextTurnInfo
import org.deathdric.ultimatecatbattle.model.Player
import org.deathdric.ultimatecatbattle.model.SupportSelectedInfo
import org.deathdric.ultimatecatbattle.model.computeEffects
import kotlin.math.abs

@Composable
fun SupportConfirmationPanel(viewModel: UltimateCatBattleViewModel, supportSelectedInfo: SupportSelectedInfo, modifier: Modifier = Modifier) {

    Column (modifier = modifier
        .padding(4.dp)
        .background(Color.White)
        .border(1.dp, colorResource(id = R.color.support_border_color))
    ) {
        MessageText(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.support_container_color))
                .padding(4.dp),
            text = stringResource(id = supportSelectedInfo.supportAction.name),
            textAlign = TextAlign.Center,
        )
        Row (modifier = Modifier
            .border(1.dp, colorResource(id = R.color.support_border_color)),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column (modifier = Modifier
                .weight(1.2f)){
                for (supportEffect in supportSelectedInfo.effects) {
                    Row (modifier = Modifier.padding(4.dp)) {
                        Image(
                            painter = painterResource(id = supportEffect.statImage),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        MessageText(
                            text = stringResource(id = supportEffect.statName),
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        if (supportEffect.statModifier > 0) {
                            Image(
                                painter = painterResource(id = R.drawable.stat_up),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.stat_down),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        val messageTextColor = if (supportEffect.statModifier > 0) colorResource(id = R.color.stat_bonus_color) else Color.Red
                        val signValue = if (supportEffect.statModifier > 0) "+" else "-"

                        MessageText(text = "%s %d %%".format(signValue, abs(supportEffect.statModifier)), color = messageTextColor)
                    }
                }
            }
            Column (modifier = Modifier
                .weight(0.8f)
                .border(1.dp, colorResource(id = R.color.support_border_color)),
                horizontalAlignment = Alignment.CenterHorizontally) {
                MessageText(text = stringResource(id = R.string.support_move).toUpperCase(Locale.current),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .background(colorResource(id = R.color.support_border_color))
                        .fillMaxWidth())
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .border(1.dp, colorResource(id = R.color.support_border_color))
                        .padding(4.dp)) {
                    MessageText(text = stringResource(id = R.string.duration) + " :")
                    MessageText(text = supportSelectedInfo.supportAction.duration.toString(), modifier = Modifier.padding(start = 4.dp, end = 4.dp))
                    Image(painter = painterResource(id = R.drawable.clock), contentDescription = null,
                        modifier = Modifier.size(24.dp))
                }
                MessageText(text = stringResource(id = R.string.next_turn), modifier = Modifier.padding(start = 8.dp), textAlign = TextAlign.Center)
                Row (modifier = Modifier.padding(top = 4.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically){
                    Image(painter = painterResource(id = supportSelectedInfo.nextTurn.player.template.icon),
                        contentDescription = stringResource(id = supportSelectedInfo.nextTurn.player.template.name),
                        modifier = Modifier.size(36.dp))
                    Spacer(modifier = modifier.size(16.dp))
                    Image(painter = painterResource(id = R.drawable.clock), contentDescription = null,
                        modifier = Modifier.size(36.dp))
                    MessageText(text = supportSelectedInfo.nextTurn.time.toString(), modifier = Modifier.padding(start = 4.dp))
                }
            }
        }
        Row {
            ImageButton(onClick = { viewModel.cancelMoveSelection()}, text = stringResource(id = R.string.cancel_move), image = painterResource(
                id = R.drawable.cancel
            ), modifier = Modifier.weight(1f), containerColor = colorResource(id = R.color.support_border_color))
            ImageButton(onClick = { viewModel.chooseSupport(supportSelectedInfo.supportAction) }, text = stringResource(id = R.string.confirm_move), image = painterResource(
                id = R.drawable.confirm
            ), modifier = Modifier.weight(1f), containerColor = colorResource(id = R.color.support_border_color))
        }
    }
}

@Composable
@Preview
fun SupportConfirmationPanelPreview() {
    val player = Player(PlayerRepository.catPlayer)

    val supportSelectedInfo = SupportSelectedInfo (
        supportAction = SupportRepository.speed,
        effects = SupportRepository.speed.computeEffects(player),
        nextTurn = NextTurnInfo(Player(PlayerRepository.catPlayer), 15)
    )
    Row (modifier = Modifier.background(Color.Blue)){
        SupportConfirmationPanel(viewModel = UltimateCatBattleViewModel(), supportSelectedInfo = supportSelectedInfo)
    }
}