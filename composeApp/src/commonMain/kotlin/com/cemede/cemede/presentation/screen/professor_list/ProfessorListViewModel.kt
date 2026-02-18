package com.cemede.cemede.presentation.screen.professor_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.use_case.GetAllProfessorsFlowUseCase
import com.cemede.cemede.domain.use_case.SyncProfessorsUseCase
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfessorListViewModel(
    private val getAllProfessorsFlowUseCase: GetAllProfessorsFlowUseCase,
    private val syncProfessorsUseCase: SyncProfessorsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfessorListState())
    val state = _state.asStateFlow()

    init {
        syncProfessors()
    }

    fun syncProfessors() =
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = syncProfessorsUseCase()) {
                is CoroutineResult.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    getProfessors()
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

    private suspend fun getProfessors() {
        getAllProfessorsFlowUseCase()
            .onEach { professors ->
                _state.value = _state.value.copy(professors = professors)
            }.launchIn(viewModelScope)
    }
}

data class ProfessorListState(
    val professors: List<Professor> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
)
