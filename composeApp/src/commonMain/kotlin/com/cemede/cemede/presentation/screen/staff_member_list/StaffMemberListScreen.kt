package com.cemede.cemede.presentation.screen.staff_member_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import cemede.composeapp.generated.resources.all
import cemede.composeapp.generated.resources.back
import cemede.composeapp.generated.resources.clear_search
import cemede.composeapp.generated.resources.empty_state_subtitle
import cemede.composeapp.generated.resources.resting
import cemede.composeapp.generated.resources.staff_member_list_screen_empty_state_title
import cemede.composeapp.generated.resources.staff_member_list_screen_header_title
import cemede.composeapp.generated.resources.staff_member_list_screen_loading
import cemede.composeapp.generated.resources.staff_member_list_screen_search_bar
import cemede.composeapp.generated.resources.synchronizing_data
import cemede.composeapp.generated.resources.working
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.util.DateTimeHandler
import com.cemede.cemede.presentation.component.CemedeBanner
import com.cemede.cemede.presentation.component.CemedeCard
import com.cemede.cemede.presentation.component.CemedeEmptyState
import com.cemede.cemede.presentation.component.CemedeErrorState
import com.cemede.cemede.presentation.component.CemedeLoader
import com.cemede.cemede.presentation.component.CemedePill
import com.cemede.cemede.presentation.component.CemedeSearchBar
import com.cemede.cemede.presentation.component.CemedeTopAppBar
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.height_16
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_8
import com.cemede.cemede.presentation.theme.space_12
import com.cemede.cemede.presentation.util.PhonesHelper
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun StaffMemberListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToStaffMemberDetail: (StaffMember) -> Unit,
    viewModel: StaffMemberListViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsState()

    StaffMemberListContent(
        isLoading = state.isLoading,
        staffMembers = state.staffMembers,
        errorMessage = state.error,
        onErrorRetry = { viewModel.getStaffMembers() },
        onNavigateBack = onNavigateBack,
        onNavigateToStaffMemberDetail = onNavigateToStaffMemberDetail,
    )
}

private enum class StaffMemberFilter {
    ALL,
    WORKING,
    RESTING,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaffMemberListContent(
    isLoading: Boolean,
    staffMembers: List<StaffMember>,
    errorMessage: String?,
    onErrorRetry: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToStaffMemberDetail: (StaffMember) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(StaffMemberFilter.ALL) }
    var showConstructionBanner by remember { mutableStateOf(false) }

    val now = DateTimeHandler.getCurrentDateTimeInfo()
    val today = DayOfWeek.valueOf(now.dayOfWeek.name)
    val currentHour = LocalTime(now.hour, 0)

    CemedeTheme {
        Scaffold(
            topBar = {
                StaffMemberListTopAppBar(
                    onNavigateBack = onNavigateBack,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    searchBarEnabled = !isLoading && errorMessage == null,
                )
            },
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                if (isLoading) {
                    CemedeLoader(
                        title = stringResource(Res.string.synchronizing_data),
                        subtitle = stringResource(Res.string.staff_member_list_screen_loading),
                    )
                } else {
                    if (errorMessage != null) {
                        CemedeErrorState.ErrorState(
                            onRetryClick = onErrorRetry,
                        )
                    } else {
                        Column {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = padding_16, vertical = padding_8),
                                horizontalArrangement = Arrangement.spacedBy(space_12),
                            ) {
                                item {
                                    CemedePill(
                                        text = stringResource(Res.string.all),
                                        isSelected = selectedFilter == StaffMemberFilter.ALL,
                                        onClick = { selectedFilter = StaffMemberFilter.ALL },
                                    )
                                }
                                item {
                                    CemedePill(
                                        text = stringResource(Res.string.working),
                                        isSelected = selectedFilter == StaffMemberFilter.WORKING,
                                        onClick = { selectedFilter = StaffMemberFilter.WORKING },
                                    )
                                }
                                item {
                                    CemedePill(
                                        text = stringResource(Res.string.resting),
                                        isSelected = selectedFilter == StaffMemberFilter.RESTING,
                                        onClick = { selectedFilter = StaffMemberFilter.RESTING },
                                    )
                                }
                            }

                            val filteredStaffMembers =
                                staffMembers.filter { prof ->
                                    val matchesSearch = prof.name.contains(searchQuery, ignoreCase = true)
                                    val isWorkingNow = prof.staffMemberWorkingSchedule[today]?.contains(currentHour) == true
                                    val matchesFilter =
                                        when (selectedFilter) {
                                            StaffMemberFilter.ALL -> true
                                            StaffMemberFilter.WORKING -> isWorkingNow
                                            StaffMemberFilter.RESTING -> !isWorkingNow
                                        }
                                    matchesSearch && matchesFilter
                                }

                            if (filteredStaffMembers.isEmpty()) {
                                CemedeEmptyState.EmptyState(
                                    title = stringResource(Res.string.staff_member_list_screen_empty_state_title),
                                    subtitle = stringResource(Res.string.empty_state_subtitle),
                                    actionText =
                                        if (searchQuery.isNotEmpty() || selectedFilter != StaffMemberFilter.ALL) {
                                            stringResource(Res.string.clear_search)
                                        } else {
                                            ""
                                        },
                                    onActionClick = {
                                        searchQuery = ""
                                        selectedFilter = StaffMemberFilter.ALL
                                    },
                                )
                            } else {
                                LazyColumn(modifier = Modifier.padding(horizontal = padding_16)) {
                                    items(filteredStaffMembers) { staffMember ->
                                        CemedeCard.StaffMemberCard(
                                            staffMember = staffMember,
                                            onCardClick = { onNavigateToStaffMemberDetail(staffMember) },
                                            onCallButtonClick = { PhonesHelper.callStaffMember(staffMember.name) },
                                            onMessageButtonClick = { PhonesHelper.openWhatsApp(staffMember.name) },
                                            onScheduleButtonClick = { showConstructionBanner = true },
                                        )
                                        Spacer(modifier = Modifier.height(height_16))
                                    }
                                }
                            }
                        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StaffMemberListTopAppBar(
    onNavigateBack: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    searchBarEnabled: Boolean,
) {
    Column {
        CemedeTopAppBar.TopAppBar(
            title = stringResource(Res.string.staff_member_list_screen_header_title),
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(Res.string.back),
                    )
                }
            },
        )

        CemedeSearchBar.SearchBar(
            modifier = Modifier.padding(horizontal = padding_16, vertical = padding_8),
            placeholder = stringResource(Res.string.staff_member_list_screen_search_bar),
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            enabled = searchBarEnabled,
        )
    }
}

@Preview(showSystemUi = true, name = "Default Preview")
@Composable
private fun StaffMemberListScreenPreview() {
    val sampleStaffMembers =
        listOf(
            StaffMember(1, "Macarena"),
            StaffMember(2, "Tomas"),
            StaffMember(3, "Gonzalo"),
            StaffMember(4, "Santiago"),
        )
    StaffMemberListContent(
        isLoading = false,
        staffMembers = sampleStaffMembers,
        errorMessage = null,
        onErrorRetry = {},
        onNavigateBack = {},
        onNavigateToStaffMemberDetail = {},
    )
}

@Preview(showSystemUi = true)
@Composable
private fun StaffMemberListScreenEmptyPreview() {
    StaffMemberListContent(
        isLoading = false,
        staffMembers = listOf(),
        errorMessage = null,
        onErrorRetry = {},
        onNavigateBack = {},
        onNavigateToStaffMemberDetail = {},
    )
}

@Preview(showSystemUi = true)
@Composable
private fun StaffMemberListScreenLoadingPreview() {
    StaffMemberListContent(
        isLoading = true,
        staffMembers = listOf(),
        errorMessage = null,
        onErrorRetry = {},
        onNavigateBack = {},
        onNavigateToStaffMemberDetail = {},
    )
}

@Preview(showSystemUi = true)
@Composable
private fun StaffMemberListScreenErrorPreview() {
    StaffMemberListContent(
        isLoading = false,
        staffMembers = listOf(),
        errorMessage = "ErrorMessage",
        onErrorRetry = {},
        onNavigateBack = {},
        onNavigateToStaffMemberDetail = {},
    )
}
