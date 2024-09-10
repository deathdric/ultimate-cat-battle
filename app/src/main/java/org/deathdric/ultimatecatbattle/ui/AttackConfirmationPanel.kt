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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.data.AttackRepository
import org.deathdric.ultimatecatbattle.data.PlayerRepository
import org.deathdric.ultimatecatbattle.model.AttackSelectedInfo
import org.deathdric.ultimatecatbattle.model.AttackSimulationResult
import org.deathdric.ultimatecatbattle.model.NextTurnInfo
import org.deathdric.ultimatecatbattle.model.Player

@Composable
fun AttackConfirmationPanel(viewModel: UltimateCatBattleViewModel, attackSelectedInfo: AttackSelectedInfo, modifier: Modifier = Modifier) {
    Column (modifier = modifier
        .padding(4.dp)
        .background(Color.White)
        .border(1.dp, colorResource(id = R.color.attack_border_color))
        ) {
        MessageText(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.attack_container_color))
                .padding(4.dp),
            text = stringResource(id = attackSelectedInfo.attackAction.name),
            textAlign = TextAlign.Center,
        )
        Row (modifier = Modifier
            .border(1.dp, colorResource(id = R.color.attack_border_color)),
            horizontalArrangement = Arrangement.SpaceBetween){
            Column (modifier = Modifier
                .weight(1.2f)
                .border(1.dp, colorResource(id = R.color.attack_border_color))){
                Row (modifier = Modifier.padding(4.dp)){
                    Image(painter = painterResource(id = R.drawable.damage), contentDescription = null, modifier = Modifier.size(24.dp))
                    MessageText(text = stringResource(id = R.string.min_damage),
                        modifier = Modifier.padding(start = 4.dp, end = 4.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    if (attackSelectedInfo.attackSimulationResult.minDamageEnough) {
                        Image(painter = painterResource(id = R.drawable.trophy), contentDescription = null,
                            modifier = Modifier.size(24.dp))
                    }
                    MessageText(text = attackSelectedInfo.attackSimulationResult.minDamage.toString(),
                        modifier = Modifier.padding(end = 16.dp))
                }
                Row (modifier = Modifier.padding(4.dp)){
                    Image(painter = painterResource(id = R.drawable.damage), contentDescription = null, modifier = Modifier.size(24.dp))
                    MessageText(text = stringResource(id = R.string.max_damage),
                        modifier = Modifier.padding(start = 4.dp, end = 4.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    if (attackSelectedInfo.attackSimulationResult.maxDamageEnough) {
                        Image(painter = painterResource(id = R.drawable.trophy), contentDescription = null,
                            modifier = Modifier.size(24.dp))
                    }
                    MessageText(text = attackSelectedInfo.attackSimulationResult.maxDamage.toString(),
                        modifier = Modifier.padding(end = 16.dp))
                }
                Row (modifier = Modifier.padding(4.dp)){
                    Image(painter = painterResource(id = R.drawable.hit_chance), contentDescription = null, modifier = Modifier.size(24.dp))
                    if (attackSelectedInfo.attackSimulationResult.successChance >= 100) {
                       MessageText(text = stringResource(id = R.string.success_guaranteed), color = colorResource(
                           id = R.color.stat_bonus_color
                       ), modifier = Modifier
                           .padding(start = 4.dp, end = 4.dp)
                           .weight(1f))
                    } else {
                        MessageText(
                            text = stringResource(id = R.string.chance_to_hit),
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        MessageText(text = "%d %%".format(attackSelectedInfo.attackSimulationResult.successChance))
                    }
                }
                Row (modifier = Modifier.padding(4.dp)){
                    Image(painter = painterResource(id = R.drawable.critical), contentDescription = null, modifier = Modifier.size(24.dp))
                    MessageText(text = stringResource(id = R.string.chance_to_crit),
                        modifier = Modifier.padding(start = 4.dp, end = 4.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    MessageText(text = "%d %%".format(attackSelectedInfo.attackSimulationResult.critChance))
                }
            }
            Column (modifier = Modifier
                .weight(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally) {
                MessageText(text = stringResource(id = R.string.attack_move).toUpperCase(Locale.current),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .background(colorResource(id = R.color.attack_border_color))
                        .fillMaxWidth())
                MessageText(text = stringResource(id = R.string.next_turn), modifier = Modifier.padding(start = 8.dp, top = 16.dp), textAlign = TextAlign.Center)
                Row (modifier = Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically){
                    Image(painter = painterResource(id = attackSelectedInfo.nextTurn.player.template.icon),
                        contentDescription = stringResource(id = attackSelectedInfo.nextTurn.player.template.name),
                        modifier = Modifier.size(36.dp))
                    Spacer(modifier = modifier.size(16.dp))
                    Image(painter = painterResource(id = R.drawable.clock), contentDescription = null,
                        modifier = Modifier.size(36.dp))
                    MessageText(text = attackSelectedInfo.nextTurn.time.toString(), modifier = Modifier.padding(start = 4.dp))
                }
            }
        }
        Row {
            ImageButton(onClick = { viewModel.cancelMoveSelection()}, text = stringResource(id = R.string.cancel_move), image = painterResource(
                id = R.drawable.cancel
            ), modifier = Modifier.weight(1f), containerColor = colorResource(id = R.color.attack_border_color))
            ImageButton(onClick = { viewModel.chooseAttack(attackSelectedInfo.attackAction) }, text = stringResource(id = R.string.confirm_move), image = painterResource(
                id = R.drawable.confirm
            ), modifier = Modifier.weight(1f), containerColor = colorResource(id = R.color.attack_border_color))
        }
    }

}

@Composable
@Preview
fun AttackConfirmationPanelPreview() {
    val attackSelectedInfo = AttackSelectedInfo (
        AttackRepository.dragonFist,
        attackSimulationResult = AttackSimulationResult(
            18,
            maxDamage = 25,
            minDamageEnough = false,
            maxDamageEnough = false,
            critChance = 10,
            successChance = 75
        ),
        nextTurn = NextTurnInfo(Player(PlayerRepository.catPlayer), 15)
    )
    Row (modifier = Modifier.background(Color.Blue)){
        AttackConfirmationPanel(viewModel = UltimateCatBattleViewModel(), attackSelectedInfo = attackSelectedInfo)
    }
}

@Composable
@Preview
fun AttackConfirmationPanelSuccessPreview() {
    val attackSelectedInfo = AttackSelectedInfo (
        AttackRepository.dragonFist,
        attackSimulationResult = AttackSimulationResult(
            18,
            maxDamage = 25,
            minDamageEnough = true,
            maxDamageEnough = true,
            critChance = 10,
            successChance = 100
        ),
        nextTurn = NextTurnInfo(Player(PlayerRepository.catPlayer), 15)
    )
    Row (modifier = Modifier.background(Color.Blue)){
        AttackConfirmationPanel(viewModel = UltimateCatBattleViewModel(), attackSelectedInfo = attackSelectedInfo)
    }
}