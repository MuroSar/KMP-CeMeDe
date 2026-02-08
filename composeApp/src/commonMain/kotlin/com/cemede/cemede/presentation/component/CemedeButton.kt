package com.cemede.cemede.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.cemede.cemede.presentation.theme.ALPHA_0_6
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.RATIO_1
import com.cemede.cemede.presentation.theme.elevation_2
import com.cemede.cemede.presentation.theme.font_size_16
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_8
import com.cemede.cemede.presentation.theme.size_12
import com.cemede.cemede.presentation.theme.size_16
import com.cemede.cemede.presentation.theme.size_32
import com.cemede.cemede.presentation.theme.size_48
import com.cemede.cemede.presentation.theme.size_56

object CemedeButton {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ActionButton(
        modifier: Modifier = Modifier,
        icon: ImageVector,
        title: String,
        subtitle: String,
        onClick: () -> Unit,
    ) {
        Card(
            onClick = onClick,
            modifier = modifier.aspectRatio(RATIO_1),
            shape = RoundedCornerShape(size_16),
            elevation = CardDefaults.cardElevation(defaultElevation = elevation_2),
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(padding_16)
                        .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(size_48)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(size_12),
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(size_32),
                    )
                }
                Column {
                    Text(
                        text = title.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = font_size_16,
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = font_size_16,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FullWidthActionButton(
        modifier: Modifier = Modifier,
        icon: ImageVector,
        title: String,
        subtitle: String,
        onClick: () -> Unit,
    ) {
        Card(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(size_16),
            elevation = CardDefaults.cardElevation(defaultElevation = elevation_2),
        ) {
            Row(
                modifier = Modifier.padding(padding_16),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(size_56)
                            .background(
                                color = MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(size_12),
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(size_32),
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = padding_8))
                Column {
                    Text(
                        text = title.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = font_size_16,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_6),
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = font_size_16,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ActionButtonPreview() {
    CemedeTheme {
        CemedeButton.ActionButton(
            icon = Icons.Default.SportsSoccer,
            title = "Staff",
            subtitle = "Buscar profe",
            onClick = { },
        )
    }
}

@Preview
@Composable
private fun FullWidthActionButtonPreview() {
    CemedeTheme {
        CemedeButton.FullWidthActionButton(
            icon = Icons.Default.Edit,
            title = "Servicios",
            subtitle = "Readaptación",
            onClick = { },
        )
    }
}
