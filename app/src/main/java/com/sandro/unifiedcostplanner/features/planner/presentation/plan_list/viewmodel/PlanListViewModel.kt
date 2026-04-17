package com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandro.unifiedcostplanner.features.planner.domain.usecase.GetPlansUseCase
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.state.PlanListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanListViewModel @Inject constructor(
    private val getPlansUseCase: GetPlansUseCase
) : ViewModel() {

    // We use MutableStateFlow to hold the UI state
    private val _state = MutableStateFlow(PlanListUiState())
    // The UI can only read the state, never change it directly (Encapsulation)
    val state: StateFlow<PlanListUiState> = _state.asStateFlow()

    init {
        loadPlans()
    }

    private fun loadPlans() {
        _state.update { it.copy(isLoading = true) }

        // We observe the Flow from the Domain layer
        getPlansUseCase()
            .onEach { plans ->
                _state.update {
                    it.copy(isLoading = false, plans = plans)
                }
            }
            .catch { error ->
                _state.update {
                    it.copy(isLoading = false, errorMessage = error.message)
                }
            }
            .launchIn(viewModelScope) // Automatically cancels if the user leaves the screen
    }
}