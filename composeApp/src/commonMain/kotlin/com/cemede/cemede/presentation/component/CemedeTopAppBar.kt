package com.cemede.cemede.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.font_size_20
import com.cemede.cemede.presentation.theme.padding_4
import com.cemede.cemede.presentation.theme.size_24
import com.cemede.cemede.presentation.theme.size_36

object CemedeTopAppBar {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBar(
        modifier: Modifier = Modifier,
        icon: ImageVector,
        title: String,
        actions: @Composable (RowScope.() -> Unit),
    ) {
        TopAppBar(
            modifier = modifier,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier =
                            Modifier
                                .size(size_36)
                                .background(MaterialTheme.colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(size_24),
                        )
                    }
                    Spacer(modifier = Modifier.padding(horizontal = padding_4))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = font_size_20,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            },
            actions = actions,
        )
    }
}

@Preview
@Composable
private fun TopAppBarPreview() {
    CemedeTheme {
        CemedeTopAppBar.TopAppBar(
            icon = Icons.Default.MedicalServices,
            title = "CE.ME.DE",
            actions = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Ajustes",
                    )
                }
            },
        )
    }
}
