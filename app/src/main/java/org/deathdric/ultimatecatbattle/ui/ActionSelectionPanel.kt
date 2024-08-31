package org.deathdric.ultimatecatbattle.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.data.AttackRepository
import org.deathdric.ultimatecatbattle.data.SupportRepository
import org.deathdric.ultimatecatbattle.model.AttackAction
import org.deathdric.ultimatecatbattle.model.SupportAction

@Composable
fun StatDetailPanel(
    @DrawableRes
    iconResource: Int,
    data : String,
    modifier: Modifier = Modifier
) {
    Row (modifier = modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically){
        Image(
            painter = painterResource(id = iconResource),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .padding(end = 4.dp)
        )
        Text(text = data, fontSize = 16.sp)
    }
}

@Composable
fun SupportActionButton(supportAction: SupportAction,
                       showDetails: Boolean,
                       onClick: () -> Unit,
                       modifier: Modifier = Modifier) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEEFFEE),
            contentColor = Color.Black),
        shape = if (showDetails) RoundedCornerShape(10) else RoundedCornerShape(50),
        onClick = onClick,
        border = BorderStroke(1.dp, Color(0xFF008000)),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()) {
        if (showDetails) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = supportAction.name),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Row(horizontalArrangement = Arrangement.Center) {
                    StatDetailPanel(
                        iconResource = R.drawable.clock,
                        data = supportAction.delay.toString()
                    )
                    StatDetailPanel(
                        iconResource = R.drawable.attack,
                        data = supportAction.attackBonus.toString()
                    )
                    StatDetailPanel(
                        iconResource = R.drawable.defense,
                        data = supportAction.defenseBonus.toString()
                    )
                }
                Row(horizontalArrangement = Arrangement.Center) {

                    StatDetailPanel(
                        iconResource = R.drawable.hit,
                        data = supportAction.hitBonus.toString()
                    )
                    StatDetailPanel(
                        iconResource = R.drawable.avoid2,
                        data = supportAction.avoidBonus.toString()
                    )
                    StatDetailPanel(
                        iconResource = R.drawable.critical,
                        data = supportAction.criticalBonus.toString()
                    )
                }
            }
        } else {
            Row {
                Text(text = "%s  (%s)".format(stringResource(id = supportAction.name), supportAction.delay),
                    modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Composable
fun AttackActionButton(attackAction: AttackAction,
                       showDetails: Boolean,
                       onClick: () -> Unit,
                       modifier: Modifier = Modifier) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFEEEE),
            contentColor = Color.Black),
        onClick = onClick,
        border = BorderStroke(1.dp, Color(0xFF800000)),
        shape = if (showDetails) RoundedCornerShape(10) else RoundedCornerShape(50),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()) {
        if (showDetails) {
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Row (verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Text(text = stringResource(id = attackAction.name),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp)
                }
                Row (horizontalArrangement = Arrangement.Center) {
                    StatDetailPanel(
                        iconResource = R.drawable.clock,
                        data = attackAction.delay.toString()
                    )
                    StatDetailPanel(
                        iconResource = R.drawable.damage,
                        data = "%d - %d".format(attackAction.minDamage, attackAction.maxDamage)
                    )
                }
                Row (horizontalArrangement = Arrangement.Center) {

                    StatDetailPanel(
                        iconResource = R.drawable.hit_chance,
                        data = attackAction.hit.toString()
                    )
                    StatDetailPanel(
                        iconResource = R.drawable.critical,
                        data = attackAction.critical.toString()
                    )
                }

            }
        } else {
            Row {
                Text(text = "%s  (%s)".format(stringResource(id = attackAction.name), attackAction.delay),
                    modifier = Modifier.padding(4.dp))
            }
        }


    }
}

@Composable
fun ActionSelectionPanel(viewModel: UltimateCatBattleViewModel, uiState: UltimateCatBattleUiState, modifier: Modifier = Modifier) {
    LazyVerticalGrid(columns = GridCells.Adaptive(280.dp), modifier = modifier) {
        items(uiState.availableAttacks) { attackAction ->
            AttackActionButton(attackAction = attackAction,
                showDetails = uiState.showActionDetails,
                onClick = { viewModel.chooseAttack(attackAction = attackAction) },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth())
        }
        items(uiState.availableSupports) { supportAction ->
            SupportActionButton(
                supportAction = supportAction,
                showDetails = uiState.showActionDetails,
                onClick = { viewModel.chooseSupport(supportAction) },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth())
        }
    }
}

@Composable
@Preview
fun DetailedAttackButtonPreview() {
    AttackActionButton(attackAction = AttackRepository.dragonFist, showDetails = true, onClick = { })
}

@Composable
@Preview
fun AttackButtonPreview() {
    AttackActionButton(attackAction = AttackRepository.dragonFist, showDetails = false, onClick = { })
}

@Composable
@Preview
fun DetailedSupportButtonPreview() {
    SupportActionButton(supportAction = SupportRepository.speed, showDetails = true, onClick = { })
}

@Composable
@Preview
fun SupportButtonPreview() {
    SupportActionButton(supportAction = SupportRepository.speed, showDetails = false, onClick = { })
}