package com.cemede.cemede.presentation.screen.partner_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cemede.cemede.domain.model.Partner
import com.cemede.cemede.domain.use_case.GetAllPartnersUseCase
import com.cemede.cemede.domain.use_case.SyncPartnersInfoUseCase
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PartnerListViewModel(
    private val syncPartnersInfoUseCase: SyncPartnersInfoUseCase,
    private val getAllPartnersFlowUseCase: GetAllPartnersUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(PartnerListState())
    val state = _state.asStateFlow()

    init {
        syncPartnersInfo()
    }

    private fun syncPartnersInfo() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = syncPartnersInfoUseCase()) {
                is CoroutineResult.Success -> {
                    getPartners()
                }

                is CoroutineResult.Error -> {
                    _state.value =
                        _state.value.copy(
                            isLoading = false,
                            error = result.message,
                        )
                }
            }
        }
    }

    private fun getPartners() {
        viewModelScope.launch {
            getAllPartnersFlowUseCase()
                .onEach { partners ->
                    _state.value =
                        _state.value.copy(
                            isLoading = false,
                            partners = partners,
                        )
                }.catch { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.message,
                    )
                }.launchIn(viewModelScope)
        }
    }
}

data class PartnerListState(
    val partners: List<Partner> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
)
