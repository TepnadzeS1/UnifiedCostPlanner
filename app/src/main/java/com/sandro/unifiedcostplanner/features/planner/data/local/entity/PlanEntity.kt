package com.sandro.unifiedcostplanner.features.planner.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plans")
data class PlanEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val category: String,
    val startDate: Long?,
    val endDate: Long?,
    val createdAt: Long
)