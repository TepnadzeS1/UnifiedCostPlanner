package com.sandro.unifiedcostplanner.features.catalog.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sandro.unifiedcostplanner.features.catalog.presentation.components.CatalogItemCard
import com.sandro.unifiedcostplanner.features.catalog.presentation.components.CatalogItemSheet
import com.sandro.unifiedcostplanner.features.catalog.presentation.viewmodel.CatalogViewModel
import com.sandro.unifiedcostplanner.ui.components.UnifiedTopBar

data class PendingCartItem(val name: String, val category: String, val unitPrice: Double, val quantity: Int)

@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel = hiltViewModel()
) {
    val plans by viewModel.plans.collectAsState()
    var selectedItemForSheet by remember { mutableStateOf<Triple<String, String, Double>?>(null) }
    var pendingCartItem by remember { mutableStateOf<PendingCartItem?>(null) }
    val filters = listOf("All Items", "Hotels", "Dining", "Services")
    var selectedFilter by remember { mutableStateOf("All Items") }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column(modifier = Modifier.padding(horizontal = 20.dp).padding(top = 16.dp)) {
                UnifiedTopBar()
                Spacer(modifier = Modifier.height(32.dp))
                Text("Local Catalog", fontSize = 32.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onBackground)
                Text("Explore curated services and amenities in your area.", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(24.dp))
                FilterChips(filters, selectedFilter) { selectedFilter = it }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                CatalogItemCard(
                    title = "The Grand Horizon", subtitle = "5-Star Accommodation", price = 350.0, unit = "/night", isFeatured = true,
                    onClick = { selectedItemForSheet = Triple("The Grand Horizon", "Accommodation", 350.0) }
                )
            }
            item {
                CatalogItemCard(
                    title = "Ivy Boutique Inn", subtitle = "Charming Local Stay", price = 180.0, unit = "/night",
                    onClick = { selectedItemForSheet = Triple("Ivy Boutique Inn", "Accommodation", 180.0) }
                )
            }
            item {
                CatalogItemCard(
                    title = "Apex Suites", subtitle = "Business District", price = 220.0, unit = "/night",
                    onClick = { selectedItemForSheet = Triple("Apex Suites", "Accommodation", 220.0) }
                )
            }
        }
    }

    selectedItemForSheet?.let { item ->
        CatalogItemSheet(
            title = item.first, category = item.second, unitPrice = item.third,
            onDismiss = { selectedItemForSheet = null },
            onAddToCart = { quantity, _ ->
                selectedItemForSheet = null
                pendingCartItem = PendingCartItem(item.first, item.second, item.third, quantity)
            }
        )
    }

    pendingCartItem?.let { cartItem ->
        PlanSelectorDialog(
            cartItem = cartItem,
            plans = plans,
            onDismiss = { pendingCartItem = null },
            onPlanSelected = { planId ->
                viewModel.addCatalogItemToPlan(planId, cartItem.name, cartItem.unitPrice, cartItem.quantity, cartItem.category)
                pendingCartItem = null
            }
        )
    }
}

@Composable
private fun FilterChips(filters: List<String>, selected: String, onSelect: (String) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(filters.size) { index ->
            val filter = filters[index]
            val isSelected = filter == selected
            FilterChip(
                selected = isSelected,
                onClick = { onSelect(filter) },
                label = { Text(filter) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                border = null,
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}

@Composable
private fun PlanSelectorDialog(
    cartItem: PendingCartItem,
    plans: List<com.sandro.unifiedcostplanner.features.planner.domain.model.Plan>,
    onDismiss: () -> Unit,
    onPlanSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select a Plan", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) },
        text = {
            if (plans.isEmpty()) {
                Text("You don't have any plans yet. Go to the Plans tab to create one first!", color = MaterialTheme.colorScheme.onSurface)
            } else {
                Column {
                    Text("Where should we add ${cartItem.name}?", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    plans.forEach { plan ->
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { onPlanSelected(plan.id) }
                        ) {
                            Text(plan.title, modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant) } },
        containerColor = MaterialTheme.colorScheme.surface
    )
}