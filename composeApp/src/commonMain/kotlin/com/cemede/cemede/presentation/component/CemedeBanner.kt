package com.cemede.cemede.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.close
import cemede.composeapp.generated.resources.construction_banner_subtitle
import cemede.composeapp.generated.resources.construction_banner_title
import cemede.composeapp.generated.resources.no_internet_banner_subtitle
import cemede.composeapp.generated.resources.no_internet_banner_title
import com.cemede.cemede.presentation.theme.ALPHA_0_2
import com.cemede.cemede.presentation.theme.ALPHA_0_7
import com.cemede.cemede.presentation.theme.CemedeDarkOliveGreen
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.WEIGHT_1
import com.cemede.cemede.presentation.theme.elevation_8
import com.cemede.cemede.presentation.theme.font_size_12
import com.cemede.cemede.presentation.theme.font_size_14
import com.cemede.cemede.presentation.theme.padding_12
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_6
import com.cemede.cemede.presentation.theme.size_12
import com.cemede.cemede.presentation.theme.size_8
import com.cemede.cemede.presentation.theme.space_12
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

object CemedeBanner {
    @Composable
    fun ConstructionBanner(
        modifier: Modifier = Modifier,
        showBanner: Boolean,
        onDismiss: () -> Unit,
    ) {
        GenericBanner(
            modifier = modifier,
            showBanner = showBanner,
            icon = Icons.Default.Handyman,
            titleRes = Res.string.construction_banner_title,
            subtitleRes = Res.string.construction_banner_subtitle,
            buttonTextRes = Res.string.close,
            onDismiss = onDismiss,
        )
    }

    @Composable
    fun NoInternetConnectionBanner(
        modifier: Modifier = Modifier,
        showBanner: Boolean,
        onDismiss: () -> Unit,
    ) {
        GenericBanner(
            modifier = modifier,
            showBanner = showBanner,
            icon = Icons.Default.WifiOff,
            titleRes = Res.string.no_internet_banner_title,
            subtitleRes = Res.string.no_internet_banner_subtitle,
            buttonTextRes = Res.string.close,
            onDismiss = onDismiss,
        )
    }

    @Composable
    private fun GenericBanner(
        modifier: Modifier = Modifier,
        showBanner: Boolean,
        icon: ImageVector,
        titleRes: StringResource,
        subtitleRes: StringResource,
        buttonTextRes: StringResource,
        onDismiss: () -> Unit,
    ) {
        AnimatedVisibility(
            visible = showBanner,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = modifier,
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(size_12),
                colors = CardDefaults.cardColors(containerColor = CemedeDarkOliveGreen),
                elevation = CardDefaults.cardElevation(defaultElevation = elevation_8),
            ) {
                Row(
                    modifier = Modifier.padding(padding_16),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space_12),
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Column(modifier = Modifier.weight(WEIGHT_1)) {
                        Text(
                            text = stringResource(titleRes),
                            fontWeight = FontWeight.Bold,
                            fontSize = font_size_14,
                            color = Color.White,
                        )
                        Text(
                            text = stringResource(subtitleRes),
                            fontSize = font_size_12,
                            color = Color.White.copy(alpha = ALPHA_0_7),
                        )
                    }
                    Surface(
                        onClick = {
                            onDismiss()
                        },
                        shape = RoundedCornerShape(size_8),
                        color = MaterialTheme.colorScheme.primary.copy(ALPHA_0_2),
                    ) {
                        Text(
                            text = stringResource(buttonTextRes).uppercase(),
                            modifier = Modifier.padding(horizontal = padding_12, vertical = padding_6),
                            fontSize = font_size_12,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ConstructionBannerPreview() {
    CemedeTheme {
        CemedeBanner.ConstructionBanner(
            showBanner = true,
            onDismiss = {},
        )
    }
}

@Preview
@Composable
private fun NoInternetConnectionBannerPreview() {
    CemedeTheme {
        CemedeBanner.NoInternetConnectionBanner(
            showBanner = true,
            onDismiss = {},
        )
    }
}
