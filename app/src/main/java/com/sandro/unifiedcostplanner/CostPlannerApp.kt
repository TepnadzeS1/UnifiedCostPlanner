package com.sandro.unifiedcostplanner

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // This is the "Magic" annotation that starts Hilt
class CostPlannerApp : Application()