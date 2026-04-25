package com.sandro.unifiedcostplanner.features.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandro.unifiedcostplanner.features.planner.domain.repository.PlannerRepository
import com.sandro.unifiedcostplanner.features.settings.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: PlannerRepository,
    private val userPrefs: UserPreferencesRepository
) : ViewModel() {

    val isDarkMode = userPrefs.isDarkMode.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    val isNotificationsEnabled = userPrefs.isNotificationsEnabled.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
    val selectedCurrency = userPrefs.selectedCurrency.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "GEL")

    // 🚀 NEW: Functions to update preferences
    fun toggleDarkMode(enabled: Boolean) = viewModelScope.launch { userPrefs.updateDarkMode(enabled) }
    fun toggleNotifications(enabled: Boolean) = viewModelScope.launch { userPrefs.updateNotifications(enabled) }
    fun updateCurrency(currency: String) = viewModelScope.launch { userPrefs.updateCurrency(currency) }

    fun eraseAllPlans() {
        viewModelScope.launch {
            repository.getPlans().first().forEach { plan ->
                repository.deletePlan(plan.id)
            }
        }
    }

    fun generateCsvData(onCsvReady: (String) -> Unit) {
        viewModelScope.launch {
            // 1. Fetch the latest snapshot of all plans
            val plans = repository.getPlans().first()

            // 2. Build the CSV Header
            val csvBuilder = StringBuilder()
            csvBuilder.append("Plan Title,Category,Item Name,Source,Quantity,Unit Price,Subtotal\n")

            // 3. Loop through everything and format it
            plans.forEach { plan ->
                if (plan.items.isEmpty()) {
                    // Export empty plans too!
                    csvBuilder.append("${escape(plan.title)},${escape(plan.category)},No Items,-,0,0.00,0.00\n")
                } else {
                    plan.items.forEach { item ->
                        csvBuilder.append(
                            "${escape(plan.title)}," +
                                    "${escape(plan.category)}," +
                                    "${escape(item.name)}," +
                                    "${item.source.name}," +
                                    "${item.quantity}," +
                                    "${item.unitPrice}," +
                                    "${item.subtotal}\n"
                        )
                    }
                }
            }

            // 4. Send the finished text back to the UI
            onCsvReady(csvBuilder.toString())
        }
    }

    // Helper function to prevent commas in names from breaking the CSV layout
    private fun escape(text: String): String {
        return "\"${text.replace("\"", "\"\"")}\""
    }
}