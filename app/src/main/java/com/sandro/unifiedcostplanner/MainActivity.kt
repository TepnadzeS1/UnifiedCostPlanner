package com.sandro.unifiedcostplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.PlanListScreen
import com.sandro.unifiedcostplanner.ui.theme.UnifiedCostPlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // CRITICAL: This allows Hilt to inject ViewModels into your screens
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            UnifiedCostPlannerTheme {
                // We call the PlanListScreen here.
                // For now, we leave the navigation lambdas empty.
                PlanListScreen(
                    onNavigateToCreate = {
                        /* We will build the Create Screen next */
                    },
                    onNavigateToDetails = { planId ->
                        /* We will build the Details Screen later */
                    }
                )
            }
        }
    }
}