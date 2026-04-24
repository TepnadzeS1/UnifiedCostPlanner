package com.sandro.unifiedcostplanner.features.search.presentation

import androidx.compose.foundation.clickable
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.sandro.unifiedcostplanner.features.catalog.presentation.components.CatalogItemSheet
import com.sandro.unifiedcostplanner.features.search.presentation.components.SearchResultCard
import com.sandro.unifiedcostplanner.features.search.presentation.viewmodel.SearchViewModel
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy

// Helper class for the cart
data class PendingExternalItem(val name: String, val platform: String, val unitPrice: Double, val quantity: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val backgroundColor = Color(0xFFF8F9FA)
    var searchQuery by remember { mutableStateOf("Vintage Audio") }
    val filters = listOf("All Results", "Buy It Now", "Auction", "Condition")
    var selectedFilter by remember { mutableStateOf("All Results") }

    // 🧠 Database State
    val plans by viewModel.plans.collectAsState()

    // 🎨 UI State for Sheets and Dialogs
    var selectedItemForSheet by remember { mutableStateOf<Triple<String, String, Double>?>(null) }
    var pendingCartItem by remember { mutableStateOf<PendingExternalItem?>(null) }

    Scaffold(containerColor = backgroundColor) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 8.dp)
        ) {
            // 1. Top Bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = PrimaryNavy)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Unified Cost Planner", color = PrimaryNavy, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Surface(shape = CircleShape, color = Color(0xFFEEEEEE), modifier = Modifier.size(36.dp)) {
                    Box(contentAlignment = Alignment.Center) { Text("UP", color = Color.DarkGray, fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEEEEEE), unfocusedContainerColor = Color(0xFFEEEEEE),
                    focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent
                ),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray, modifier = Modifier.size(20.dp)) },
                trailingIcon = { Icon(Icons.Default.Tune, contentDescription = "Filters", tint = Color.Gray, modifier = Modifier.size(20.dp)) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Filter Chips
            LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filters) { filter ->
                    val isSelected = filter == selectedFilter
                    Surface(
                        color = if (isSelected) PrimaryNavy else Color(0xFFEEEEEE),
                        shape = RoundedCornerShape(8.dp),
                        onClick = { selectedFilter = filter }
                    ) {
                        Text(
                            text = filter, color = if (isSelected) Color.White else Color.DarkGray,
                            fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Results Header
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text("Search Results", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A))
                    Text("Found 42 items for \"$searchQuery\"", fontSize = 11.sp, color = Color.Gray)
                }
                Text("Sort: Best Match ▾", fontSize = 11.sp, color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Results List
            LazyColumn(contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 80.dp)) {
                item {
                    SearchResultCard(
                        title = "Vintage Pioneer SX-780 Stereo Receiver", condition = "Pre-Owned",
                        price = 450.00, shippingCost = 35.00, watchers = 2, rating = 4.8, reviews = 124, isTopRated = true,
                        // 🚀 TRIGGERS THE SHEET
                        onClick = { selectedItemForSheet = Triple("Vintage Pioneer SX-780", "eBay", 485.00) } // Price + Shipping combined for simplicity
                    )
                }
                item {
                    SearchResultCard(
                        title = "Marantz 6100 Belt Drive Turntable Record Player", condition = "Pre-Owned • Marantz",
                        price = 122.50, shippingCost = 15.00, watchers = 14, rating = 4.2, reviews = 45,
                        onClick = { selectedItemForSheet = Triple("Marantz 6100 Turntable", "eBay", 137.50) }
                    )
                }
            }
        }
    }

    // 🚀 THE BOTTOM SHEET (Reusing your awesome Catalog sheet!)
    selectedItemForSheet?.let { item ->
        CatalogItemSheet(
            title = item.first,
            category = item.second,
            unitPrice = item.third,
            onDismiss = { selectedItemForSheet = null },
            onAddToCart = { quantity, _ ->
                selectedItemForSheet = null
                pendingCartItem = PendingExternalItem(item.first, item.second, item.third, quantity)
            }
        )
    }

    // 🚀 THE PLAN SELECTOR DIALOG
    pendingCartItem?.let { cartItem ->
        AlertDialog(
            onDismissRequest = { pendingCartItem = null },
            title = { Text("Select a Plan", fontWeight = FontWeight.Bold, color = PrimaryNavy) },
            text = {
                if (plans.isEmpty()) {
                    Text("You don't have any plans yet. Go to the Plans tab to create one first!")
                } else {
                    Column {
                        Text("Where should we add ${cartItem.name}?", color = Color.Gray, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        plans.forEach { plan ->
                            Surface(
                                color = Color(0xFFF5F5F5), shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable {
                                    // 🚀 SAVE TO DB
                                    viewModel.addExternalItemToPlan(
                                        planId = plan.id,
                                        name = cartItem.name,
                                        unitPrice = cartItem.unitPrice,
                                        quantity = cartItem.quantity,
                                        platform = cartItem.platform
                                    )
                                    pendingCartItem = null
                                }
                            ) { Text(text = plan.title, modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A)) }
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { pendingCartItem = null }) { Text("Cancel", color = Color.Gray) } },
            containerColor = Color.White
        )
    }
}