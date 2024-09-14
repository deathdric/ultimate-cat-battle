package org.deathdric.ultimatecatbattle.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deathdric.ultimatecatbattle.R

@Composable
fun AboutScreen(viewModel: UltimateCatBattleViewModel, modifier: Modifier = Modifier) {

    val githubLinkString : AnnotatedString = buildAnnotatedString {
        val str = stringResource(id = R.string.visit_on_github)
        append(str)
        addStyle(style = SpanStyle(
            color = Color(0xff64B5F6),
            fontSize = 18.sp,
            textDecoration = TextDecoration.Underline
        ), start = 0, end = str.length)

        addStringAnnotation(
            tag = "URL",
            annotation = "https://github.com/deathdric/ultimate-cat-battle",
            start = 0,
            end = str.length
        )

    }

    val uriHandler = LocalUriHandler.current
    Column(modifier = modifier) {
        TitleText(text = stringResource(id = R.string.main_title), textAlign = TextAlign.Center, modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth())
        MessageText(text = stringResource(id = R.string.copyright_notice), modifier = Modifier.padding(8.dp))
        ClickableText(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = githubLinkString,
            onClick = {
                githubLinkString
                    .getStringAnnotations("URL", it, it)
                    .firstOrNull()?.let { stringAnnotation ->
                        uriHandler.openUri(stringAnnotation.item)
                    }
            }
        )
        SimpleButton(onClick = { viewModel.displayMenu() }, text = stringResource(id = R.string.back_to_menu))
    }
}

@Composable
@Preview
fun AboutScreenPreview() {
    AboutScreen(viewModel = UltimateCatBattleViewModel(), modifier = Modifier.background(Color.White))
}