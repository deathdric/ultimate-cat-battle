package org.deathdric.ultimatecatbattle.ui

import org.deathdric.ultimatecatbattle.R

enum class MenuActiveItem {
    MAIN_MENU,
    CONFIRM_QUIT_GAME,
    ABOUT_SCREEN,
    GAME_INSTRUCTIONS_MENU,
    GAME_INSTRUCTIONS_DETAILS
}

enum class InstructionsActiveItem(val title: Int) {
    GAME_BASICS(R.string.game_basics),
    ATTACK_MOVES(R.string.game_attacks),
    SUPPORT_MOVES(R.string.game_supports)
}

data class MenuState(
    val active: Boolean = false,
    val activeItem: MenuActiveItem = MenuActiveItem.MAIN_MENU,
    val instructionsActiveItem: InstructionsActiveItem = InstructionsActiveItem.GAME_BASICS
)
