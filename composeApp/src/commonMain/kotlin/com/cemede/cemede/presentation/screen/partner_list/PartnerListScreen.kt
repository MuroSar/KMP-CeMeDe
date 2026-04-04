package com.cemede.cemede.presentation.screen.partner_list

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.all
import cemede.composeapp.generated.resources.back
import cemede.composeapp.generated.resources.clear_search
import cemede.composeapp.generated.resources.empty_state_subtitle
import cemede.composeapp.generated.resources.partner_list_screen_empty_state_title
import cemede.composeapp.generated.resources.partner_list_screen_header_title
import cemede.composeapp.generated.resources.partner_list_screen_loading
import cemede.composeapp.generated.resources.partner_list_screen_search_bar
import cemede.composeapp.generated.resources.synchronizing_data
import com.cemede.cemede.domain.model.Partner
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
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun PartnerListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPartnerDetail: (Partner) -> Unit,
    viewModel: PartnerListViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsState()

    PartnerListContent(
        isLoading = state.isLoading,
        partners = state.partners,
        errorMessage = state.error,
        onErrorRetry = { viewModel.getPartners() },
        onNavigateToPartnerDetail = onNavigateToPartnerDetail,
        onNavigateBack = onNavigateBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartnerListContent(
    isLoading: Boolean,
    partners: List<Partner>,
    errorMessage: String?,
    onErrorRetry: () -> Unit,
    onNavigateToPartnerDetail: (Partner) -> Unit,
    onNavigateBack: () -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedProcessType by remember { mutableStateOf<String?>(null) }

    val processTypes =
        remember(partners) {
            partners.map { it.processType }.distinct().filter { it.isNotBlank() }
        }

    CemedeTheme {
        Scaffold(
            topBar = {
                PartnerListTopAppBar(
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
                        subtitle = stringResource(Res.string.partner_list_screen_loading),
                    )
                } else {
                    if (errorMessage != null) {
                        CemedeErrorState.ErrorState(
                            onRetryClick = onErrorRetry,
                        )
                    } else {
                        Column {
                            // Filter Pills
                            if (processTypes.isNotEmpty()) {
                                LazyRow(
                                    contentPadding = PaddingValues(horizontal = padding_16, vertical = padding_8),
                                    horizontalArrangement = Arrangement.spacedBy(space_12),
                                ) {
                                    item {
                                        CemedePill(
                                            text = stringResource(Res.string.all),
                                            isSelected = selectedProcessType == null,
                                            onClick = { selectedProcessType = null },
                                        )
                                    }
                                    items(processTypes) { type ->
                                        CemedePill(
                                            text = type,
                                            isSelected = selectedProcessType == type,
                                            onClick = { selectedProcessType = type },
                                        )
                                    }
                                }
                            }

                            val filteredPartners =
                                partners.filter { partner ->
                                    val matchesSearch = partner.name.contains(searchQuery, ignoreCase = true)
                                    val matchesType = selectedProcessType == null || partner.processType == selectedProcessType
                                    matchesSearch && matchesType
                                }

                            if (filteredPartners.isEmpty()) {
                                CemedeEmptyState.EmptyState(
                                    title = stringResource(Res.string.partner_list_screen_empty_state_title),
                                    subtitle = stringResource(Res.string.empty_state_subtitle),
                                    actionText =
                                        if (searchQuery.isNotEmpty() || selectedProcessType != null) {
                                            stringResource(Res.string.clear_search)
                                        } else {
                                            ""
                                        },
                                    onActionClick = {
                                        searchQuery = ""
                                        selectedProcessType = null
                                    },
                                )
                            } else {
                                LazyColumn(
                                    modifier = Modifier.padding(horizontal = padding_16),
                                    contentPadding = PaddingValues(vertical = padding_8),
                                ) {
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
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PartnerListTopAppBar(
    onNavigateBack: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    searchBarEnabled: Boolean,
) {
    Column {
        CemedeTopAppBar.TopAppBar(
            title = stringResource(Res.string.partner_list_screen_header_title),
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
            placeholder = stringResource(Res.string.partner_list_screen_search_bar),
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            enabled = searchBarEnabled,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PartnerListScreenPreview() {
    val samplePartners =
        listOf(
            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
            Partner(id = 2, name = "María García", processType = "Deportivo"),
            Partner(id = 3, name = "Lucas Rodríguez", processType = "Salud"),
            Partner(id = 4, name = "Ana Martínez", processType = "Readaptacion"),
        )
    PartnerListContent(
        isLoading = false,
        partners = samplePartners,
        errorMessage = null,
        onErrorRetry = {},
        onNavigateToPartnerDetail = {},
        onNavigateBack = {},
    )
}

@Preview(showSystemUi = true)
@Composable
private fun PartnerListScreenEmptyPreview() {
    PartnerListContent(
        isLoading = false,
        partners = listOf(),
        errorMessage = null,
        onErrorRetry = {},
        onNavigateToPartnerDetail = {},
        onNavigateBack = {},
    )
}

@Preview(showSystemUi = true)
@Composable
private fun PartnerListScreenLoadingPreview() {
    PartnerListContent(
        isLoading = true,
        partners = listOf(),
        errorMessage = null,
        onErrorRetry = {},
        onNavigateToPartnerDetail = {},
        onNavigateBack = {},
    )
}

@Preview(showSystemUi = true)
@Composable
private fun PartnerListScreenErrorPreview() {
    PartnerListContent(
        isLoading = false,
        partners = listOf(),
        errorMessage = "ErrorMessage",
        onErrorRetry = {},
        onNavigateToPartnerDetail = {},
        onNavigateBack = {},
    )
}
