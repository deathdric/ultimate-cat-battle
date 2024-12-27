package org.deathdric.ultimatecatbattle.ui.toolkit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(text: String, modifier: Modifier = Modifier, sizeMode: ElementSizeMode = ElementSizeMode.NORMAL, fontWeight: FontWeight = FontWeight.Bold, textAlign: TextAlign = TextAlign.Center,
              color: Color = Color.Black, shadowColor: Color = Color.LightGray) {
    Text(text = text, fontSize = when(sizeMode) {
        ElementSizeMode.SMALL -> 16.sp
        ElementSizeMode.NORMAL -> 18.sp
        ElementSizeMode.LARGE -> 24.sp
        ElementSizeMode.VERY_LARGE -> 28.sp }, fontWeight = fontWeight, fontFamily = FontFamily.SansSerif,
        textAlign = textAlign, modifier = modifier, color = color, fontStyle = FontStyle.Italic,
        style = TextStyle(
            shadow = Shadow(shadowColor, offset = Offset(-2f, 2f))
        ))
}
@Composable
fun MessageText(text: String, modifier: Modifier = Modifier, sizeMode: ElementSizeMode = ElementSizeMode.NORMAL, fontWeight: FontWeight = FontWeight.Bold, textAlign: TextAlign = TextAlign.Unspecified,
                color: Color = Color.Black) {
    Text(text = text, fontSize = when(sizeMode) {
        ElementSizeMode.SMALL -> 14.sp
        ElementSizeMode.NORMAL -> 16.sp
        ElementSizeMode.LARGE -> 20.sp
        ElementSizeMode.VERY_LARGE -> 24.sp }, fontWeight = fontWeight, fontFamily = FontFamily.SansSerif,
        textAlign = textAlign, modifier = modifier, color = color)
}

@Composable
fun StandardText(text: String, modifier: Modifier = Modifier, fontWeight: FontWeight = FontWeight.Normal, textAlign: TextAlign = TextAlign.Justify,
                 color: Color = Color.Black, fontSize: TextUnit = 16.sp, fontStyle: FontStyle = FontStyle.Normal, lineHeight: TextUnit = TextUnit.Unspecified) {
    val targetLineHeight = if (lineHeight == TextUnit.Unspecified) (fontSize * 6) / 4 else lineHeight
    Text(text = text, fontSize = fontSize, fontWeight = fontWeight, fontFamily = FontFamily.SansSerif,
        textAlign = textAlign, modifier = modifier, color = color, fontStyle = fontStyle, lineHeight = targetLineHeight)
}

@Composable
fun ButtonText(text: String, modifier: Modifier = Modifier, sizeMode: ElementSizeMode = ElementSizeMode.NORMAL, fontWeight: FontWeight = FontWeight.Bold, textAlign: TextAlign = TextAlign.Center,
               color: Color = Color.White) {
    Text(text = text, fontSize =
        when(sizeMode) {
            ElementSizeMode.SMALL -> 14.sp
            ElementSizeMode.NORMAL -> 14.sp
            ElementSizeMode.LARGE -> 18.sp
            ElementSizeMode.VERY_LARGE -> 22.sp },
        fontWeight = fontWeight, fontFamily = FontFamily.SansSerif,
        textAlign = textAlign, modifier = modifier, color = color)
}