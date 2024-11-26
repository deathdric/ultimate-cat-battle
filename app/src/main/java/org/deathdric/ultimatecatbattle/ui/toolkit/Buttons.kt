package org.deathdric.ultimatecatbattle.ui.toolkit

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.deathdric.ultimatecatbattle.R

enum class ElementSizeMode {
    SMALL,
    NORMAL,
    LARGE,
    VERY_LARGE
}

@Composable
fun ImageButton(onClick: () -> Unit, text: String, image: Painter, modifier: Modifier = Modifier, sizeMode: ElementSizeMode = ElementSizeMode.NORMAL, imageDescription: String? = null,
                containerColor : Color = colorResource(id = R.color.default_border_color), contentColor: Color = Color.White,
                disabledContainerColor : Color = colorResource(id = R.color.disabled_background_color),
                disabledContentColor : Color = colorResource(id = R.color.disabled_foreground_color),
                enabled : Boolean = true) {
    Button(onClick = onClick, modifier = modifier
        .padding(
            when(sizeMode) {
                ElementSizeMode.SMALL -> 4.dp
                ElementSizeMode.NORMAL -> 4.dp
                ElementSizeMode.LARGE -> 6.dp
                ElementSizeMode.VERY_LARGE -> 8.dp
            }
        ),
        shape = RoundedCornerShape(50),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        )
    )
    {
        Row (verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = image,
                contentDescription = imageDescription,
                modifier = Modifier.size(when(sizeMode) {
                    ElementSizeMode.SMALL -> 24.dp
                    ElementSizeMode.NORMAL -> 24.dp
                    ElementSizeMode.LARGE -> 28.dp
                    ElementSizeMode.VERY_LARGE -> 32.dp
                }),
                alpha = if (enabled) 1f else 0.3f
            )
            ButtonText(text = text, sizeMode = sizeMode, color = if (enabled) contentColor else disabledContentColor, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun HelpIcon(onClick: () -> Unit, size: Dp, modifier: Modifier = Modifier) {
    Box (modifier = modifier) {
        Image(painter = painterResource(id = R.drawable.question_mark), contentDescription = stringResource(
            id = R.string.help
        ),
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .size(size)
                .clickable { onClick() })
    }
}

@Composable
fun SimpleButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier, sizeMode: ElementSizeMode = ElementSizeMode.NORMAL,
                 containerColor : Color = colorResource(id = R.color.default_border_color), contentColor: Color = Color.White) {
    Button(onClick = onClick, modifier = modifier
        .padding(
            when(sizeMode) {
                ElementSizeMode.SMALL -> 4.dp
                ElementSizeMode.NORMAL -> 4.dp
                ElementSizeMode.LARGE -> 6.dp
                ElementSizeMode.VERY_LARGE -> 8.dp
            }
        ),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    )
    {
        Row (verticalAlignment = Alignment.CenterVertically) {
            ButtonText(text = text, sizeMode = sizeMode, color = contentColor, modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun ImageButtonPreview() {
    ImageButton(onClick = { }, text = "Continue", image = painterResource(id = R.drawable.confirm))
}

@Preview
@Composable
fun ImageButtonPreviewVeryLarge() {
    ImageButton(onClick = { }, sizeMode = ElementSizeMode.VERY_LARGE, text = "Continue", image = painterResource(id = R.drawable.confirm))
}

@Preview
@Composable
fun DisabledImageButtonPreview() {
    ImageButton(onClick = { }, text = "Continue", enabled = false, image = painterResource(id = R.drawable.confirm))
}