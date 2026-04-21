package com.sandro.unifiedcostplanner.features.planner.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PlanWithItems(
    @Embedded val plan: PlanEntity,
    @Relation(
        parentColumn = "id", // The ID in the PlanEntity
        entityColumn = "planId" // The foreign key in PlannerItemEntity
    )
    val items: List<PlannerItemEntity>
)