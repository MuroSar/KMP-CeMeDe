package com.cemede.cemede.presentation.screen.staff_member_list

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

class StaffMemberListViewModel(
    private val getAllStaffMembersFlowUseCase: GetAllStaffMembersFlowUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(StaffMemberListState())
    val state = _state.asStateFlow()

    init {
        getStaffMembers()
    }

    fun getStaffMembers() {
        viewModelScope.launch {
            getAllStaffMembersFlowUseCase()
                .onEach { staffMembers ->
                    _state.value =
                        _state.value.copy(
                            isLoading = false,
                            staffMembers = staffMembers,
                            error = null,
                        )
                }.catch { error ->
                    _state.value =
                        _state.value.copy(
                            isLoading = false,
                            error = error.message,
                        )
                }.launchIn(viewModelScope)
        }
    }
}

data class StaffMemberListState(
    val staffMembers: List<StaffMember> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
)
