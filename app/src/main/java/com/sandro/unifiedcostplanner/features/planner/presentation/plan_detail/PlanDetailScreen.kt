package com.sandro.unifiedcostplanner.features.planner.presentation.plan_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_detail.components.DetailItemCard
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_detail.components.ManualEntrySheet
import com.sandro.unifiedcostplanner.features.planner.presentation.plan_list.viewmodel.PlanDetailViewModel
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PlanDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: PlanDetailViewModel = hiltViewModel()
) {
    // 🧠 The Engine
    val plan by viewModel.state.collectAsState()
    val backgroundColor = Color(0xFFF8F9FA)

    // State to control the bottom sheet
    var showAddSheet by remember { mutableStateOf(false) }

    if (plan == null) {
        Box(modifier = Modifier.fillMaxSize().background(backgroundColor), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = PrimaryNavy)
        }
        return
    }

    val dateText = remember(plan?.startDate) {
        val sdf = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
        val start = plan?.startDate
        if (start != null && start > 0) {
            "${sdf.format(Date(start))} - Estimated Completion"
        } else {
            "Ongoing"
        }
    }

    // 🏗️ The Scaffold added back to hold the FAB!
    Scaffold(
        containerColor = backgroundColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddSheet = true }, // Opens the sheet
                containerColor = PrimaryNavy,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        }
    ) { paddingValues ->
        // 🎨 The Chassis
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Scaffold safe-padding
                .padding(horizontal = 20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))

                // 1. Custom Top Bar (Figma Style)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onNavigateBack, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryNavy)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Unified Cost\nPlanner",
                            color = PrimaryNavy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            lineHeight = 18.sp
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = PrimaryNavy, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Surface(shape = CircleShape, color = PrimaryNavy, modifier = Modifier.size(32.dp)) {
                            Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White, modifier = Modifier.padding(6.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // 2. Header Section (Real Data)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = plan!!.title,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1A1A1A),
                        lineHeight = 36.sp,
                        modifier = Modifier.weight(1f)
                    )

                    // "In Progress" Badge
                    Surface(
                        color = Color(0xFFE8F0EA),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(6.dp).background(Color(0xFF2E7D32), CircleShape))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("In\nProgress", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = dateText, color = Color.Gray, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(24.dp))

                // 3. Total Cost (Real Math)
                Text(text = "TOTAL PROJECTED COST", color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "$${String.format(Locale.US, "%.2f", plan!!.totalCost)}",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black,
                    color = PrimaryNavy
                )

                Spacer(modifier = Modifier.height(32.dp))
                Text(text = "Expenses", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A))
                Spacer(modifier = Modifier.height(8.dp))
            }

            // 4. The Real List
            if (plan!!.items.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(top = 40.dp), contentAlignment = Alignment.Center) {
                        Text("No expenses yet. Time to add some!", color = Color.Gray)
                    }
                }
            } else {
                items(plan!!.items) { item ->
                    DetailItemCard(
                        title = item.name,
                        subtitle = "Manual Entry",
                        tag = item.source.name,
                        tagColor = Color.LightGray,
                        iconBgColor = Color(0xFFEEEEEE),
                        unitPrice = item.unitPrice,
                        qty = item.quantity,
                        subtotal = item.subtotal
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }

    // 🔌 The Bottom Sheet Trigger
    if (showAddSheet) {
        ManualEntrySheet(
            onDismiss = { showAddSheet = false },
            onAddExpense = { name, totalCost, qty, notes, category ->
                // TODO: Wire this to the ViewModel next!
                showAddSheet = false
                println("Adding item: $name, Subtotal: $totalCost, Qty: $qty")
            }
        )
    }
}