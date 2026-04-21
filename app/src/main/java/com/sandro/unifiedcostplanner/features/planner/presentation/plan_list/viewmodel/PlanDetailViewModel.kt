package com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandro.unifiedcostplanner.features.planner.domain.model.Plan
import com.sandro.unifiedcostplanner.features.planner.domain.repository.PlannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanDetailViewModel @Inject constructor(
    private val repository: PlannerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow<Plan?>(null)
    val state = _state.asStateFlow()

    init {
        val id: String? = savedStateHandle["planId"]
        id?.let { loadData(it) }
    }

    private fun loadData(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val plan = repository.getPlanById(id)
            _state.value = plan
        }
    }
}