package com.cemede.cemede.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cemede.cemede.domain.use_case.SyncAllStaffMembersScheduleUseCase
import com.cemede.cemede.domain.use_case.SyncPartnersInfoUseCase
import com.cemede.cemede.domain.use_case.SyncStaffMembersWorkingScheduleUseCase
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val syncStaffMembersWorkingScheduleUseCase: SyncStaffMembersWorkingScheduleUseCase,
    private val syncPartnersInfoUseCase: SyncPartnersInfoUseCase,
    private val syncAllStaffMembersScheduleUseCase: SyncAllStaffMembersScheduleUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    init {
        syncStaffMembersWorkingSchedule()
    }

    private fun syncStaffMembersWorkingSchedule() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = syncStaffMembersWorkingScheduleUseCase()) {
                is CoroutineResult.Success -> {
                    syncAllRemainingData()
                }

                is CoroutineResult.Error -> {
                    _state.value =
                        _state.value.copy(
                            isLoading = false,
                            error = "Error al sincronizar horarios de trabajo: ${result.message}",
                        )
                }
            }
        }
    }

    private fun syncAllRemainingData() {
        viewModelScope.launch {
            val partnersInfoDeferred = async { syncPartnersInfoUseCase() }
            val allStaffMembersScheduleDeferred = async { syncAllStaffMembersScheduleUseCase() }

            val partnersResult = partnersInfoDeferred.await()
            val scheduleResult = allStaffMembersScheduleDeferred.await()

            if (partnersResult is CoroutineResult.Error) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Error al sincronizar información de socios: ${partnersResult.message}"
                )
                return@launch
            }

            if (scheduleResult is CoroutineResult.Error) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Error al sincronizar agenda de staff: ${scheduleResult.message}"
                )
                return@launch
            }

            _state.value = _state.value.copy(
                isLoading = false,
                isSyncComplete = true
            )
        }
    }
}

data class SplashState(
    val isLoading: Boolean = true,
    val isSyncComplete: Boolean = false,
    val error: String? = null,
)
