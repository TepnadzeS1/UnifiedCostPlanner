package com.sandro.unifiedcostplanner.features.planner.data.repository

import com.sandro.unifiedcostplanner.features.planner.data.local.dao.PlanDao
import com.sandro.unifiedcostplanner.features.planner.data.mapper.toDomain
import com.sandro.unifiedcostplanner.features.planner.data.mapper.toEntity
import com.sandro.unifiedcostplanner.features.planner.domain.model.Plan
import com.sandro.unifiedcostplanner.features.planner.domain.model.PlannerItem
import com.sandro.unifiedcostplanner.features.planner.domain.repository.PlannerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlannerRepositoryImpl @Inject constructor(
    private val planDao: PlanDao
) : PlannerRepository {

    override fun getPlans(): Flow<List<Plan>> {
        return planDao.getAllPlans().map { entities ->
            entities.map { entity ->
                val items = planDao.getItemsForPlan(entity.id)
                entity.toDomain(items)
            }
        }
    }

    override suspend fun getPlanById(id: String): Plan? {
        // We fetch the plan entity first
        val planEntity = planDao.getPlanById(id) ?: return null
        // Then we fetch all items belonging to this plan
        val items = planDao.getItemsForPlan(id)
        // Convert the database stuff into a clean Domain Plan
        return planEntity.toDomain(items)
    }

    override suspend fun savePlan(plan: Plan) {
        planDao.insertPlan(plan.toEntity())
    }

    override suspend fun addItemToPlan(planId: String, item: PlannerItem) {
        planDao.insertItem(item.toEntity(planId))
    }

    override suspend fun deletePlan(planId: String) {
        planDao.deletePlan(planId)
    }
}