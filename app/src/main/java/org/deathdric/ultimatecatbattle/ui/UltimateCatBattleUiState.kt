package org.deathdric.ultimatecatbattle.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.deathdric.ultimatecatbattle.model.AttackAction
import org.deathdric.ultimatecatbattle.model.AttackResult
import org.deathdric.ultimatecatbattle.model.AttackSelectedInfo
import org.deathdric.ultimatecatbattle.model.SupportAction
import org.deathdric.ultimatecatbattle.model.SupportSelectedInfo

data class UltimateCatBattleUiState(
    val isStartScreen: Boolean = true,
    val isGameOver: Boolean = false,
    val actionMode: ActionMode = ActionMode.ACTION_SELECT,
    val remainingTime: Int = 0,
    @StringRes
    var activePlayerName: Int,
    @DrawableRes
    val activePlayerIcon: Int,
    var activePlayerId: Int,
    val player1 : PlayerStatus,
    val player2 : PlayerStatus,
    val availableAttacks : List<AttackAction> = emptyList(),
    val availableSupports : List<SupportAction> = emptyList(),
    val lastAttack: AttackAction? = null,
    val attackSelectedInfo: AttackSelectedInfo? = null,
    val attackResult : AttackResult? = null,
    val supportSelectedInfo: SupportSelectedInfo? = null,
    val lastSupport : SupportAction? = null,
    val showActionDetails : Boolean = false,
    val menuState: MenuState = MenuState()

)
