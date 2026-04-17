package com.sandro.unifiedcostplanner.features.planner.data.local.converter

import androidx.room.TypeConverter
import com.sandro.unifiedcostplanner.features.planner.domain.model.SourceType

class Converters {
    @TypeConverter
    fun fromSourceType(value: SourceType): String {
        return value.name
    }

    @TypeConverter
    fun toSourceType(value: String): SourceType {
        return SourceType.valueOf(value)
    }
}