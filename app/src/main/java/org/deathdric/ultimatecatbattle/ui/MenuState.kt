package org.deathdric.ultimatecatbattle.ui

enum class MenuActiveItem {
    MAIN_MENU,
    CONFIRM_QUIT_GAME,
    ABOUT_SCREEN
}

data class MenuState(
    val active: Boolean = false,
    val activeItem: MenuActiveItem = MenuActiveItem.MAIN_MENU
)
