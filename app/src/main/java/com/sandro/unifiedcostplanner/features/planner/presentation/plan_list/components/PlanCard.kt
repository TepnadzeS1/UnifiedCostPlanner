package com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.sandro.unifiedcostplanner.features.planner.domain.model.Plan
import com.sandro.unifiedcostplanner.ui.theme.ChipBackground
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy
import com.sandro.unifiedcostplanner.ui.theme.SurfaceWhite

@Composable
fun PlanCard(
    plan: Plan,
    navController: NavController,
    onClick: () -> Unit = {
        // 🛡️ NAVIGATION GUARD: Only navigate if the screen is fully visible (Resumed)
        // This prevents the "5-click" ANR by ignoring the extra 4 clicks
        if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
            navController.navigate("detail/${plan.id}")
        }
    }
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp), // Increased roundness from design
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = plan.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    // We need to add 'dateRange' to the Plan Domain Model later!
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.DateRange, null, Modifier.size(14.dp))
                        Text(text = "Oct 12 - Oct 20", style = MaterialTheme.typography.bodySmall)
                    }
                }

                // The big price highlight
                Text(
                    text = "$${plan.totalCost}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = PrimaryNavy,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Item count pill
                Surface(
                    color = ChipBackground,
                    shape = CircleShape
                ) {
                    Text(
                        text = "${plan.items.size} items",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}