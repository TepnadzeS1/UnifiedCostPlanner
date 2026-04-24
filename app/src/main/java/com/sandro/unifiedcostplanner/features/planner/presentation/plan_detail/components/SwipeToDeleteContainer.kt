package com.sandro.unifiedcostplanner.features.planner.presentation.plan_detail.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenWidthPx = with(density) { screenWidth.toPx() }
    
    // How far to swipe to "reveal" the delete button
    val revealThresholdPx = with(density) { -80.dp.toPx() }
    // How far to swipe to trigger automatic delete
    val autoDeleteThresholdPx = -screenWidthPx * 0.6f
    
    val offsetX = remember { Animatable(0f) }
    
    // We want the red background to show when offsetX < 0
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(Color.Red)
    ) {
        // --- DELETE BUTTON (Visible when swiped) ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    scope.launch {
                        offsetX.animateTo(-screenWidthPx)
                        onDelete(item)
                    }
                }
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White
            )
        }

        // --- CONTENT LAYER ---
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        scope.launch {
                            val newValue = (offsetX.value + delta).coerceAtMost(0f)
                            offsetX.snapTo(newValue)
                        }
                    },
                    onDragStopped = { velocity ->
                        scope.launch {
                            if (offsetX.value < autoDeleteThresholdPx || velocity < -1000f) {
                                // Full swipe or fast swipe: delete
                                offsetX.animateTo(-screenWidthPx)
                                onDelete(item)
                            } else if (offsetX.value < revealThresholdPx / 2) {
                                // Partial swipe: stay open at the reveal threshold
                                offsetX.animateTo(revealThresholdPx)
                            } else {
                                // Not enough swipe: snap back
                                offsetX.animateTo(0f)
                            }
                        }
                    }
                )
                .background(Color(0xFFF8F9FA)) // Cover the red area
        ) {
            content()
        }
    }
}
