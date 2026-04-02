package com.cemede.cemede.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
) : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    init {
        syncInitialData()
    }

    private fun syncInitialData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val staffWorkingScheduleSyncDeferred = async { syncStaffMembersWorkingScheduleUseCase() }
            val partnersSyncDeferred = async { syncPartnersInfoUseCase() }

            val staffWorkingScheduleResult = staffWorkingScheduleSyncDeferred.await()
            val partnersResult = partnersSyncDeferred.await()

            val errorMessage = when {
                staffWorkingScheduleResult is CoroutineResult.Error -> staffWorkingScheduleResult.message
                partnersResult is CoroutineResult.Error -> partnersResult.message
                else -> null
            }

            _state.value = _state.value.copy(
                isLoading = false,
                error = errorMessage
            )
        }
    }
}

data class SplashState(
    val isLoading: Boolean = true,
    val error: String? = null,
)
