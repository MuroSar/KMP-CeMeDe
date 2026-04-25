package com.cemede.cemede.presentation.screen.staff_member_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.back
import cemede.composeapp.generated.resources.clear_search
import cemede.composeapp.generated.resources.empty_state_subtitle
import cemede.composeapp.generated.resources.filter
import cemede.composeapp.generated.resources.see_all
import cemede.composeapp.generated.resources.staff_member_detail_screen_daily_schedule
import cemede.composeapp.generated.resources.staff_member_detail_screen_daily_schedule_empty_state
import cemede.composeapp.generated.resources.staff_member_detail_screen_empty_state_title
import cemede.composeapp.generated.resources.staff_member_detail_screen_header_title
import cemede.composeapp.generated.resources.staff_member_detail_screen_loading
import cemede.composeapp.generated.resources.staff_member_detail_screen_partner_list
import cemede.composeapp.generated.resources.staff_member_detail_screen_search_bar
import cemede.composeapp.generated.resources.staff_member_detail_screen_staff_member_not_found
import cemede.composeapp.generated.resources.synchronizing_data
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.Partner
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.util.DateTimeHandler
import com.cemede.cemede.presentation.component.CemedeBanner
import com.cemede.cemede.presentation.component.CemedeCard
import com.cemede.cemede.presentation.component.CemedeDialog
import com.cemede.cemede.presentation.component.CemedeEmptyState
import com.cemede.cemede.presentation.component.CemedeErrorState
import com.cemede.cemede.presentation.component.CemedeLoader
import com.cemede.cemede.presentation.component.CemedeSearchBar
import com.cemede.cemede.presentation.component.CemedeTopAppBar
import com.cemede.cemede.presentation.theme.ALPHA_0_2
import com.cemede.cemede.presentation.theme.ALPHA_0_7
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.height_16
import com.cemede.cemede.presentation.theme.height_8
import com.cemede.cemede.presentation.theme.padding_12
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_6
import com.cemede.cemede.presentation.theme.padding_8
import com.cemede.cemede.presentation.theme.size_16
import com.cemede.cemede.presentation.theme.size_8
import com.cemede.cemede.presentation.theme.space_12
import com.cemede.cemede.presentation.theme.space_8
import com.cemede.cemede.presentation.theme.width_4
import com.cemede.cemede.presentation.util.AnimationUtils.smoothScrollTo
import com.cemede.cemede.presentation.util.PhonesHelper
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun StaffMemberDetailScreen(
    staffMember: StaffMember,
    onNavigateBack: () -> Unit,
    onNavigateToPartnerDetail: (Partner) -> Unit,
    onNavigateToFullSchedule: (StaffMember) -> Unit,
    viewModel: StaffMemberDetailViewModel = koinInject(parameters = { parametersOf(staffMember) }),
) {
    val state by viewModel.state.collectAsState()

    StaffMemberDetailContent(
        state = state,
        onErrorRetry = { viewModel.syncStaffMemberInfo() },
        onNavigateBack = onNavigateBack,
        onNavigateToPartnerDetail = onNavigateToPartnerDetail,
        onNavigateToFullSchedule = onNavigateToFullSchedule,
        onDismissNetworkBanner = { viewModel.dismissNetworkBanner() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaffMemberDetailContent(
    state: StaffMemberDetailState,
    onErrorRetry: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToPartnerDetail: (Partner) -> Unit,
    onNavigateToFullSchedule: (StaffMember) -> Unit,
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
                    title = stringResource(Res.string.staff_member_detail_screen_header_title),
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
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    state.isLoading -> {
                        CemedeLoader(
                            title = stringResource(Res.string.synchronizing_data),
                            subtitle = stringResource(Res.string.staff_member_detail_screen_loading),
                        )
                    }

                    state.error != null -> {
                        CemedeErrorState.ErrorState(
                            onRetryClick = onErrorRetry,
                        )
                    }

                    state.staffMember != null -> {
                        Column(modifier = Modifier.padding(paddingValues)) {
                            CemedeCard.StaffMemberDetailCard(
                                modifier = Modifier.fillMaxWidth(),
                                staffMember = state.staffMember,
                                onCallButtonClick = { PhonesHelper.callStaffMember(state.staffMember.name) },
                                onMessageButtonClick = { PhonesHelper.openWhatsApp(state.staffMember.name) },
                            )
                            DailySchedule(
                                partnersSchedule = state.staffMember.partnersSchedule,
                                onScheduleClick = { time, partners ->
                                    selectedSchedule = time to partners
                                },
                                onSeeAllClicked = { onNavigateToFullSchedule(state.staffMember) }
                            )
                            AssignedPartners(
                                partners = state.staffMember.partners,
                                onNavigateToPartnerDetail = onNavigateToPartnerDetail,
                            )
                        }
                    }

                    else -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(state.error ?: stringResource(Res.string.staff_member_detail_screen_staff_member_not_found))
                        }
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
                        onDismiss = onDismissNetworkBanner,
                    )
                }
            }
        }
    }
}

@Composable
private fun DailySchedule(
    partnersSchedule: Map<DayOfWeek, Map<LocalTime, List<Partner>>>,
    onScheduleClick: (LocalTime, List<Partner>) -> Unit,
    onSeeAllClicked: () -> Unit,
) {
    val now = DateTimeHandler.getCurrentDateTimeInfo()
    val today = DayOfWeek.valueOf(now.dayOfWeek.name)

    val scrollState = rememberLazyListState()

    val scheduleForToday = partnersSchedule[today]?.entries?.toList()?.sortedBy { it.key }
    val upcomingAppointmentIndex =
        scheduleForToday?.indexOfFirst { (time, _) -> time >= now.time } ?: -1

    LaunchedEffect(upcomingAppointmentIndex) {
        if (upcomingAppointmentIndex != -1) {
            scrollState.smoothScrollTo(upcomingAppointmentIndex)
        }
    }

    Column(
        modifier =
            Modifier
                .background(Color.White.copy(alpha = ALPHA_0_2))
                .padding(vertical = padding_16),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = padding_16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                stringResource(Res.string.staff_member_detail_screen_daily_schedule, DateTimeHandler.getSpanishDayOfWeek(now.dayOfWeek)),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
            Surface(
                onClick = onSeeAllClicked,
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
        Spacer(modifier = Modifier.height(height_16))

        if (partnersSchedule[today].isNullOrEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.staff_member_detail_screen_daily_schedule_empty_state),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_7),
                textAlign = TextAlign.Center,
            )
        } else {
            LazyRow(
                state = scrollState,
                contentPadding = PaddingValues(horizontal = padding_16),
                horizontalArrangement = Arrangement.spacedBy(space_12),
            ) {
                partnersSchedule[today]?.let {
                    items(it.entries.toList()) { (time, partners) ->
                        CemedeCard.WeeklyScheduleCard(
                            time = time,
                            partners = partners,
                            now = now,
                            onCardClick = { onScheduleClick(time, partners) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AssignedPartners(
    partners: List<Partner>,
    onNavigateToPartnerDetail: (Partner) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = padding_16)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.staff_member_detail_screen_partner_list, partners.size),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
            TextButton(
                onClick = { showSearchBar = !showSearchBar },
                enabled = partners.isNotEmpty(),
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Ícono de filtrar",
                    modifier = Modifier.size(size_16),
                )
                Spacer(modifier = Modifier.width(width_4))
                Text(
                    text = stringResource(Res.string.filter),
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
        Spacer(modifier = Modifier.height(height_8))

        if (showSearchBar) {
            CemedeSearchBar.SearchBar(
                modifier = Modifier.padding(vertical = padding_8),
                placeholder = stringResource(Res.string.staff_member_detail_screen_search_bar),
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
            )
        }

        if (partners.isEmpty()) {
            CemedeEmptyState.EmptyState(
                title = stringResource(Res.string.staff_member_detail_screen_empty_state_title),
            )
        } else {
            val filteredPartners =
                partners.filter { partner ->
                    partner.name.contains(searchQuery, ignoreCase = true)
                }

            if (filteredPartners.isEmpty() && searchQuery.isNotEmpty()) {
                CemedeEmptyState.EmptyState(
                    title = stringResource(Res.string.staff_member_detail_screen_empty_state_title),
                    subtitle = stringResource(Res.string.empty_state_subtitle),
                    actionText = stringResource(Res.string.clear_search),
                    onActionClick = { searchQuery = "" },
                )
            } else {
                LazyColumn {
                    items(filteredPartners) { partner ->
                        CemedeCard.PartnerCard(
                            partner = partner,
                            onCardClick = onNavigateToPartnerDetail,
                        )
                        Spacer(modifier = Modifier.height(height_16))
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun StaffMemberDetailScreenPreview() {
    val partners =
        listOf(
            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
            Partner(id = 2, name = "María García", processType = "Deportivo"),
            Partner(id = 3, name = "Lucas Rodríguez", processType = "Salud"),
        )
    val staffMember =
        StaffMember(
            id = 1,
            name = "Prof. Macarena",
            partners = partners,
            partnersSchedule =
                mapOf(
                    DayOfWeek.MONDAY to mapOf(DateTimeHandler.parseTime("12:00:00 PM") to partners),
                    DayOfWeek.TUESDAY to mapOf(DateTimeHandler.parseTime("12:00:00 PM") to partners),
                    DayOfWeek.WEDNESDAY to mapOf(DateTimeHandler.parseTime("12:00:00 PM") to partners),
                    DayOfWeek.THURSDAY to mapOf(DateTimeHandler.parseTime("12:00:00 PM") to partners),
                    DayOfWeek.FRIDAY to mapOf(DateTimeHandler.parseTime("12:00:00 PM") to partners),
                    DayOfWeek.SATURDAY to mapOf(DateTimeHandler.parseTime("21:00:00 PM") to partners),
                    DayOfWeek.SUNDAY to mapOf(DateTimeHandler.parseTime("12:00:00 PM") to partners),
                ),
        )
    StaffMemberDetailContent(
        state = StaffMemberDetailState(staffMember = staffMember, isLoading = false),
        onErrorRetry = {},
        onNavigateBack = {},
        onNavigateToPartnerDetail = {},
        onNavigateToFullSchedule = {},
        onDismissNetworkBanner = {},
    )
}

@Preview(showSystemUi = true)
@Composable
private fun StaffMemberDetailScreenLoadingPreview() {
    StaffMemberDetailContent(
        state = StaffMemberDetailState(isLoading = true),
        onErrorRetry = {},
        onNavigateBack = {},
        onNavigateToPartnerDetail = {},
        onNavigateToFullSchedule = {},
        onDismissNetworkBanner = {},
    )
}

@Preview(showSystemUi = true)
@Composable
private fun StaffMemberDetailScreenErrorPreview() {
    StaffMemberDetailContent(
        state = StaffMemberDetailState(isLoading = false, error = "Error Message"),
        onErrorRetry = {},
        onNavigateBack = {},
        onNavigateToPartnerDetail = {},
        onNavigateToFullSchedule = {},
        onDismissNetworkBanner = {},
    )
}
