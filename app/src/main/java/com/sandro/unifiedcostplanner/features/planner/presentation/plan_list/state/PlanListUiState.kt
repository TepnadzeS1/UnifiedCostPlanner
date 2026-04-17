package com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.state

import com.sandro.unifiedcostplanner.features.planner.domain.model.Plan

data class PlanListUiState(
    val isLoading: Boolean = false,
    val plans: List<Plan> = emptyList(),
    val errorMessage: String? = null
)