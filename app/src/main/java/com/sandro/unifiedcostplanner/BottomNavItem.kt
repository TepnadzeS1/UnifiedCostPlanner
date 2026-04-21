package com.sandro.unifiedcostplanner

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
) {
    object Plans : BottomNavItem("Plans", "plans", Icons.Default.DateRange)
    object Catalog : BottomNavItem("Catalog", "catalog", Icons.Default.List)
    object Search : BottomNavItem("Search", "search", Icons.Default.Search)
    object Settings : BottomNavItem("Settings", "settings", Icons.Default.Settings)
}