package com.cemede.cemede.presentation.screen.professor_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.use_case.GetAllProfessorsUseCase
import com.cemede.cemede.domain.use_case.SyncProfessorsUseCase
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfessorListViewModel(
    private val getAllProfessorsUseCase: GetAllProfessorsUseCase,
    private val syncProfessorsUseCase: SyncProfessorsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfessorListState())
    val state = _state.asStateFlow()

    init {
        syncProfessors()
    }

    fun syncProfessors() = viewModelScope.launch {
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

    private fun getProfessors() {
        getAllProfessorsUseCase()
            .onEach { professors ->
                _state.value = _state.value.copy(professors = professors)
            }.launchIn(viewModelScope)
    }
}

data class ProfessorListState(
    val professors: List<Professor> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
