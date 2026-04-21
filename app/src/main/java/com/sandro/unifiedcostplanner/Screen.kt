package com.sandro.unifiedcostplanner

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object PlanList : Screen("plans", "Plans", Icons.Default.DateRange)
    object Catalog : Screen("catalog", "Catalog", Icons.Default.List)
    object Search : Screen("search", "Search", Icons.Default.Search)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)

    // Create Plan is a screen, but not a bottom tab
    object CreatePlan : Screen("create_plan", "Create Plan", Icons.Default.Add)
}