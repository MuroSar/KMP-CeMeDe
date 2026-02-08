package com.cemede.cemede.presentation.screen.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.ALPHA_0_1
import com.cemede.cemede.presentation.theme.ALPHA_0_2
import com.cemede.cemede.presentation.theme.ALPHA_0_3
import com.cemede.cemede.presentation.theme.ALPHA_0_4
import com.cemede.cemede.presentation.theme.ALPHA_0_5
import com.cemede.cemede.presentation.theme.ALPHA_0_6
import com.cemede.cemede.presentation.theme.ALPHA_0_8
import com.cemede.cemede.presentation.theme.font_size_12
import com.cemede.cemede.presentation.theme.font_size_18
import com.cemede.cemede.presentation.theme.font_size_20
import com.cemede.cemede.presentation.theme.font_size_36
import com.cemede.cemede.presentation.theme.height_4
import com.cemede.cemede.presentation.theme.letter_spacing_0_5
import com.cemede.cemede.presentation.theme.letter_spacing_0_8
import com.cemede.cemede.presentation.theme.letter_spacing_1
import com.cemede.cemede.presentation.theme.letter_spacing_5
import com.cemede.cemede.presentation.theme.padding_15
import com.cemede.cemede.presentation.theme.padding_30
import com.cemede.cemede.presentation.theme.padding_48
import com.cemede.cemede.presentation.theme.padding_70
import com.cemede.cemede.presentation.theme.SCALE_1_1
import com.cemede.cemede.presentation.theme.SCALE_1_25
import com.cemede.cemede.presentation.theme.size_120
import com.cemede.cemede.presentation.theme.size_200
import com.cemede.cemede.presentation.theme.size_250
import com.cemede.cemede.presentation.theme.size_32
import com.cemede.cemede.presentation.theme.space_32
import com.cemede.cemede.presentation.theme.space_4
import com.cemede.cemede.presentation.theme.space_40
import com.cemede.cemede.presentation.theme.WEIGHT_1
import com.cemede.cemede.presentation.theme.width_24
import com.cemede.cemede.presentation.theme.width_4
import com.cemede.cemede.presentation.theme.width_48

private const val ANIMATION_DURATION: Int = 2000

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    val pulse = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        pulse.animateTo(
            targetValue = 1.1f,
            animationSpec = tween(durationMillis = ANIMATION_DURATION)
        )
        onSplashFinished()
    }

    CemedeTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            DecorativeElements()
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.weight(WEIGHT_1),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(space_40)
                    ) {
                        CemedeLogo(pulse = pulse.value)
                        AppTitle()
                    }
                }
                SplashScreenFooter()
            }
        }
    }
}

@Composable
private fun CemedeLogo(pulse: Float) {
    Box(
        modifier = Modifier.size(size_250),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .scale(SCALE_1_1)
                .alpha(ALPHA_0_2)
                .background(Color.White.copy(alpha = ALPHA_0_3), CircleShape)
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .scale(SCALE_1_25)
                .alpha(ALPHA_0_1)
                .background(Color.White.copy(alpha = ALPHA_0_2), CircleShape)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(pulse),
            verticalArrangement = Arrangement.spacedBy(space_32)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Explore Icon",
                    modifier = Modifier.size(size_120),
                    tint = MaterialTheme.colorScheme.primary
                )
                Box(
                    modifier = Modifier
                        .size(size_32)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape,
                        )
                        .border(
                            width = width_4,
                            color = MaterialTheme.colorScheme.background,
                            shape = CircleShape,
                        )
                )
            }
            Text(
                text = buildAnnotatedString {
                    append("CE.")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append("ME.")
                    }
                    append("DE")
                },
                color = Color.White,
                fontSize = font_size_36,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = letter_spacing_0_5,
            )
        }
    }
}

@Composable
private fun AppTitle() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space_4),
    ) {
        Text(
            text = "CENTRO MÉDICO",
            color = Color.White.copy(alpha = ALPHA_0_8),
            fontSize = font_size_20,
            fontWeight = FontWeight.Medium,
            letterSpacing = letter_spacing_5,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "Deportivo del Este",
            color = Color.White.copy(alpha = ALPHA_0_6),
            fontSize = font_size_18,
            fontWeight = FontWeight.Light,
            letterSpacing = letter_spacing_1,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SplashScreenFooter(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(bottom = padding_48),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space_32),
    ) {
        Box(
            modifier = Modifier
                .size(width = width_48, height = height_4)
                .background(Color.White.copy(alpha = ALPHA_0_2), CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .size(width = width_24, height = height_4)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
            )
        }
        Text(
            text = "MANEJO INTERNO V1.0",
            color = Color.White.copy(alpha = ALPHA_0_4),
            fontSize = font_size_12,
            fontWeight = FontWeight.Normal,
            letterSpacing = letter_spacing_0_8,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DecorativeElements() {
    Box(modifier = Modifier.fillMaxSize()) {
        Icon(
            imageVector = Icons.Filled.FitnessCenter,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(padding_15)
                .alpha(ALPHA_0_5)
                .size(size_200),
            tint = Color.White
        )
        Icon(
            imageVector = Icons.Filled.HealthAndSafety,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = padding_30, bottom = padding_70)
                .alpha(ALPHA_0_5)
                .size(size_200),
            tint = Color.White
        )

    }
}

@Preview(showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen(
        onSplashFinished = {},
    )
}
