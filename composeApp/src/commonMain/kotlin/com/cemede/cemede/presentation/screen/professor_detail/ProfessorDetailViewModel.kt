package com.cemede.cemede.presentation.screen.professor_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.use_case.GetProfessorDetailFlowUseCase
import com.cemede.cemede.domain.use_case.SyncProfessorScheduleUseCase
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfessorDetailViewModel(
    private val professor: Professor,
    private val getProfessorDetailFlowUseCase: GetProfessorDetailFlowUseCase,
    private val syncProfessorScheduleUseCase: SyncProfessorScheduleUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfessorDetailState())
    val state = _state.asStateFlow()

    init {
        syncProfessorSchedule()
    }

    fun syncProfessorSchedule() =
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = syncProfessorScheduleUseCase(professor)) {
                is CoroutineResult.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    getProfessorDetail(professor.id)
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

    private fun getProfessorDetail(id: Int) =
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getProfessorDetailFlowUseCase(id)
                .onEach { professor ->
                    _state.value = _state.value.copy(professor = professor, isLoading = false)
                }.launchIn(viewModelScope)
        }
}

data class ProfessorDetailState(
    val professor: Professor? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
)
