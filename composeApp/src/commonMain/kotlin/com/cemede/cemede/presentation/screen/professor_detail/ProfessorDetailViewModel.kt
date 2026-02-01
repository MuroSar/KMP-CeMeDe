package com.cemede.cemede.presentation.screen.professor_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.use_case.GetProfessorDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProfessorDetailViewModel(
    private val getProfessorDetailUseCase: GetProfessorDetailUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfessorDetailState())
    val state = _state.asStateFlow()

    fun getProfessor(name: String) {
        getProfessorDetailUseCase(name)
            .onEach { professor ->
                _state.value = _state.value.copy(professor = professor)
            }.launchIn(viewModelScope)
    }
}

data class ProfessorDetailState(
    val professor: Professor? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
