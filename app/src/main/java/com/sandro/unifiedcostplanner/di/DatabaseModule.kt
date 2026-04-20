package com.sandro.unifiedcostplanner.di

import android.content.Context
import androidx.room.Room
import com.sandro.unifiedcostplanner.features.planner.data.local.CostPlannerDatabase
import com.sandro.unifiedcostplanner.features.planner.data.local.dao.PlanDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CostPlannerDatabase {
        return Room.databaseBuilder(
            context,
            CostPlannerDatabase::class.java,
            "cost_planner_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePlanDao(db: CostPlannerDatabase): PlanDao {
        return db.planDao()
    }
}
