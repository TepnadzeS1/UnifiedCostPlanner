package com.sandro.unifiedcostplanner.features.planner.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sandro.unifiedcostplanner.features.planner.domain.model.SourceType

@Entity(tableName = "planner_items")
data class PlannerItemEntity(
    @PrimaryKey val id: String,
    val planId: String, // Required for Room Relations
    val name: String,
    val quantity: Int,
    val unitPrice: Double,
    val currency: String,
    val source: String, // Store the Enum as a String in the DB
    val imageUrl: String?,
    val externalUrl: String?,
    val notes: String?,
    val subtotal: Double// Added this so your Bottom Sheet notes don't get lost!
)