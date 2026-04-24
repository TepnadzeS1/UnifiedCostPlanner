package com.sandro.unifiedcostplanner.features.planner.data.repository

import com.sandro.unifiedcostplanner.features.planner.data.local.dao.PlanDao
import com.sandro.unifiedcostplanner.features.planner.data.local.entity.PlannerItemEntity
import com.sandro.unifiedcostplanner.features.planner.data.mapper.toDomain
import com.sandro.unifiedcostplanner.features.planner.data.mapper.toEntity
import com.sandro.unifiedcostplanner.features.planner.domain.model.Plan
import com.sandro.unifiedcostplanner.features.planner.domain.model.PlannerItem
import com.sandro.unifiedcostplanner.features.planner.domain.repository.PlannerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class PlannerRepositoryImpl @Inject constructor(
    private val planDao: PlanDao
) : PlannerRepository {

    override fun getPlans(): Flow<List<Plan>> {
        return planDao.getAllPlansWithItems()
            .map { list ->
                list.map { planWithItems ->
                    planWithItems.plan.toDomain(planWithItems.items)
                }
            }
            .distinctUntilChanged() // <--- CRITICAL: Only update UI if data actually changed
            .flowOn(Dispatchers.IO) // <--- Moves the mapping loop to background
    }

    override suspend fun getPlanById(id: String): Plan? = withContext(Dispatchers.IO) {
        val planEntity = planDao.getPlanById(id) ?: return@withContext null
        val items = planDao.getItemsForPlan(id)
        planEntity.toDomain(items)
    }

    override suspend fun savePlan(plan: Plan) = withContext(Dispatchers.IO) {
        planDao.insertPlan(plan.toEntity())
    }

    override suspend fun addItemToPlan(planId: String, item: PlannerItem) = withContext(Dispatchers.IO) {
        planDao.insertItem(item.toEntity(planId))
    }

    override suspend fun deletePlan(planId: String) = withContext(Dispatchers.IO) {
        planDao.deletePlan(planId)
    }

    override suspend fun deleteItem(itemId: String) = withContext(Dispatchers.IO) {
        planDao.deleteItem(itemId)
    }

    override suspend fun addExpenseToPlan(
        planId: String,
        name: String,
        unitPrice: Double,
        quantity: Int,
        notes: String,
        category: String
    ) = withContext(Dispatchers.IO) {

        // 1. Create the database entity (Matches your UPDATED Entity exactly!)
        val newItem = PlannerItemEntity(
            id = UUID.randomUUID().toString(),
            planId = planId,
            name = name,
            quantity = quantity,
            unitPrice = unitPrice,
            currency = "GEL", // Default from your domain model
            source = category, // The category from the bottom sheet
            imageUrl = null,
            externalUrl = null,
            notes = notes,
            subtotal = unitPrice * quantity
        )

        // 2. Save it to Room
        planDao.insertItem(newItem)

    }
}