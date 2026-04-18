package com.cemede.cemede.presentation.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.main_screen_daily_availability
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
import com.cemede.cemede.presentation.component.CemedeBanner
import com.cemede.cemede.presentation.component.CemedeButton
import com.cemede.cemede.presentation.component.CemedeScheduleTable
import com.cemede.cemede.presentation.component.CemedeTopAppBar
import com.cemede.cemede.presentation.theme.ALPHA_0_7
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.WEIGHT_1
import com.cemede.cemede.presentation.theme.font_size_18
import com.cemede.cemede.presentation.theme.font_size_20
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_8
import com.cemede.cemede.presentation.theme.space_16
import com.cemede.cemede.presentation.theme.space_8
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToStaffMemberList: () -> Unit,
    onNavigateToPartnerList: () -> Unit,
    viewModel: MainViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var showConstructionBanner by remember { mutableStateOf(false) }

    CemedeTheme {
        Scaffold(
            topBar = {
                CemedeTopAppBar.MainTopAppBar(
                    icon = Icons.Default.MedicalServices,
                    title = stringResource(Res.string.main_screen_top_app_bar_title),
                    actions = {
                        IconButton(onClick = { showConstructionBanner = true }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = stringResource(Res.string.main_screen_top_app_bar_settings),
                                tint = MaterialTheme.colorScheme.secondary,
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
                            onNavigateToStaffMemberList = onNavigateToStaffMemberList,
                            onNavigateToPartnerList = onNavigateToPartnerList,
                            onShowConstructionBanner = { showConstructionBanner = true },
                        )
                    }
                    item {
                        DailyAvailability(state = state)
                    }
                }

                Column(
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = padding_16, start = padding_16, end = padding_16),
                    verticalArrangement = Arrangement.spacedBy(space_8),
                ) {
                    CemedeBanner.NoInternetConnectionBanner(
                        showBanner = state.showNetworkBanner,
                        onDismiss = { viewModel.dismissNetworkBanner() },
                    )

                    CemedeBanner.ConstructionBanner(
                        showBanner = showConstructionBanner,
                        onDismiss = { showConstructionBanner = false },
                    )
                }
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
    onNavigateToStaffMemberList: () -> Unit,
    onNavigateToPartnerList: () -> Unit,
    onShowConstructionBanner: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(space_16)) {
        Row(horizontalArrangement = Arrangement.spacedBy(space_16)) {
            CemedeButton.ActionButton(
                modifier = Modifier.weight(WEIGHT_1),
                icon = Icons.Default.SportsSoccer,
                title = stringResource(Res.string.main_screen_staff_button_title),
                subtitle = stringResource(Res.string.main_screen_staff_button_subtitle),
                onClick = onNavigateToStaffMemberList,
            )
            CemedeButton.ActionButton(
                modifier = Modifier.weight(WEIGHT_1),
                icon = Icons.Default.Group,
                title = stringResource(Res.string.main_screen_member_button_title),
                subtitle = stringResource(Res.string.main_screen_member_button_subtitle),
                onClick = onNavigateToPartnerList,
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
private fun DailyAvailability(state: MainState) {
    Column {
        Text(
            text = stringResource(Res.string.main_screen_daily_availability),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = font_size_18,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.padding(vertical = padding_8))

        if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
            )
        } else {
            CemedeScheduleTable.StaffSchedule(staffMembers = state.staffMembers)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(onNavigateToStaffMemberList = {}, onNavigateToPartnerList = {})
}
