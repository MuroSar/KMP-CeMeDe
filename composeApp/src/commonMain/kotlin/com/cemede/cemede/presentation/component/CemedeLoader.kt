package com.cemede.cemede.presentation.component

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.cemede.cemede.presentation.theme.ALPHA_0_7
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.height_24
import com.cemede.cemede.presentation.theme.height_4
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.size_48
import com.cemede.cemede.presentation.theme.size_96
import com.cemede.cemede.presentation.theme.width_2

@Composable
fun CemedeLoader(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loader")

    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(2000),
            ),
        label = "pulse",
    )

    Column(
        modifier = modifier.fillMaxSize().padding(start = padding_16, end = padding_16, bottom = padding_16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(size_96),
                strokeWidth = width_2,
                strokeCap = StrokeCap.Round,
            )
            Icon(
                imageVector = Icons.Default.LightMode,
                contentDescription = null,
                modifier =
                    Modifier
                        .size(size_48)
                        .scale(pulse),
                tint = MaterialTheme.colorScheme.secondary,
            )
        }
        Spacer(modifier = Modifier.height(height_24))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(height_4))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_7),
        )
    }
}

@Preview
@Composable
private fun CemedeLoaderPreview() {
    CemedeTheme {
        CemedeLoader(
            title = "Sincronizando datos...",
            subtitle = "Por favor, espere un momento",
        )
    }
}
