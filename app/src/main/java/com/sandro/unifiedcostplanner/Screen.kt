package com.sandro.unifiedcostplanner

sealed class Screen(val route: String) {
    object PlanList : Screen("plan_list")
    object CreatePlan : Screen("create_plan")

    // We can add "Details" later like this:
    // object PlanDetails : Screen("plan_details/{planId}")
}