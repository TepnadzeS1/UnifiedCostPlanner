package com.sandro.unifiedcostplanner.features.planner.domain.model

data class Plan(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val description: String?,
    val category: String = "General", // To match your 'Operations', 'Marketing' chips
    val startDate: Long? = null,     // For the "Oct 12 - Oct 20" display
    val endDate: Long? = null,
    val items: List<PlannerItem> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
) {
    val totalCost: Double get() = items.sumOf { it.unitPrice * it.quantity }
}