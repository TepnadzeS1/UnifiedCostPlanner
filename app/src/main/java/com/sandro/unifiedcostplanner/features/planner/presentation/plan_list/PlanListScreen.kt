package com.sandro.unifiedcostplanner.features.planner.presentation.plan_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_detail.components.SwipeToDeleteContainer
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.components.PlanCard
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.viewmodel.PlanListViewModel
import com.sandro.unifiedcostplanner.ui.theme.BackgroundLight
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy
import com.sandro.unifiedcostplanner.ui.theme.SurfaceWhite

@Composable
fun PlanListScreen(
    onNavigateToCreate: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    viewModel: PlanListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // 🏗️ THE SCAFFOLD: The skeleton of your screen
    Scaffold(
        containerColor = BackgroundLight, // 🎨 This gives you the Figma background!
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreate,
                containerColor = PrimaryNavy,
                contentColor = SurfaceWhite
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create Plan")
            }
        }
    ) { paddingValues ->
        // The Scaffold passes 'paddingValues' so your list doesn't hide behind bars
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Apply Scaffold's safe padding here
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 🚀 THE KEY FIX: Tells Compose to track items by ID
            items(items = state.plans, key = { it.id }) { plan ->
                SwipeToDeleteContainer(
                    item = plan,
                    onDelete = { viewModel.deletePlan(plan.id) }
                ) {
                    PlanCard(
                        plan = plan,
                        onClick = { onNavigateToDetails(plan.id) }
                    )
                }
            }
        }
    }
}