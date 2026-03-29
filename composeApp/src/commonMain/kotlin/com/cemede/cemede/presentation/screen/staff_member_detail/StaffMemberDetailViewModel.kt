package com.cemede.cemede.presentation.screen.staff_member_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.use_case.GetStaffMemberDetailFlowUseCase
import com.cemede.cemede.domain.use_case.SyncStaffMemberInfoUseCase
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class StaffMemberDetailViewModel(
    private val staffMember: StaffMember,
    private val getStaffMemberDetailFlowUseCase: GetStaffMemberDetailFlowUseCase,
    private val syncStaffMemberInfoUseCase: SyncStaffMemberInfoUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(StaffMemberDetailState())
    val state = _state.asStateFlow()

    init {
        syncStaffMemberInfo()
    }

    fun syncStaffMemberInfo() =
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = syncStaffMemberInfoUseCase(staffMember)) {
                is CoroutineResult.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    getStaffMemberDetail(staffMember.id)
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

    private fun getStaffMemberDetail(id: Int) =
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getStaffMemberDetailFlowUseCase(id)
                .onEach { staffMember ->
                    _state.value = _state.value.copy(staffMember = staffMember, isLoading = false)
                }.catch { error ->
                    _state.value = _state.value.copy(isLoading = false, error = error.message)
                }.launchIn(viewModelScope)
        }
}

data class StaffMemberDetailState(
    val staffMember: StaffMember? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
)
