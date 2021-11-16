package com.example.appfootprint.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserRecollect")
data class UserRecollect(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int?,
                         @ColumnInfo(name = "material") var material: String,
                         @ColumnInfo(name = "cantidad") var cantidad: String,
                         @ColumnInfo(name = "fecha") var fecha: String,
                         @ColumnInfo(name = "co2") var co2: String) {

    constructor() : this(null, "","", "", "")
}
