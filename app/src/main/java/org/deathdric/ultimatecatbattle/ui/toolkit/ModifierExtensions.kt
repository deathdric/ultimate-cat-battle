package org.deathdric.ultimatecatbattle.ui.toolkit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

fun Modifier.screenModifier(): Modifier {
    return this then Modifier
        .background(Color.White)
        .safeContentPadding()
        .statusBarsPadding()
        .systemBarsPadding()
        .navigationBarsPadding()
        .captionBarPadding()
        .fillMaxSize()
}