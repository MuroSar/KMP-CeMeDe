package com.cemede.cemede.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.error_state_subtitle
import cemede.composeapp.generated.resources.error_state_title
import cemede.composeapp.generated.resources.retry
import com.cemede.cemede.presentation.theme.ALPHA_0_05
import com.cemede.cemede.presentation.theme.ALPHA_0_1
import com.cemede.cemede.presentation.theme.ALPHA_0_3
import com.cemede.cemede.presentation.theme.ALPHA_0_5
import com.cemede.cemede.presentation.theme.ALPHA_0_7
import com.cemede.cemede.presentation.theme.CemedeOliveGreen
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.RedOverloadedCapacity
import com.cemede.cemede.presentation.theme.elevation_2
import com.cemede.cemede.presentation.theme.elevation_4
import com.cemede.cemede.presentation.theme.elevation_8
import com.cemede.cemede.presentation.theme.font_size_22
import com.cemede.cemede.presentation.theme.height_16
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_20
import com.cemede.cemede.presentation.theme.padding_8
import com.cemede.cemede.presentation.theme.radius_40
import com.cemede.cemede.presentation.theme.size_180
import com.cemede.cemede.presentation.theme.size_192
import com.cemede.cemede.presentation.theme.size_20
import com.cemede.cemede.presentation.theme.size_40
import com.cemede.cemede.presentation.theme.size_64
import com.cemede.cemede.presentation.theme.width_1
import com.cemede.cemede.presentation.theme.width_2
import com.cemede.cemede.presentation.theme.width_4
import org.jetbrains.compose.resources.stringResource

object CemedeErrorState {
    @Composable
    fun ErrorState(
        modifier: Modifier = Modifier,
        title: String = stringResource(Res.string.error_state_title),
        subtitle: String = stringResource(Res.string.error_state_subtitle),
        onRetryClick: (() -> Unit)? = null,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    start = padding_16,
                    end = padding_16,
                    bottom = padding_16,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            ErrorVisual()

            Spacer(modifier = Modifier.height(height_16))

            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontSize = font_size_22,
            )
            Spacer(modifier = Modifier.padding(top = padding_8))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_7),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.padding(top = padding_16))
            if (onRetryClick != null) {
                Button(
                    onClick = onRetryClick,
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Ícono de recargar",
                        )
                        Spacer(modifier = Modifier.width(padding_8))
                        Text(
                            text = stringResource(Res.string.retry),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ErrorVisual() {
        Box(contentAlignment = Alignment.Center) {
            // Abstract Background Element (Blur effect)
            Box(
                modifier = Modifier
                    .size(size_180)
                    .alpha(ALPHA_0_3)
                    .blur(radius_40)
                    .background(
                        color = CemedeOliveGreen.copy(alpha = ALPHA_0_5),
                        shape = CircleShape,
                    )
            )

            // Central Error Visual
            Box(
                modifier = Modifier
                    .size(size_192)
                    .border(
                        width = width_2,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_1),
                        shape = CircleShape,
                    )
                    .background(
                        color = Color.White,
                        shape = CircleShape,
                    )
                    .shadow(
                        elevation = elevation_2,
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center
            ) {

                // Main Icon Component
                Box(contentAlignment = Alignment.TopEnd) {
                    Surface(
                        modifier = Modifier
                            .padding(padding_16)
                            .shadow(
                                elevation = elevation_4,
                                shape = RoundedCornerShape(size_20),
                            ),
                        shape = RoundedCornerShape(size_20),
                        color = CemedeOliveGreen.copy(alpha = ALPHA_0_05),
                        border = BorderStroke(
                            width = width_1,
                            color = CemedeOliveGreen.copy(alpha = ALPHA_0_1),
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Dangerous,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(padding_20)
                                .size(size_64),
                            tint = CemedeOliveGreen
                        )
                    }

                    // Priority High Badge
                    Surface(
                        modifier = Modifier
                            .size(size_40)
                            .shadow(
                                elevation = elevation_8,
                                shape = CircleShape,
                            ),
                        shape = CircleShape,
                        color = RedOverloadedCapacity,
                        border = BorderStroke(
                            width = width_4,
                            color = Color.White,
                        )
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.PriorityHigh,
                                contentDescription = null,
                                modifier = Modifier.size(size_20),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ErrorStateWithActionPreview() {
    CemedeTheme {
        CemedeErrorState.ErrorState(
            onRetryClick = { },
        )
    }
}

@Preview
@Composable
private fun ErrorStateWithoutActionPreview() {
    CemedeTheme {
        CemedeErrorState.ErrorState()
    }
}
