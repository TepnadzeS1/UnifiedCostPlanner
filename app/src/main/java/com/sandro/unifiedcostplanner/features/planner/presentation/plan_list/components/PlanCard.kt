package com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sandro.unifiedcostplanner.features.planner.domain.model.Plan
import com.sandro.unifiedcostplanner.ui.theme.ChipBackground
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy
import com.sandro.unifiedcostplanner.ui.theme.SurfaceWhite
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PlanCard(
    plan: Plan,
    onClick: () -> Unit
) {
    // 🛡️ CLICK DEBOUNCER: Ignores rapid fire clicks
    var lastClickTime by remember { mutableStateOf<Long>(0L) }

    val dateRange = remember(plan.startDate, plan.endDate) {
        val sdf = SimpleDateFormat("MMM dd", Locale.US)
        val start = plan.startDate?.let { sdf.format(Date(it)) } ?: "N/A"
        val end = plan.endDate?.let { sdf.format(Date(it)) } ?: "N/A"
        "$start - $end"
    }

    Card(
        onClick = {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > 500L) {
                lastClickTime = currentTime
                onClick()
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = plan.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold, color = PrimaryNavy)
                    Text(text = dateRange, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                }
                Text(text = "$${String.format(Locale.US, "%.2f", plan.totalCost)}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black, color = PrimaryNavy)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Surface(color = ChipBackground, shape = CircleShape) {
                Text(text = "${plan.items.size} items", modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = PrimaryNavy)
            }
        }
    }
}