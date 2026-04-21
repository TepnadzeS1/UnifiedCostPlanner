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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.components.PlanCard
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.viewmodel.PlanListViewModel
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy
import com.sandro.unifiedcostplanner.ui.theme.SurfaceWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanListScreen(
    onNavigateToCreate: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    viewModel: PlanListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 🚀 THE KEY FIX: Tells Compose to track items by ID, not index
        items(items = state.plans, key = { it.id }) { plan ->
            PlanCard(
                plan = plan,
                onClick = { onNavigateToDetails(plan.id) }
            )
        }
    }
}