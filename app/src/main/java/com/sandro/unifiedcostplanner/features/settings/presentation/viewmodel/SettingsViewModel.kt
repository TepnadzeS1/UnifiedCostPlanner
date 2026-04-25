package com.sandro.unifiedcostplanner.features.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandro.unifiedcostplanner.features.planner.domain.repository.PlannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: PlannerRepository
) : ViewModel() {

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