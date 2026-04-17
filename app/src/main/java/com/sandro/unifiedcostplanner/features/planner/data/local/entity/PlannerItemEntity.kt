package com.sandro.unifiedcostplanner.features.planner.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sandro.unifiedcostplanner.features.planner.domain.model.SourceType

@Entity(tableName = "planner_items")
data class PlannerItemEntity(
    @PrimaryKey val id: String,
    val planId: String, // Foreign key linking to the Plan
    val name: String,
    val quantity: Int,
    val unitPrice: Double,
    val currency: String,
    val source: SourceType, // We will handle this with a TypeConverter
    val imageUrl: String?,
    val externalUrl: String?
)