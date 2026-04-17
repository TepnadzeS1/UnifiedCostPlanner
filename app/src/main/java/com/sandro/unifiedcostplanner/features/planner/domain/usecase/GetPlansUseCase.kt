package com.sandro.unifiedcostplanner.features.planner.domain.usecase

import com.sandro.unifiedcostplanner.features.planner.domain.model.Plan
import com.sandro.unifiedcostplanner.features.planner.domain.repository.PlannerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlansUseCase @Inject constructor(
    private val repository: PlannerRepository
) {
    // We use 'operator fun invoke' so we can call this like: getPlansUseCase()
    operator fun invoke(): Flow<List<Plan>> {
        return repository.getPlans()
    }
}