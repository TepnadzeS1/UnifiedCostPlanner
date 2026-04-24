package com.sandro.unifiedcostplanner.features.planner.presentation.plan_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sandro.unifiedcostplanner.features.planner.domain.model.Plan
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_detail.components.SwipeToDeleteContainer
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.components.PlanCard
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.viewmodel.PlanListViewModel
import com.sandro.unifiedcostplanner.ui.components.UnifiedTopBar
import com.sandro.unifiedcostplanner.ui.theme.BackgroundLight
import com.sandro.unifiedcostplanner.ui.theme.SecondaryTeal

@Composable
fun PlanListScreen(
    onNavigateToCreate: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    viewModel: PlanListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = BackgroundLight,
        topBar = {
            PlanListHeader()
        },
        floatingActionButton = {
            PlanListFAB(onNavigateToCreate)
        }
    ) { paddingValues ->
        PlanListContent(
            plans = state.plans,
            paddingValues = paddingValues,
            onNavigateToDetails = onNavigateToDetails,
            onDeletePlan = viewModel::deletePlan
        )
    }
}

@Composable
private fun PlanListHeader() {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(top = 16.dp)
            .padding(horizontal = 20.dp)
    ) {
        UnifiedTopBar(
            profileContent = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.Gray,
                    modifier = Modifier.padding(6.dp)
                )
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Plans",
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF1A1A1A)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun PlanListContent(
    plans: List<Plan>,
    paddingValues: PaddingValues,
    onNavigateToDetails: (String) -> Unit,
    onDeletePlan: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = plans, key = { it.id }) { plan ->
            SwipeToDeleteContainer(
                item = plan,
                onDelete = { onDeletePlan(plan.id) }
            ) {
                PlanCard(
                    plan = plan,
                    onClick = { onNavigateToDetails(plan.id) }
                )
            }
        }
    }
}

@Composable
private fun PlanListFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = SecondaryTeal,
        contentColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(Icons.Default.Add, contentDescription = "Create Plan")
    }
}
