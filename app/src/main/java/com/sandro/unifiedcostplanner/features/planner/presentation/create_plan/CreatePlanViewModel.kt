package com.sandro.unifiedcostplanner.features.planner.presentation.create_plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandro.unifiedcostplanner.features.planner.domain.model.Plan
import com.sandro.unifiedcostplanner.features.planner.domain.repository.PlannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePlanViewModel @Inject constructor(
    private val repository: PlannerRepository
) : ViewModel() {

    fun savePlan(title: String, description: String, category: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            val newPlan = Plan(
                title = title,
                description = description.ifBlank { null },
                category = category,
                // Total cost starts at 0 automatically!
            )
            repository.savePlan(newPlan)
            onComplete() // Tells the UI to navigate back
        }
    }
}