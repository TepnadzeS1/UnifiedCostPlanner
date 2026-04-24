package com.sandro.unifiedcostplanner.features.catalog.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandro.unifiedcostplanner.features.catalog.presentation.components.CatalogItemCard
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy

@Composable
fun CatalogScreen() {
    val backgroundColor = Color(0xFFF8F9FA)

    // State for the filter chips
    val filters = listOf("All Items", "Hotels", "Dining", "Services")
    var selectedFilter by remember { mutableStateOf("All Items") }

    Scaffold(
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // 1. Top Bar (Matches your Figma exactly)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = PrimaryNavy)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Unified Cost Planner",
                        color = PrimaryNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = Color(0xFFEEEEEE),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("UP", color = Color.DarkGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 2. Screen Header
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "Local Catalog",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF1A1A1A)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Explore curated services and amenities in your area.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Filter Chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    val isSelected = filter == selectedFilter
                    Surface(
                        color = if (isSelected) PrimaryNavy else Color(0xFFEEEEEE),
                        shape = RoundedCornerShape(20.dp),
                        onClick = { selectedFilter = filter }
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) Color.White else Color.DarkGray,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. The Catalog List (Dummy Data for now)
            LazyColumn(
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 80.dp)
            ) {
                item {
                    CatalogItemCard(
                        title = "The Grand Horizon",
                        subtitle = "5-Star Accommodation",
                        price = 350.0,
                        unit = "/night",
                        isFeatured = true
                    )
                }
                item {
                    CatalogItemCard(
                        title = "Ivy Boutique Inn",
                        subtitle = "Charming Local Stay",
                        price = 180.0,
                        unit = "/night"
                    )
                }
                item {
                    CatalogItemCard(
                        title = "Apex Suites",
                        subtitle = "Business District",
                        price = 220.0,
                        unit = "/night"
                    )
                }
            }
        }
    }
}