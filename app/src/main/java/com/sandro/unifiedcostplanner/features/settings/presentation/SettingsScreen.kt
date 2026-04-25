package com.sandro.unifiedcostplanner.features.settings.presentation

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sandro.unifiedcostplanner.features.settings.presentation.viewmodel.SettingsViewModel
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    // 🚀 FIXED: Dynamic Material colors so Dark Mode actually works!
    val backgroundColor = MaterialTheme.colorScheme.background
    val surfaceColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onBackground
    val secondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant

    val context = LocalContext.current

    val darkThemeEnabled by viewModel.isDarkMode.collectAsState()
    val notificationsEnabled by viewModel.isNotificationsEnabled.collectAsState()
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()

    var showCurrencyDialog by remember { mutableStateOf(false) }
    var showEraseDialog by remember { mutableStateOf(false) }

    Scaffold(containerColor = backgroundColor) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 8.dp)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // 1. Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Unified Cost Planner", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Settings", fontSize = 32.sp, fontWeight = FontWeight.Black, color = textColor)

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Profile Card (Stays Navy in both themes)
            Surface(
                color = PrimaryNavy,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.2f), modifier = Modifier.size(56.dp)) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White, modifier = Modifier.padding(12.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Sandro", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Pro Plan Member", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                    }
                    Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 3. General Settings
            Text("GENERAL", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = secondaryTextColor, modifier = Modifier.padding(start = 8.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Surface(color = surfaceColor, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Column {
                    SettingsItem(
                        icon = Icons.Default.Payments,
                        title = "Default Currency",
                        subtitle = "$selectedCurrency (₾)",
                        textColor = textColor,
                        secondaryColor = secondaryTextColor,
                        onClick = { showCurrencyDialog = true }
                    )
                    HorizontalDivider(color = backgroundColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    SettingsToggleItem(icon = Icons.Default.Notifications, title = "Push Notifications", checked = notificationsEnabled, textColor = textColor, secondaryColor = secondaryTextColor, onCheckedChange = { viewModel.toggleNotifications(it) })
                    HorizontalDivider(color = backgroundColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    SettingsToggleItem(icon = Icons.Default.DarkMode, title = "Dark Theme", checked = darkThemeEnabled, textColor = textColor, secondaryColor = secondaryTextColor, onCheckedChange = { viewModel.toggleDarkMode(it) })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Data & Privacy
            Text("DATA & PRIVACY", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = secondaryTextColor, modifier = Modifier.padding(start = 8.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Surface(color = surfaceColor, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Column {
                    SettingsItem(icon = Icons.Default.Download, title = "Export Data to CSV", textColor = textColor, secondaryColor = secondaryTextColor, onClick = {
                        viewModel.generateCsvData { csvContent ->
                            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/csv"
                                putExtra(Intent.EXTRA_SUBJECT, "Unified Cost Planner - Export")
                                putExtra(Intent.EXTRA_TEXT, csvContent)
                            }
                            val shareIntent = Intent.createChooser(sendIntent, "Export Plans to CSV")
                            context.startActivity(shareIntent)
                        }
                    })
                    HorizontalDivider(color = backgroundColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    SettingsItem(
                        icon = Icons.Default.DeleteForever,
                        title = "Erase All Plans",
                        titleColor = Color(0xFFD32F2F),
                        textColor = textColor,
                        secondaryColor = secondaryTextColor,
                        onClick = { showEraseDialog = true }
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // App Version
            Text(
                text = "Unified Cost Planner v1.0.0",
                color = secondaryTextColor,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(100.dp)) // Bottom Nav Clearance
        }
    }

    // --- Dialogs ---

    if (showCurrencyDialog) {
        val currencies = listOf("GEL", "USD", "EUR", "GBP")
        AlertDialog(
            onDismissRequest = { showCurrencyDialog = false },
            title = { Text("Select Default Currency") },
            text = {
                Column {
                    currencies.forEach { currency ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.updateCurrency(currency)
                                    showCurrencyDialog = false
                                }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = currency == selectedCurrency, onClick = null)
                            Spacer(Modifier.width(8.dp))
                            Text(currency)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCurrencyDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showEraseDialog) {
        AlertDialog(
            onDismissRequest = { showEraseDialog = false },
            title = { Text("Erase All Data?") },
            text = { Text("This will permanently delete all your plans and items. This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.eraseAllPlans()
                        showEraseDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                ) {
                    Text("Erase Everything")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEraseDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// Helper Components updated to support dynamic colors
@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    titleColor: Color? = null,
    textColor: Color,
    secondaryColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = secondaryColor, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = titleColor ?: textColor)
        }
        if (subtitle != null) {
            Text(subtitle, fontSize = 14.sp, color = secondaryColor)
        } else {
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = secondaryColor.copy(alpha = 0.5f))
        }
    }
}

@Composable
fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    textColor: Color,
    secondaryColor: Color,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = secondaryColor, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = textColor, modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PrimaryNavy)
        )
    }
}