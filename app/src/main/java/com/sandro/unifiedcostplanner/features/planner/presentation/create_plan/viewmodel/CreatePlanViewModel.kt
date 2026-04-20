package com.sandro.unifiedcostplanner.features.planner.presentation.create_plan.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    // Simple state for the input fields
    var title by mutableStateOf("")
    var description by mutableStateOf("")

    fun savePlan(onSuccess: () -> Unit) {
        if (title.isBlank()) return

        viewModelScope.launch {
            val newPlan = Plan(
                title = title,
                description = description
            )
            repository.savePlan(newPlan)
            onSuccess() // Navigate back after saving
        }
    }
}