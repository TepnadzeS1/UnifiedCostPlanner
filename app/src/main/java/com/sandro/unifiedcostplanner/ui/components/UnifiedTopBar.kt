package com.sandro.unifiedcostplanner.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandro.unifiedcostplanner.ui.theme.PrimaryNavy
import com.sandro.unifiedcostplanner.ui.theme.ProfileGray

@Composable
fun UnifiedTopBar(
    modifier: Modifier = Modifier,
    profileContent: @Composable () -> Unit = {
        Text("UP", color = Color.DarkGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = PrimaryNavy)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Unified Cost Planner",
                color = PrimaryNavy,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Surface(
            shape = CircleShape,
            color = ProfileGray,
            modifier = Modifier.size(36.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                profileContent()
            }
        }
    }
}