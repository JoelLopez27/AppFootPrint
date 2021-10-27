package com.example.appfootprint.db

import androidx.room.TypeConverter
import com.example.appfootprint.models.Source

class Converters {
    @TypeConverter
    fun fromSources(source: Source): String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}