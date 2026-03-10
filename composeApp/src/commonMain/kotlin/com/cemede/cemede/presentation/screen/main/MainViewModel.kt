package com.cemede.cemede.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.use_case.GetAllProfessorsFlowUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllProfessorsFlowUseCase: GetAllProfessorsFlowUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        getProfessors()
    }

    private fun getProfessors() {
        viewModelScope.launch {
            getAllProfessorsFlowUseCase()
                .onEach { professors ->
                    _state.value = _state.value.copy(professors = professors)
                }.catch { error ->
                    _state.value = _state.value.copy(error = error.message)
                }.launchIn(viewModelScope)
        }
    }
}

data class MainState(
    val professors: List<Professor> = emptyList(),
    // TODO: REVISAR
    val isLoading: Boolean = true,
    val error: String? = null,
)
