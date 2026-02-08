package com.cemede.cemede.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.font_size_18
import com.cemede.cemede.presentation.theme.font_size_20
import com.cemede.cemede.presentation.theme.padding_4
import com.cemede.cemede.presentation.theme.size_24
import com.cemede.cemede.presentation.theme.size_36

object CemedeTopAppBar {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainTopAppBar(
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBar(
        modifier: Modifier = Modifier,
        title: String,
        navigationIcon: @Composable () -> Unit,
        actions: @Composable (RowScope.() -> Unit) = { IconButton(onClick = { }) {} },
    ) {
        TopAppBar(
            modifier = modifier,
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = font_size_18,
                )
            },
            navigationIcon = navigationIcon,
            actions = actions,
        )
    }
}

@Preview
@Composable
private fun MainTopAppBarPreview() {
    CemedeTheme {
        CemedeTopAppBar.MainTopAppBar(
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

@Preview
@Composable
private fun TopAppBarPreview() {
    CemedeTheme {
        Column {
            CemedeTopAppBar.TopAppBar(
                title = "Staff",
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Instructor",
                        )
                    }
                },
            )
            CemedeTopAppBar.TopAppBar(
                title = "Staff",
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        }
    }
}
