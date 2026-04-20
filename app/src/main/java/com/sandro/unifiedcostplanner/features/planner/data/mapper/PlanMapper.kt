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
        category = this.category,
        startDate = this.startDate,
        endDate = this.endDate,
        createdAt = this.createdAt,
        items = items.map { it.toDomain() }
    )
}

fun PlannerItemEntity.toDomain(): PlannerItem = PlannerItem(
    id = id,
    name = name,
    quantity = quantity,
    unitPrice = unitPrice,
    currency = currency,
    source = source,
    imageUrl = imageUrl,
    externalUrl = externalUrl
)

fun Plan.toEntity(): PlanEntity = PlanEntity(
    id = id,
    title = title,
    description = description,
    category = category,
    startDate = startDate,
    endDate = endDate,
    createdAt = createdAt
)

fun PlannerItem.toEntity(planId: String): PlannerItemEntity = PlannerItemEntity(
    id = id,
    planId = planId,
    name = name,
    quantity = quantity,
    unitPrice = unitPrice,
    currency = currency,
    source = source,
    imageUrl = imageUrl,
    externalUrl = externalUrl
)
