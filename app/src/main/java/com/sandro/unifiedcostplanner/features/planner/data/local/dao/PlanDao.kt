package com.sandro.unifiedcostplanner.features.planner.data.local.dao

import androidx.room.*
import com.sandro.unifiedcostplanner.features.planner.data.local.entity.PlanEntity
import com.sandro.unifiedcostplanner.features.planner.data.local.entity.PlanWithItems
import com.sandro.unifiedcostplanner.features.planner.data.local.entity.PlannerItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {

    @Transaction // Required because Room is hitting two tables at once
    @Query("SELECT * FROM plans ORDER BY createdAt DESC")
    fun getAllPlansWithItems(): Flow<List<PlanWithItems>>

    @Query("SELECT * FROM plans ORDER BY createdAt DESC")
    fun getAllPlans(): Flow<List<PlanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlan(plan: PlanEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: PlannerItemEntity)

    @Query("SELECT * FROM planner_items WHERE planId = :planId")
    suspend fun getItemsForPlan(planId: String): List<PlannerItemEntity>

    @Query("DELETE FROM plans WHERE id = :planId")
    suspend fun deletePlan(planId: String)

    @Query("SELECT * FROM plans WHERE id = :id")
    suspend fun getPlanById(id: String): PlanEntity?
}