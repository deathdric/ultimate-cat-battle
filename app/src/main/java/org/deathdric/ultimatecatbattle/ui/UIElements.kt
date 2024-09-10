package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R

@Composable
fun TitleText(text: String, modifier: Modifier = Modifier, fontWeight: FontWeight = FontWeight.Bold, textAlign: TextAlign = TextAlign.Center,
                color: Color = Color.Black) {
    Text(text = text, fontSize = 18.sp, fontWeight = fontWeight, fontFamily = FontFamily.SansSerif,
        textAlign = textAlign, modifier = modifier, color = color)
}
@Composable
fun MessageText(text: String, modifier: Modifier = Modifier, fontWeight: FontWeight = FontWeight.Bold, textAlign: TextAlign = TextAlign.Unspecified,
                color: Color = Color.Black) {
    Text(text = text, fontSize = 16.sp, fontWeight = fontWeight, fontFamily = FontFamily.SansSerif,
        textAlign = textAlign, modifier = modifier, color = color)
}

@Composable
fun ButtonText(text: String, modifier: Modifier = Modifier, fontWeight: FontWeight = FontWeight.Bold, textAlign: TextAlign = TextAlign.Center,
               color: Color = Color.White) {
    Text(text = text, fontSize = 14.sp, fontWeight = fontWeight, fontFamily = FontFamily.SansSerif,
        textAlign = textAlign, modifier = modifier, color = color)
}

@Composable
fun ImageButton(onClick: () -> Unit, text: String, image: Painter, modifier: Modifier = Modifier, imageDescription: String? = null,
                containerColor : Color = colorResource(id = R.color.default_border_color), contentColor: Color = Color.White) {
    Button(onClick = onClick, modifier = modifier
        .padding(4.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    )
    {
        Row (verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = image,
                contentDescription = imageDescription,
                modifier = Modifier.size(24.dp)
            )
            ButtonText(text = text, color = contentColor, modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun ImageButtonPreview() {
    ImageButton(onClick = { }, text = "Continue", image = painterResource(id = R.drawable.confirm))
}