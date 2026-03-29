package com.cemede.cemede.presentation.screen.partner_list

import androidx.lifecycle.ViewModel
import com.cemede.cemede.domain.model.Partner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PartnerListViewModel : ViewModel() {
    private val _state = MutableStateFlow(PartnerListState())
    val state = _state.asStateFlow()
}

data class PartnerListState(
    val partners: List<Partner> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
)
