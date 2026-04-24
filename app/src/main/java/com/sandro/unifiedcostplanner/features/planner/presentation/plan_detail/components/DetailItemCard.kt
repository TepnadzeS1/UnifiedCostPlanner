package com.sandro.unifiedcostplanner.features.planner.presentation.plan_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy

@Composable
fun DetailItemCard(
    title: String,
    subtitle: String,
    tag: String,
    tagColor: Color,
    iconBgColor: Color,
    unitPrice: Double,
    qty: Int,
    subtotal: Double
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // Flat look from Figma
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top Row: Icon, Title, Tag
            Row(verticalAlignment = Alignment.Top) {
                // Colored Icon Box
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(iconBgColor, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = PrimaryNavy, modifier = Modifier.size(20.dp))
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Title and Subtitle
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = title, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A), fontSize = 15.sp)
                    Text(text = subtitle, color = Color.Gray, fontSize = 12.sp)
                }

                // Tag Badge (e.g., CATALOG, EBAY)
                Surface(
                    color = tagColor,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = tag,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom Row: Qty/Price and Subtotal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(text = "Qty x$qty", color = Color.Gray, fontSize = 11.sp)
                    Text(text = "$${String.format("%.2f", unitPrice)} /ea", color = Color(0xFF1A1A1A), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Subtotal", color = Color.Gray, fontSize = 11.sp)
                    Text(
                        text = "$${String.format("%.2f", subtotal)}",
                        color = PrimaryNavy,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}