package com.sandro.unifiedcostplanner.features.planner.domain.usecase

import com.sandro.unifiedcostplanner.features.planner.domain.model.PlannerItem

/**
 * Encapsulates the logic for calculating the total of a list of items.
 * Even though the Plan model has a totalCost property, this Use Case
 * allows us to calculate potential totals before an item is even added to a plan.
 */
class GetPlanTotalUseCase {
    operator fun invoke(items: List<PlannerItem>): Double {
        return items.sumOf { it.subtotal }
    }
}