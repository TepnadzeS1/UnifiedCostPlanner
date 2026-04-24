package com.sandro.unifiedcostplanner.features.planner.domain.model

import java.util.UUID

data class PlannerItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val quantity: Int,
    val unitPrice: Double,
    val currency: String = "GEL",
    val source: SourceType,
    val imageUrl: String? = null,
    val externalUrl: String? = null,
    val notes: String? = null
) {
    val subtotal: Double get() = quantity * unitPrice
}