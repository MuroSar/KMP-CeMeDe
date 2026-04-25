package com.cemede.cemede.presentation.screen.staff_member_schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.back
import cemede.composeapp.generated.resources.staff_member_schedule_screen_header
import cemede.composeapp.generated.resources.staff_member_schedule_screen_loading
import cemede.composeapp.generated.resources.staff_member_schedule_screen_secondary_header
import cemede.composeapp.generated.resources.synchronizing_data
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.Partner
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.presentation.component.CemedeBanner
import com.cemede.cemede.presentation.component.CemedeDialog
import com.cemede.cemede.presentation.component.CemedeErrorState
import com.cemede.cemede.presentation.component.CemedeLoader
import com.cemede.cemede.presentation.component.CemedeScheduleTable
import com.cemede.cemede.presentation.component.CemedeTopAppBar
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.padding_16
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun StaffMemberScheduleScreen(
    staffMember: StaffMember,
    shouldSyncInfo: Boolean,
    onNavigateBack: () -> Unit,
    onNavigateToPartnerDetail: (Partner) -> Unit,
    viewModel: StaffMemberScheduleViewModel = koinInject(parameters = { parametersOf(staffMember, shouldSyncInfo) }),
) {
    val state by viewModel.state.collectAsState()

    StaffMemberScheduleContent(
        state = state,
        onNavigateBack = onNavigateBack,
        onNavigateToPartnerDetail = onNavigateToPartnerDetail,
        onRetrySync = { viewModel.syncStaffMemberInfo() },
        onDismissNetworkBanner = { viewModel.dismissNetworkBanner() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaffMemberScheduleContent(
    state: StaffMemberScheduleState,
    onNavigateBack: () -> Unit,
    onNavigateToPartnerDetail: (Partner) -> Unit,
    onRetrySync: () -> Unit,
    onDismissNetworkBanner: () -> Unit,
) {
    var selectedSchedule by remember { mutableStateOf<Pair<LocalTime, List<Partner>>?>(null) }

    CemedeTheme {
        selectedSchedule?.let {
            CemedeDialog.PartnerListDialog(
                time = it.first,
                partners = it.second,
                onPartnerClicked = onNavigateToPartnerDetail,
                onDismiss = { selectedSchedule = null },
            )
        }

        Scaffold(
            topBar = {
                CemedeTopAppBar.TopAppBar(
                    title = if (state.staffMember != null)
                        stringResource(Res.string.staff_member_schedule_screen_header, state.staffMember.name)
                    else
                        stringResource(Res.string.staff_member_schedule_screen_secondary_header),
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(Res.string.back),
                            )
                        }
                    },
                )
            },
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                when {
                    state.isLoading -> {
                        CemedeLoader(
                            title = stringResource(Res.string.synchronizing_data),
                            subtitle = stringResource(Res.string.staff_member_schedule_screen_loading),
                        )
                    }

                    state.error != null -> {
                        CemedeErrorState.ErrorState(
                            onRetryClick = onRetrySync,
                        )
                    }

                    state.staffMember != null -> {
                        Box(modifier = Modifier.padding(padding_16)) {
                            CemedeScheduleTable.StaffMemberFullSchedule(
                                staffMember = state.staffMember,
                                onScheduleClick = { time, partners ->
                                    selectedSchedule = time to partners
                                },
                            )
                        }
                    }
                }

                CemedeBanner.NoInternetConnectionBanner(
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(
                                bottom = padding_16,
                                start = padding_16,
                                end = padding_16,
                            ),
                    showBanner = state.showNetworkBanner,
                    onDismiss = onDismissNetworkBanner,
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun StaffMemberScheduleScreenPreview() {
    val partners =
        listOf(
            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
            Partner(id = 2, name = "María García", processType = "Deportivo"),
        )
    val staffMember =
        StaffMember(
            id = 1,
            name = "Macarena",
            partnersSchedule =
                mapOf(
                    DayOfWeek.MONDAY to mapOf(LocalTime(8, 0) to partners, LocalTime(9, 0) to partners),
                    DayOfWeek.TUESDAY to mapOf(LocalTime(8, 0) to partners),
                    DayOfWeek.WEDNESDAY to mapOf(LocalTime(10, 0) to partners),
                    DayOfWeek.THURSDAY to mapOf(LocalTime(8, 0) to partners),
                    DayOfWeek.FRIDAY to mapOf(LocalTime(11, 0) to partners),
                ),
        )
    StaffMemberScheduleContent(
        state =
            StaffMemberScheduleState(
                staffMember = staffMember,
                isLoading = false,
            ),
        onNavigateBack = {},
        onNavigateToPartnerDetail = {},
        onRetrySync = {},
        onDismissNetworkBanner = {},
    )
}

@Preview(showSystemUi = true)
@Composable
private fun StaffMemberScheduleScreenLoadingPreview() {
    StaffMemberScheduleContent(
        state = StaffMemberScheduleState(isLoading = true),
        onNavigateBack = {},
        onNavigateToPartnerDetail = {},
        onRetrySync = {},
        onDismissNetworkBanner = {},
    )
}

@Preview(showSystemUi = true)
@Composable
private fun StaffMemberScheduleScreenErrorPreview() {
    StaffMemberScheduleContent(
        state = StaffMemberScheduleState(isLoading = false, error = "Error message"),
        onNavigateBack = {},
        onNavigateToPartnerDetail = {},
        onRetrySync = {},
        onDismissNetworkBanner = {},
    )
}

@Preview(showSystemUi = true)
@Composable
private fun StaffMemberScheduleScreenNetworkErrorPreview() {
    val partners =
        listOf(
            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
        )
    val staffMember =
        StaffMember(
            id = 1,
            name = "Macarena",
            partnersSchedule =
                mapOf(
                    DayOfWeek.MONDAY to mapOf(LocalTime(8, 0) to partners),
                ),
        )
    StaffMemberScheduleContent(
        state =
            StaffMemberScheduleState(
                staffMember = staffMember,
                isLoading = false,
                showNetworkBanner = true,
            ),
        onNavigateBack = {},
        onNavigateToPartnerDetail = {},
        onRetrySync = {},
        onDismissNetworkBanner = {},
    )
}
