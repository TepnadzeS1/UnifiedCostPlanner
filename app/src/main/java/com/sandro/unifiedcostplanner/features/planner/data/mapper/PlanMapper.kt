package com.sandro.unifiedcostplanner.features.planner.data.mapper

import com.sandro.unifiedcostplanner.features.planner.data.local.entity.PlanEntity
import com.sandro.unifiedcostplanner.features.planner.data.local.entity.PlannerItemEntity
import com.sandro.unifiedcostplanner.features.planner.domain.model.Plan
import com.sandro.unifiedcostplanner.features.planner.domain.model.PlannerItem

fun PlanEntity.toDomain(items: List<PlannerItemEntity>): Plan {
    return Plan(
        id = this.id,
        title = this.title,
        description = this.description,
        createdAt = this.createdAt,
        items = items.map { it.toDomain() }
    )
}

fun PlannerItemEntity.toDomain(): PlannerItem {
    return PlannerItem(
        id = this.id,
        name = this.name,
        quantity = this.quantity,
        unitPrice = this.unitPrice,
        currency = this.currency,
        source = this.source,
        imageUrl = this.imageUrl,
        externalUrl = this.externalUrl
    )
}

// And the reverse for saving
fun Plan.toEntity(): PlanEntity = PlanEntity(id, title, description, createdAt)
fun PlannerItem.toEntity(planId: String): PlannerItemEntity = PlannerItemEntity(
    id, planId, name, quantity, unitPrice, currency, source, imageUrl, externalUrl
)