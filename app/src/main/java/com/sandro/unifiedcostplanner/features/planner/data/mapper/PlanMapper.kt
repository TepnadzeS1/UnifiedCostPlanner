package com.sandro.unifiedcostplanner.features.planner.data.mapper

import com.sandro.unifiedcostplanner.features.planner.data.local.entity.PlanEntity
import com.sandro.unifiedcostplanner.features.planner.data.local.entity.PlannerItemEntity
import com.sandro.unifiedcostplanner.features.planner.domain.model.Plan
import com.sandro.unifiedcostplanner.features.planner.domain.model.PlannerItem
import com.sandro.unifiedcostplanner.features.planner.domain.model.SourceType

fun PlanEntity.toDomain(items: List<PlannerItemEntity>): Plan {
    return Plan(
        id = this.id,
        title = this.title,
        // If DB has empty string, map it back to null for your Domain model
        description = this.description.ifBlank { null },
        category = this.category,
        // If DB has 0L, map it to null for your Domain model
        startDate = if (this.startDate > 0L) this.startDate else null,
        endDate = if (this.endDate > 0L) this.endDate else null,
        createdAt = this.createdAt,
        // 🚀 NOTICE: No 'totalCost' here! Your Plan calculates it automatically.
        items = items.map { it.toDomain() }
    )
}

fun PlannerItemEntity.toDomain(): PlannerItem = PlannerItem(
    id = id,
    name = name,
    quantity = quantity,
    unitPrice = unitPrice,
    currency = currency,
    // Safely convert DB String back to your Enum
    source = try { SourceType.valueOf(source) } catch (e: Exception) { SourceType.MANUAL_ENTRY },
    imageUrl = imageUrl,
    externalUrl = externalUrl,
    notes = notes
)

fun Plan.toEntity(): PlanEntity = PlanEntity(
    id = id,
    title = title,
    // 🚀 Handle your nullables safely for Room
    description = description ?: "",
    category = category,
    startDate = startDate ?: 0L,
    endDate = endDate ?: 0L,
    createdAt = createdAt,
    // 🚀 Calls your get() function to save the calculated number to the database!
    totalCost = totalCost
)

fun PlannerItem.toEntity(planId: String): PlannerItemEntity = PlannerItemEntity(
    id = id,
    planId = planId,
    name = name,
    quantity = quantity,
    unitPrice = unitPrice,
    currency = currency,
    source = source.name,
    imageUrl = imageUrl,
    externalUrl = externalUrl,
    notes = notes,
    subtotal = subtotal
)