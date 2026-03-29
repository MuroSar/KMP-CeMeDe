package com.cemede.cemede.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cemede.cemede.domain.use_case.SyncStaffMembersWorkingScheduleUseCase
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val syncStaffMembersWorkingScheduleUseCase: SyncStaffMembersWorkingScheduleUseCase,
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
                    _state.value = _state.value.copy(isLoading = false)
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
}

data class SplashState(
    val isLoading: Boolean = true,
    val error: String? = null,
)
