package com.sandro.unifiedcostplanner.features.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandro.unifiedcostplanner.features.search.presentation.components.SearchResultCard
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    val backgroundColor = Color(0xFFF8F9FA)
    var searchQuery by remember { mutableStateOf("Vintage Audio") }

    val filters = listOf("All Results", "Buy It Now", "Auction", "Condition")
    var selectedFilter by remember { mutableStateOf("All Results") }

    Scaffold(containerColor = backgroundColor) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 8.dp) // The perfect top margin!
        ) {
            // 1. Top Bar
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
                    Text("Unified Cost Planner", color = PrimaryNavy, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Surface(shape = CircleShape, color = Color(0xFFEEEEEE), modifier = Modifier.size(36.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("UP", color = Color.DarkGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEEEEEE),
                    unfocusedContainerColor = Color(0xFFEEEEEE),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray, modifier = Modifier.size(20.dp)) },
                trailingIcon = { Icon(Icons.Default.Tune, contentDescription = "Filters", tint = Color.Gray, modifier = Modifier.size(20.dp)) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Filter Chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    val isSelected = filter == selectedFilter
                    Surface(
                        color = if (isSelected) PrimaryNavy else Color(0xFFEEEEEE),
                        shape = RoundedCornerShape(8.dp),
                        onClick = { selectedFilter = filter }
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) Color.White else Color.DarkGray,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Results Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text("Search Results", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A))
                    Text("Found 42 items for \"$searchQuery\"", fontSize = 11.sp, color = Color.Gray)
                }
                Text("Sort: Best Match ▾", fontSize = 11.sp, color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Results List
            LazyColumn(
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 80.dp)
            ) {
                item {
                    SearchResultCard(
                        title = "Vintage Pioneer SX-780 Stereo Receiver - Mint Condition, Fully Restored & Tested",
                        condition = "Pre-Owned", price = 450.00, shippingCost = 35.00, watchers = 2, rating = 4.8, reviews = 124, isTopRated = true
                    )
                }
                item {
                    SearchResultCard(
                        title = "Marantz 6100 Belt Drive Turntable Record Player - Original Dust Cover",
                        condition = "Pre-Owned • Marantz", price = 122.50, shippingCost = 15.00, watchers = 14, rating = 4.2, reviews = 45
                    )
                }
            }
        }
    }
}