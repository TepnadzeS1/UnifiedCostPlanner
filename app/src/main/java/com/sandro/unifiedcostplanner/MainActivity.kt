package com.sandro.unifiedcostplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sandro.unifiedcostplanner.features.planner.presentation.components.MainBottomBar
import com.sandro.unifiedcostplanner.features.planner.presentation.create_plan.CreatePlanScreen
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.PlanListScreen
import com.sandro.unifiedcostplanner.ui.theme.UnifiedCostPlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            println("CRASH_LOG: ${throwable.localizedMessage}")
            throwable.printStackTrace()
        }
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
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // 1. Plans Tab
                        composable(BottomNavItem.Plans.route) {
                            PlanListScreen(
                                navController = navController,
                                onNavigateToCreate = {
                                    if (navController.currentDestination?.route != "create_plan") {
                                        navController.navigate("create_plan")
                                    }
                                },
                                onNavigateToDetails = { id -> navController.navigate("details/$id") }
                            )
                        }

                        // 2. Catalog Tab (Placeholder for now)
                        composable(BottomNavItem.Catalog.route) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Catalog Screen Coming Soon")
                            }
                        }

                        // 3. Search Tab (Placeholder for now)
                        composable(BottomNavItem.Search.route) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Search Screen Coming Soon")
                            }
                        }

                        // 4. Settings Tab (Placeholder for now)
                        composable(BottomNavItem.Settings.route) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Settings Screen Coming Soon")
                            }
                        }

                        // 5. Create Plan (Overlay Screen)
                        composable("create_plan") {
                            CreatePlanScreen(onNavigateBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}