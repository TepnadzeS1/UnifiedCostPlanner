package com.sandro.unifiedcostplanner.features.planner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sandro.unifiedcostplanner.features.planner.data.local.converter.Converters
import com.sandro.unifiedcostplanner.features.planner.data.local.dao.PlanDao
import com.sandro.unifiedcostplanner.features.planner.data.local.entity.PlanEntity
import com.sandro.unifiedcostplanner.features.planner.data.local.entity.PlannerItemEntity

@Database(
    entities = [PlanEntity::class, PlannerItemEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class) // Tell Room how to handle our Enum
abstract class CostPlannerDatabase : RoomDatabase() {
    abstract fun planDao(): PlanDao
}