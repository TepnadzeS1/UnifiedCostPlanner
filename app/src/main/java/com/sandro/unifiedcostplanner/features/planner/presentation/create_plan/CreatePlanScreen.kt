package com.sandro.unifiedcostplanner.features.planner.presentation.create_plan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sandro.unifiedcostplanner.features.planner.presentation.create_plan.viewmodel.CreatePlanViewModel
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlanScreen(
    onNavigateBack: () -> Unit,
    viewModel: CreatePlanViewModel = hiltViewModel()
) {
    // 🎨 Premium UI Colors
    val backgroundColor = Color(0xFFF8F9FA)
    val fieldBackground = Color(0xFFEEEEEE)
    val textColor = Color(0xFF1A1A1A)

    // Optional: Add category chips for extra flair!
    val categories = listOf("General", "Operations", "Marketing", "Event", "Personal")
    var selectedCategory by remember { mutableStateOf("General") }

    Scaffold(containerColor = backgroundColor) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // 1. Custom Top Bar (Matches the rest of the app)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = fieldBackground,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onNavigateBack() }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = PrimaryNavy,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Create New Plan", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryNavy)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 2. Plan Name Input
            Text("Plan Name", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.title,
                onValueChange = { viewModel.title = it },
                placeholder = { Text("e.g., Summer Office Renovation", color = Color.Gray, fontSize = 14.sp) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = fieldBackground,
                    unfocusedContainerColor = fieldBackground,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Category Chips (Bonus UI!)
            Text("Category", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { category ->
                    val isSelected = category == selectedCategory
                    Surface(
                        color = if (isSelected) PrimaryNavy else fieldBackground,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.clickable { selectedCategory = category }
                    ) {
                        Text(
                            text = category,
                            color = if (isSelected) Color.White else Color.DarkGray,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Description Input
            Text("Description (Optional)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.description,
                onValueChange = { viewModel.description = it },
                placeholder = { Text("What is the goal of this plan?", color = Color.Gray, fontSize = 14.sp) },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = fieldBackground,
                    unfocusedContainerColor = fieldBackground,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom

            // 5. Save Button
            val isValid = viewModel.title.isNotBlank()
            Button(
                onClick = {
                    if (isValid) {
                        // NOTE: If your viewmodel's savePlan needs the category, pass it in!
                        // viewModel.savePlan(category = selectedCategory) { onNavigateBack() }
                        viewModel.savePlan { onNavigateBack() }
                    }
                },
                enabled = isValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryNavy,
                    disabledContainerColor = Color.LightGray
                )
            ) {
                Text("Create Plan", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}