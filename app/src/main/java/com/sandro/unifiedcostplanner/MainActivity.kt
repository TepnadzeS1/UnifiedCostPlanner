package com.sandro.unifiedcostplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sandro.unifiedcostplanner.features.planner.presentation.create_plan.CreatePlanScreen
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.PlanListScreen
import com.sandro.unifiedcostplanner.ui.theme.UnifiedCostPlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnifiedCostPlannerTheme {
                val navController = rememberNavController()

                // The NavHost defines all possible destinations
                NavHost(
                    navController = navController,
                    startDestination = Screen.PlanList.route
                ) {
                    // 1. Plan List Screen
                    composable(route = Screen.PlanList.route) {
                        PlanListScreen(
                            onNavigateToCreate = {
                                navController.navigate(Screen.CreatePlan.route)
                            },
                            onNavigateToDetails = { planId ->
                                // navController.navigate("plan_details/$planId")
                            }
                        )
                    }

                    // 2. Create Plan Screen
                    composable(route = Screen.CreatePlan.route) {
                        CreatePlanScreen(
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}