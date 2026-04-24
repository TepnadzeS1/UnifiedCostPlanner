package com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sandro.unifiedcostplanner.features.planner.domain.model.PlannerItem
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy
import com.sandro.unifiedcostplanner.ui.theme.SurfaceWhite
import java.util.*

@Composable
fun PlannerItemRow(item: PlannerItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryNavy,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${item.quantity} x $${String.format(Locale.US, "%.2f", item.unitPrice)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Text(
                text = "$${String.format(Locale.US, "%.2f", item.subtotal)}",
                style = MaterialTheme.typography.titleLarge,
                color = PrimaryNavy,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}