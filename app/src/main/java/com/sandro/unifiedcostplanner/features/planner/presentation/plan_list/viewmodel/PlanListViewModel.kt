package com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandro.unifiedcostplanner.features.planner.domain.repository.PlannerRepository
import com.sandro.unifiedcostplanner.features.planner.domain.usecase.GetPlansUseCase
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.state.PlanListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanListViewModel @Inject constructor(
    private val getPlansUseCase: GetPlansUseCase,
    private val repository: PlannerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PlanListUiState())
    val state: StateFlow<PlanListUiState> = _state.asStateFlow()

    init {
        loadPlans()
    }

    private fun loadPlans() {
        _state.update { it.copy(isLoading = true) }

        getPlansUseCase()
            .distinctUntilChanged() // Don't trigger UI updates if the list is the same
            .onEach { plans ->
                _state.update {
                    it.copy(isLoading = false, plans = plans)
                }
            }
            .catch { error ->
                _state.update { it.copy(isLoading = false, errorMessage = error.message) }
            }
            .launchIn(viewModelScope)
    }

    fun deletePlan(planId: String) {
        viewModelScope.launch {
            repository.deletePlan(planId)
        }
    }
}