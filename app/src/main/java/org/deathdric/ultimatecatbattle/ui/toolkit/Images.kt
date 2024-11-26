package org.deathdric.ultimatecatbattle.ui.toolkit

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun ToggledImage(
    active: Boolean ,
    @DrawableRes
    activeImageId: Int,
    @DrawableRes
    inactiveImageId: Int,
    modifier: Modifier = Modifier,
    alternativeText: String? = null,
    onClick: () -> Unit
) {
    val painterResource = if (active) painterResource(id = activeImageId) else painterResource(id = inactiveImageId)
    Image(painter = painterResource, contentDescription = alternativeText, modifier = modifier.clickable { onClick() })
}