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
import kotlinx.coroutines.flow.update
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
            _state.update { it.copy(isLoading = true) }
            val message = "Cargando horarios del staff.."
            addMessage(message)

            when (val result = syncStaffMembersWorkingScheduleUseCase()) {
                is CoroutineResult.Success -> {
                    removeMessage(message)
                    syncAllRemainingData()
                }

                is CoroutineResult.Error -> {
                    removeMessage(message)
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Error al sincronizar horarios de trabajo: ${result.message}",
                        )
                    }
                }
            }
        }
    }

    private fun syncAllRemainingData() {
        viewModelScope.launch {
            val syncPartnersInfoMessage = "Cargando socios.."
            val syncAllStaffMembersScheduleMessage = "Cargando disponibilidad horaria del staff.."

            val partnersInfoDeferred =
                async {
                    addMessage(syncPartnersInfoMessage)
                    val result = syncPartnersInfoUseCase()
                    removeMessage(syncPartnersInfoMessage)
                    result
                }
            val allStaffMembersScheduleDeferred =
                async {
                    addMessage(syncAllStaffMembersScheduleMessage)
                    val result = syncAllStaffMembersScheduleUseCase()
                    removeMessage(syncAllStaffMembersScheduleMessage)
                    result
                }

            val partnersResult = partnersInfoDeferred.await()
            val scheduleResult = allStaffMembersScheduleDeferred.await()

            if (partnersResult is CoroutineResult.Error) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al sincronizar información de socios: ${partnersResult.message}",
                    )
                }
                return@launch
            }

            if (scheduleResult is CoroutineResult.Error) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al sincronizar agenda de staff: ${scheduleResult.message}",
                    )
                }
                return@launch
            }

            _state.update {
                it.copy(
                    isLoading = false,
                    isSyncComplete = true,
                )
            }
        }
    }

    private fun addMessage(message: String) {
        _state.update { it.copy(messages = it.messages + message) }
    }

    private fun removeMessage(message: String) {
        _state.update { it.copy(messages = it.messages - message) }
    }
}

data class SplashState(
    val isLoading: Boolean = true,
    val isSyncComplete: Boolean = false,
    val error: String? = null,
    val messages: List<String> = emptyList(),
)
