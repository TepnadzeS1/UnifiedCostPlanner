package com.sandro.unifiedcostplanner.features.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandro.unifiedcostplanner.features.planner.domain.repository.PlannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: PlannerRepository
) : ViewModel() {

    // Get all plans for the Plan Selector Dialog
    val plans = repository.getPlans()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Save the item to the selected plan
    fun addExternalItemToPlan(
        planId: String,
        name: String,
        unitPrice: Double,
        quantity: Int,
        platform: String
    ) {
        viewModelScope.launch {
            repository.addExpenseToPlan(
                planId = planId,
                name = name,
                unitPrice = unitPrice,
                quantity = quantity,
                notes = "Sourced from external search ($platform)",
                category = "EBAY" // This perfectly matches your blue EBAY tag in the Figma!
            )
        }
    }
}