package com.sandro.unifiedcostplanner.features.planner.presentation.plan_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualEntrySheet(
    onDismiss: () -> Unit,
    onAddExpense: (name: String, price: Double, quantity: Int, notes: String, category: String) -> Unit
) {
    // State for the form fields
    var itemName by remember { mutableStateOf("") }
    var priceInput by remember { mutableStateOf("") }
    var quantityInput by remember { mutableStateOf("1") }
    var notes by remember { mutableStateOf("") }

    val categories = listOf("Equipment", "Software", "Office Supplies", "Services")
    var selectedCategory by remember { mutableStateOf("Office Supplies") }

    // Calculate Estimated Total dynamically
    val price = priceInput.toDoubleOrNull() ?: 0.0
    val quantity = quantityInput.toIntOrNull() ?: 1
    val estimatedTotal = price * quantity

    // Figma Colors
    val fieldBackground = Color(0xFFEEEEEE) // Light grey for inputs
    val textColor = Color(0xFF1A1A1A)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        dragHandle = null // Removing standard handle to match your Figma's clean top
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // --- HEADER ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = PrimaryNavy)
                }
                Text(
                    text = "Manual Entry",
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryNavy,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(24.dp)) // Balance the close icon
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- ITEM NAME ---
            Text("Item Name", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = itemName,
                onValueChange = { itemName = it },
                placeholder = { Text("e.g., Office Chairs", color = Color.Gray, fontSize = 14.sp) },
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

            Spacer(modifier = Modifier.height(16.dp))

            // --- PRICE & QUANTITY ROW ---
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Price", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = textColor)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = priceInput,
                        onValueChange = { priceInput = it },
                        leadingIcon = { Text("$", color = Color.Gray) },
                        placeholder = { Text("0.00", color = Color.Gray) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = fieldBackground,
                            unfocusedContainerColor = fieldBackground,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Quantity", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = textColor)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = quantityInput,
                        onValueChange = { quantityInput = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = fieldBackground,
                            unfocusedContainerColor = fieldBackground,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- ESTIMATED TOTAL BOX ---
            Surface(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Estimated Total", color = Color.Gray, fontSize = 14.sp)
                    Text(
                        text = "$${String.format(Locale.US, "%.2f", estimatedTotal)}",
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = PrimaryNavy
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- CATEGORY PILLS ---
            Text("Category", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))
            @OptIn(ExperimentalLayoutApi::class)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = category == selectedCategory
                    Surface(
                        color = if (isSelected) PrimaryNavy else fieldBackground,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.clickable { selectedCategory = category }
                    ) {
                        Text(
                            text = category,
                            color = if (isSelected) Color.White else Color.Gray,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- NOTES ---
            Text("Notes (Optional)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                placeholder = { Text("Add any details, links, or specifications...", color = Color.Gray, fontSize = 14.sp) },
                modifier = Modifier.fillMaxWidth().height(100.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = fieldBackground,
                    unfocusedContainerColor = fieldBackground,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- ADD BUTTON ---
            Button(
                onClick = {
                    if (itemName.isNotBlank() && price > 0) {
                        onAddExpense(itemName, estimatedTotal, quantity, notes, selectedCategory)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryNavy)
            ) {
                Text("Add to Plan", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp)) // Bottom padding
        }
    }
}