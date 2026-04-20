package com.sandro.unifiedcostplanner.di

import com.sandro.unifiedcostplanner.features.planner.data.repository.PlannerRepositoryImpl
import com.sandro.unifiedcostplanner.features.planner.domain.repository.PlannerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlannerRepository(
        plannerRepositoryImpl: PlannerRepositoryImpl
    ): PlannerRepository
}
