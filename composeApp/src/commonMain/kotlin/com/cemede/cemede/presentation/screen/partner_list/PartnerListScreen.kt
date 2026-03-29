package com.cemede.cemede.presentation.screen.partner_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.cemede.cemede.domain.model.Partner
import org.koin.compose.koinInject

@Composable
fun PartnerListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPartnerDetail: (Partner) -> Unit,
    viewModel: PartnerListViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsState()
}
