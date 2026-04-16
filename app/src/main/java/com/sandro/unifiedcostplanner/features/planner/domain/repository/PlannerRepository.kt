package com.sandro.unifiedcostplanner.features.planner.domain.repository

import com.sandro.unifiedcostplanner.features.planner.domain.model.Plan
import com.sandro.unifiedcostplanner.features.planner.domain.model.PlannerItem
import kotlinx.coroutines.flow.Flow

interface PlannerRepository {
    // We use Flow so the UI updates automatically when data changes
    fun getPlans(): Flow<List<Plan>>

    suspend fun getPlanById(id: String): Plan?

    suspend fun savePlan(plan: Plan)

    suspend fun addItemToPlan(planId: String, item: PlannerItem)

    suspend fun deletePlan(planId: String)
}