package com.sandro.unifiedcostplanner.features.catalog.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogItemSheet(
    title: String,
    category: String,
    unitPrice: Double,
    onDismiss: () -> Unit,
    onAddToCart: (quantity: Int, totalCost: Double) -> Unit
) {
    val haptics = LocalHapticFeedback.current
    var quantity by remember { mutableStateOf(1) }
    val subtotal = unitPrice * quantity

    val surfaceColor = MaterialTheme.colorScheme.surface
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val surfaceVariantColor = MaterialTheme.colorScheme.surfaceVariant
    val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant
    val primaryColor = MaterialTheme.colorScheme.primary
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = surfaceColor,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            // 🖼️ The Hero Image Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.secondaryContainer,
                                MaterialTheme.colorScheme.tertiaryContainer
                            )
                        )
                    )
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                        .size(32.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }

            Column(modifier = Modifier.padding(24.dp)) {
                // 📝 Title and Category
                Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = onSurfaceColor)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "$category • SKU-${(1000..9999).random()}A", fontSize = 12.sp, color = onSurfaceVariantColor)

                Spacer(modifier = Modifier.height(24.dp))

                // 💰 Unit Price Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Unit Price", fontSize = 14.sp, color = onSurfaceVariantColor)
                    Surface(color = surfaceVariantColor, shape = RoundedCornerShape(8.dp)) {
                        Text(
                            text = "$ ${String.format(Locale.US, "%.2f", unitPrice)}",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            fontWeight = FontWeight.Bold,
                            color = onSurfaceColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 🔢 Quantity Selector Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Quantity", fontSize = 14.sp, color = onSurfaceVariantColor)

                    Surface(color = surfaceVariantColor, shape = RoundedCornerShape(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { if (quantity > 1) quantity-- },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = onSurfaceColor)
                            }

                            Text(
                                text = quantity.toString(),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.width(32.dp),
                                textAlign = TextAlign.Center,
                                color = onSurfaceColor
                            )

                            IconButton(
                                onClick = { quantity++ },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Increase", tint = onSurfaceColor)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // 📊 Subtotal row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text("Subtotal", color = onSurfaceVariantColor, fontSize = 14.sp)
                    Text(
                        text = "$${String.format(Locale.US, "%.2f", subtotal)}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = primaryColor
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 🛒 Add to Plan Button
                Button(
                    onClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        onAddToCart(quantity, subtotal)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor, contentColor = onPrimaryColor)
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add to Plan", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}