package com.cemede.cemede.presentation.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.main_screen_daily_resume
import cemede.composeapp.generated.resources.main_screen_member_button_subtitle
import cemede.composeapp.generated.resources.main_screen_member_button_title
import cemede.composeapp.generated.resources.main_screen_services_button_subtitle
import cemede.composeapp.generated.resources.main_screen_services_button_title
import cemede.composeapp.generated.resources.main_screen_staff_button_subtitle
import cemede.composeapp.generated.resources.main_screen_staff_button_title
import cemede.composeapp.generated.resources.main_screen_subtitle
import cemede.composeapp.generated.resources.main_screen_title
import cemede.composeapp.generated.resources.main_screen_top_app_bar_settings
import cemede.composeapp.generated.resources.main_screen_top_app_bar_title
import cemede.composeapp.generated.resources.see_all
import com.cemede.cemede.presentation.component.CemedeBanner
import com.cemede.cemede.presentation.component.CemedeButton
import com.cemede.cemede.presentation.component.CemedeTopAppBar
import com.cemede.cemede.presentation.theme.ALPHA_0_1
import com.cemede.cemede.presentation.theme.ALPHA_0_2
import com.cemede.cemede.presentation.theme.ALPHA_0_6
import com.cemede.cemede.presentation.theme.ALPHA_0_7
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.WEIGHT_1
import com.cemede.cemede.presentation.theme.elevation_2
import com.cemede.cemede.presentation.theme.font_size_18
import com.cemede.cemede.presentation.theme.font_size_20
import com.cemede.cemede.presentation.theme.padding_12
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_6
import com.cemede.cemede.presentation.theme.padding_8
import com.cemede.cemede.presentation.theme.size_16
import com.cemede.cemede.presentation.theme.size_24
import com.cemede.cemede.presentation.theme.size_40
import com.cemede.cemede.presentation.theme.size_8
import com.cemede.cemede.presentation.theme.space_16
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onNavigateToProfessorList: () -> Unit) {
    var showConstructionBanner by remember { mutableStateOf(false) }

    CemedeTheme {
        Scaffold(
            topBar = {
                CemedeTopAppBar.TopAppBar(
                    icon = Icons.Default.MedicalServices,
                    title = stringResource(Res.string.main_screen_top_app_bar_title),
                    actions = {
                        IconButton(onClick = { showConstructionBanner = true }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = stringResource(Res.string.main_screen_top_app_bar_settings),
                            )
                        }
                    },
                )
            },
        ) { paddingValues ->
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
            ) {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = padding_16),
                    verticalArrangement = Arrangement.spacedBy(space_16),
                ) {
                    item {
                        Spacer(modifier = Modifier.padding(top = padding_8))
                        WelcomeHeader()
                    }
                    item {
                        ActionButtonsGrid(
                            onNavigateToProfessorList = onNavigateToProfessorList,
                            onShowConstructionBanner = { showConstructionBanner = true },
                        )
                    }
                    item {
                        DailySummary(
                            onShowConstructionBanner = { showConstructionBanner = true },
                        )
                    }
                }

                CemedeBanner.ConstructionBanner(
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = padding_16, start = padding_16, end = padding_16),
                    showBanner = showConstructionBanner,
                    onDismiss = { showConstructionBanner = false },
                )
            }
        }
    }
}

@Composable
private fun WelcomeHeader() {
    Column {
        Text(
            text = stringResource(Res.string.main_screen_title),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            fontSize = font_size_20,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = stringResource(Res.string.main_screen_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_7),
            fontSize = font_size_18,
        )
    }
}

@Composable
private fun ActionButtonsGrid(
    onNavigateToProfessorList: () -> Unit,
    onShowConstructionBanner: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(space_16)) {
        Row(horizontalArrangement = Arrangement.spacedBy(space_16)) {
            CemedeButton.ActionButton(
                modifier = Modifier.weight(WEIGHT_1),
                icon = Icons.Default.SportsSoccer,
                title = stringResource(Res.string.main_screen_staff_button_title),
                subtitle = stringResource(Res.string.main_screen_staff_button_subtitle),
                onClick = onNavigateToProfessorList,
            )
            CemedeButton.ActionButton(
                modifier = Modifier.weight(WEIGHT_1),
                icon = Icons.Default.Group,
                title = stringResource(Res.string.main_screen_member_button_title),
                subtitle = stringResource(Res.string.main_screen_member_button_subtitle),
                onClick = onShowConstructionBanner,
            )
        }
        CemedeButton.FullWidthActionButton(
            icon = Icons.Default.Edit,
            title = stringResource(Res.string.main_screen_services_button_title),
            subtitle = stringResource(Res.string.main_screen_services_button_subtitle),
            onClick = onShowConstructionBanner,
        )
    }
}

@Composable
private fun DailySummary(onShowConstructionBanner: () -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.main_screen_daily_resume),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = font_size_18,
                color = MaterialTheme.colorScheme.primary,
            )
            Surface(
                onClick = onShowConstructionBanner,
                shape = RoundedCornerShape(size_8),
                color = MaterialTheme.colorScheme.secondary.copy(ALPHA_0_2),
            ) {
                Text(
                    text = stringResource(Res.string.see_all),
                    modifier = Modifier.padding(horizontal = padding_12, vertical = padding_6),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }
        Spacer(modifier = Modifier.padding(vertical = padding_8))
        Card(
            modifier = Modifier.fillMaxWidth(),
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
                            .size(size_40)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = ALPHA_0_1),
                                CircleShape,
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(size_24),
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = padding_8))
                Column {
                    Text(
                        text = "12 Sesiones hoy",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = "Próxima: 10:30 AM",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_6),
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(onNavigateToProfessorList = {})
}
