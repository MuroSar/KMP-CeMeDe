package com.cemede.cemede.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.use_case.GetAllStaffMembersFlowUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllStaffMembersFlowUseCase: GetAllStaffMembersFlowUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        getStaffMembers()
    }

    private fun getStaffMembers() {
        viewModelScope.launch {
            getAllStaffMembersFlowUseCase()
                .onEach { staffMembers ->
                    _state.value = _state.value.copy(staffMembers = staffMembers)
                }.catch { error ->
                    _state.value = _state.value.copy(error = error.message)
                }.launchIn(viewModelScope)
        }
    }
}

data class MainState(
    val staffMembers: List<StaffMember> = emptyList(),
    // TODO: REVISAR
    val isLoading: Boolean = true,
    val error: String? = null,
)
