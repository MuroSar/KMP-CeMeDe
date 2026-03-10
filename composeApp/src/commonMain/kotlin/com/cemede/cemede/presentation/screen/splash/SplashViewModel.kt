package com.cemede.cemede.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.use_case.SyncProfessorsWorkingScheduleUseCase
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SplashViewModel(
    private val syncProfessorsWorkingScheduleUseCase: SyncProfessorsWorkingScheduleUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    init {
        syncProfessorsWorkingSchedule()
    }

    private fun syncProfessorsWorkingSchedule() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = syncProfessorsWorkingScheduleUseCase()) {
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
