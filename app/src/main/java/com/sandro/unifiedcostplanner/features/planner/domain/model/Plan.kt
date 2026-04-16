package com.sandro.unifiedcostplanner.features.planner.domain.model

data class Plan(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String? = null,
    val items: List<PlannerItem> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
) {
    // Business Rule: Total is the sum of all item subtotals
    val totalCost: Double get() = items.sumOf { it.subtotal }
}