package com.sandro.unifiedcostplanner.features.planner.presentation.create_plan.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandro.unifiedcostplanner.features.planner.domain.model.Plan
import com.sandro.unifiedcostplanner.features.planner.domain.repository.PlannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreatePlanViewModel @Inject constructor(
    private val repository: PlannerRepository
) : ViewModel() {

    var title by mutableStateOf("")
    var description by mutableStateOf("")

    fun savePlan(onSuccess: () -> Unit) {
        if (title.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newPlan = Plan(
                    title = title,
                    description = description,
                    category = "General",
                    startDate = System.currentTimeMillis(),
                    endDate = System.currentTimeMillis()
                )

                repository.savePlan(newPlan)

                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}