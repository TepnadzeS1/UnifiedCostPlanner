package com.sandro.unifiedcostplanner.features.catalog.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandro.unifiedcostplanner.features.planner.domain.repository.PlannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val repository: PlannerRepository
) : ViewModel() {

    // 1. Get all plans so the user can choose which one to add the item to
    val plans = repository.getPlans()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 2. Save the item to the selected plan
    fun addCatalogItemToPlan(
        planId: String,
        name: String,
        unitPrice: Double,
        quantity: Int,
        category: String
    ) {
        viewModelScope.launch {
            repository.addExpenseToPlan(
                planId = planId,
                name = name,
                unitPrice = unitPrice,
                quantity = quantity,
                notes = "Added from Local Catalog", // Automatically tag it!
                category = "CATALOG" // Fits your Figma 'CATALOG' badge!
            )
        }
    }
}