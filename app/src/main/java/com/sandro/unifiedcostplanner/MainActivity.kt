package com.sandro.unifiedcostplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
// 🚀 Imports
import com.sandro.unifiedcostplanner.features.catalog.presentation.CatalogScreen
import com.sandro.unifiedcostplanner.features.planner.presentation.components.MainBottomBar
import com.sandro.unifiedcostplanner.features.planner.presentation.create_plan.CreatePlanScreen
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_detail.PlanDetailScreen
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.PlanListScreen
import com.sandro.unifiedcostplanner.features.search.presentation.SearchScreen
import com.sandro.unifiedcostplanner.features.settings.presentation.SettingsScreen
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

                Scaffold(
                    bottomBar = { MainBottomBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItem.Plans.route,
                        modifier = Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding)
                    ) {
                        // 1. List Screen
                        composable(BottomNavItem.Plans.route) {
                            PlanListScreen(
                                onNavigateToCreate = {
                                    navController.navigate("create_plan") { launchSingleTop = true }
                                },
                                onNavigateToDetails = { planId ->
                                    // 🛡️ NAVIGATION GUARD: The ANR Killer
                                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                                        navController.navigate("plan_details/$planId") {
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            )
                        }

                        // 2. Details Screen
                        composable(
                            route = "plan_details/{planId}",
                            arguments = listOf(navArgument("planId") { type = NavType.StringType })
                        ) {
                            PlanDetailScreen(onNavigateBack = { navController.popBackStack() })
                        }

                        // 3. Create Screen
                        composable("create_plan") {
                            CreatePlanScreen(onNavigateBack = { navController.popBackStack() })
                        }

                        // 4. Catalog Screen
                        composable(BottomNavItem.Catalog.route) {
                            CatalogScreen()
                        }

                        // 5. Search Screen
                        composable(BottomNavItem.Search.route) {
                            SearchScreen()
                        }

                        // 6. Settings Screen
                        composable(BottomNavItem.Settings.route) {
                            SettingsScreen()
                        }
                    }
                }
            }
        }
    }
}