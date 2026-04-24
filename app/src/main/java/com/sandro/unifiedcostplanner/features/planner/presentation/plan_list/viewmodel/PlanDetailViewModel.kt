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

    // Store the ID so we know which plan to add items to
    private val currentPlanId: String? = savedStateHandle["planId"]

    init {
        currentPlanId?.let { loadPlan(it) }
    }

    private fun loadPlan(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val plan = repository.getPlanById(id)
            _state.value = plan
        }
    }

    // 🚀 NEW: The Bridge function
    fun addExpense(name: String, unitPrice: Double, quantity: Int, notes: String, category: String) {
        val planId = currentPlanId ?: return

        viewModelScope.launch(Dispatchers.IO) {
            // 1. Save to Database
            repository.addExpenseToPlan(planId, name, unitPrice, quantity, notes, category)

            // 2. Refresh the UI to show the new item and updated total!
            loadPlan(planId)
        }
    }

    fun deleteExpense(itemId: String) {
        val planId = currentPlanId ?: return
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(itemId)
            loadPlan(planId)
        }
    }
}