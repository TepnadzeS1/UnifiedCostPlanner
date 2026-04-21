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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanListScreen(
    navController: NavController,
    onNavigateToCreate: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    viewModel: PlanListViewModel = hiltViewModel() // Hilt injects the ViewModel here
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Unified Cost Planner") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Create Plan")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 1. Loading State
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            // 2. Empty State
            if (!state.isLoading && state.plans.isEmpty()) {
                Text(
                    text = "No plans yet. Tap + to start!",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // 3. Success State (The List)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.plans) { plan ->
                    PlanCard(
                        plan = plan,
                        navController = navController,
                        onClick = { onNavigateToDetails(plan.id) }
                    )
                }
            }
        }
    }
}