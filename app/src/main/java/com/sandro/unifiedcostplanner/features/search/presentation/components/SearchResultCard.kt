package com.sandro.unifiedcostplanner.features.search.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy
import java.util.Locale

@Composable
fun SearchResultCard(
    title: String,
    price: Double,
    shippingCost: Double,
    condition: String,
    watchers: Int,
    rating: Double,
    reviews: Int,
    isTopRated: Boolean = false,
    platform: String = "eBay"
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // 🖼️ The Image Area (Gradient placeholder)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFE0E0E0), Color(0xFFBDBDBD))
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                if (isTopRated) {
                    Text(
                        text = "TOP RATED PLUS",
                        color = PrimaryNavy,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 📝 Title and Condition
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A),
                lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = condition, fontSize = 11.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(4.dp))

            // ⭐ Ratings
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) { index ->
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = if (index < rating.toInt()) Color(0xFFFFB300) else Color.LightGray,
                        modifier = Modifier.size(12.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text("($reviews)", fontSize = 10.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 💰 Price & Platform Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "$${String.format(Locale.US, "%.2f", price)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1A1A1A)
                    )
                    Text(
                        text = "+$${String.format(Locale.US, "%.2f", shippingCost)} shipping",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (watchers > 0) {
                        Text(text = "$watchers watchers", fontSize = 10.sp, color = Color(0xFFD32F2F), fontWeight = FontWeight.Medium)
                    }
                }

                // Platform Badge
                Surface(
                    color = Color(0xFFEEEEEE),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = platform,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}