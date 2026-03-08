package com.cemede.cemede.presentation.screen.professor_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.back
import cemede.composeapp.generated.resources.clear_search
import cemede.composeapp.generated.resources.empty_state_subtitle
import cemede.composeapp.generated.resources.professor_list_screen_empty_state_title
import cemede.composeapp.generated.resources.professor_list_screen_header_title
import cemede.composeapp.generated.resources.professor_list_screen_loading
import cemede.composeapp.generated.resources.professor_list_screen_search_bar
import cemede.composeapp.generated.resources.synchronizing_data
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.presentation.component.CemedeBanner
import com.cemede.cemede.presentation.component.CemedeCard
import com.cemede.cemede.presentation.component.CemedeEmptyState
import com.cemede.cemede.presentation.component.CemedeLoader
import com.cemede.cemede.presentation.component.CemedeSearchBar
import com.cemede.cemede.presentation.component.CemedeTopAppBar
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_8
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun ProfessorListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToProfessorDetail: (Professor) -> Unit,
    viewModel: ProfessorListViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsState()

    ProfessorListContent(
        isLoading = state.isLoading,
        professors = state.professors,
        onNavigateBack = onNavigateBack,
        onNavigateToProfessorDetail = onNavigateToProfessorDetail,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessorListContent(
    isLoading: Boolean,
    professors: List<Professor>,
    onNavigateBack: () -> Unit,
    onNavigateToProfessorDetail: (Professor) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    var showConstructionBanner by remember { mutableStateOf(false) }

    CemedeTheme {
        Scaffold(
            topBar = {
                ProfessorListTopAppBar(
                    onNavigateBack = onNavigateBack,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    searchBarEnabled = !isLoading,
                )
            },
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                if (isLoading) {
                    CemedeLoader(
                        title = stringResource(Res.string.synchronizing_data),
                        subtitle = stringResource(Res.string.professor_list_screen_loading),
                    )
                } else {
                    val filteredProfessors =
                        professors.filter { prof ->
                            prof.name.contains(searchQuery, ignoreCase = true)
                        }

                    if (filteredProfessors.isEmpty() && searchQuery.isNotEmpty()) {
                        CemedeEmptyState.EmptyState(
                            title = stringResource(Res.string.professor_list_screen_empty_state_title),
                            subtitle = stringResource(Res.string.empty_state_subtitle),
                            actionText = stringResource(Res.string.clear_search),
                            onActionClick = { searchQuery = "" },
                        )
                    } else {
                        LazyColumn(modifier = Modifier.padding(horizontal = padding_16)) {
                            items(filteredProfessors) { professor ->
                                CemedeCard.ProfessorCard(
                                    professor = professor,
                                    onCardClick = { onNavigateToProfessorDetail(professor) },
                                    onCallButtonClick = { showConstructionBanner = true },
                                    onMessageButtonClick = { showConstructionBanner = true },
                                    onScheduleButtonClick = { showConstructionBanner = true },
                                )
                                Spacer(modifier = Modifier.height(16.dp))
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
private fun ProfessorListTopAppBar(
    onNavigateBack: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    searchBarEnabled: Boolean,
) {
    Column {
        CemedeTopAppBar.TopAppBar(
            title = stringResource(Res.string.professor_list_screen_header_title),
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
            placeholder = stringResource(Res.string.professor_list_screen_search_bar),
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            enabled = searchBarEnabled,
        )
    }
}

@Preview(showSystemUi = true, name = "Default Preview")
@Composable
private fun ProfessorListScreenPreview() {
    val sampleProfessors =
        listOf(
            Professor(1, "Macarena", true),
            Professor(2, "Juan", true),
            Professor(3, "Sofia", false),
            Professor(4, "Carlos", true),
        )
    ProfessorListContent(
        isLoading = false,
        professors = sampleProfessors,
        onNavigateBack = {},
        onNavigateToProfessorDetail = {},
    )
}

@Preview(showSystemUi = true, name = "Loading Preview")
@Composable
private fun ProfessorListScreenLoadingPreview() {
    ProfessorListContent(
        isLoading = true,
        professors = emptyList(),
        onNavigateBack = {},
        onNavigateToProfessorDetail = {},
    )
}

@Preview(showSystemUi = true, name = "Empty Search Preview")
@Composable
private fun ProfessorListScreenEmptySearchPreview() {
    val sampleProfessors =
        listOf(
            Professor(1, "Macarena", true),
            Professor(2, "Juan", true),
        )
    ProfessorListContent(
        isLoading = false,
        professors = sampleProfessors,
        onNavigateBack = {},
        onNavigateToProfessorDetail = {},
    )
}
