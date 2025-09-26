package com.example.jwstimagepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jwstimagepractice.ui.JWSTDisplayState
import com.example.jwstimagepractice.ui.theme.JWSTImagePracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JWSTImagePracticeTheme {
                JWSTApp()
            }
        }
    }
}

@Composable
fun JWSTApp() {
    val jwstDisplayStates = listOf(
        JWSTDisplayState(R.drawable.cosmic_wreath, R.string.text_cosmic_wreath, R.string.credit_cosmic_wreath),
        JWSTDisplayState(R.drawable.ngc_2556, R.string.text_ngc2556, R.string.credit_ngc2556),
        JWSTDisplayState(R.drawable.ic2163_ngc2207, R.string.text_ic2163, R.string.credit_ic2163),
        JWSTDisplayState(R.drawable.sombrero, R.string.text_sombrero, R.string.credit_sombrero),
        JWSTDisplayState(R.drawable.lensed_quasar, R.string.text_lensed_quasar, R.string.credit_lensed_quasar)
    )
    var currentIndex by remember { mutableIntStateOf(0) }

    JWSTScreen(
        jwstDisplayStates[currentIndex],
        isPreviousButton = (currentIndex > 0),
        isNextButton = (currentIndex < jwstDisplayStates.lastIndex),
        onNextClickAction = { if (currentIndex < jwstDisplayStates.lastIndex) currentIndex++ },
        onPreviousClickAction = { if (currentIndex > 0) currentIndex-- }
    )
}

@Composable
private fun JWSTScreen (
    jwstDisplayState : JWSTDisplayState,
    modifier: Modifier = Modifier,
    isNextButton: Boolean = true,
    isPreviousButton: Boolean = true,
    onNextClickAction : () -> Unit = {},
    onPreviousClickAction: () -> Unit = {}
    ) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(dimensionResource(R.dimen.display_horizontal_padding))
            .statusBarsPadding()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        JWSTDescText(
            jwstDisplayState.imageText,
            modifier = Modifier
                .width(dimensionResource(R.dimen.text_box_width))
                .height(dimensionResource(R.dimen.text_box_height))
        )
        Surface(
            modifier = Modifier
                .width(dimensionResource(R.dimen.image_pane_width))
                .height(dimensionResource(R.dimen.image_pane_height))
                .padding(top = 40.dp),
            shadowElevation = 20.dp,
            tonalElevation = 5.dp,
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(12.dp)
        ) {
            JWSTImage(jwstDisplayState.imageRef, jwstDisplayState.imageText)
        }
        Box(
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                JWSTCreditText(
                    jwstDisplayState.imageCredit,
                    modifier = Modifier
                    .width(dimensionResource(R.dimen.text_box_width))
                    .height(dimensionResource(R.dimen.text_box_height)))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    NavigationButton(
                        buttonTextRes = R.string.previous_button,
                        modifier = Modifier.padding(top = 20.dp).width(104.dp),
                        isButton = isPreviousButton,
                        onButtonClick = onPreviousClickAction
                    )

                    NavigationButton(
                        buttonTextRes = R.string.next_button,
                        modifier = Modifier.padding(top = 20.dp).width(104.dp),
                        onButtonClick = onNextClickAction,
                        isButton = isNextButton
                    )
                }
            }
        }
    }
}


@Composable
private fun JWSTImage(
    @DrawableRes imageRef : Int,
    @StringRes contentDescId : Int
) {
    Image(
        painter = painterResource(imageRef),
        contentDescription = stringResource(contentDescId),
        contentScale = ContentScale.Fit,
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
private fun JWSTDescText(
    @StringRes imageText : Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(imageText),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        modifier = modifier
    )
}

@Composable
private fun JWSTCreditText(
    @StringRes creditRef : Int,
    modifier : Modifier = Modifier
) {
    Text(
        text = stringResource(creditRef),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall,
        color = Color.White,
        modifier = modifier
    )
}

@Composable
private fun NavigationButton(
    @StringRes buttonTextRes : Int,
    modifier: Modifier = Modifier,
    onButtonClick : () -> Unit = { },
    isButton: Boolean = true
    ) {
    Button(
        onClick = onButtonClick,
        modifier = modifier,
        enabled = isButton,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
        elevation = ButtonDefaults.buttonElevation(10.dp)
    ) {
        Text(text = stringResource(buttonTextRes), color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun JWSTAppPreview() {
    JWSTImagePracticeTheme {
        JWSTApp()
    }
}