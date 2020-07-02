package com.example.geomob.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class History(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "history_id") val historyId: Int,
    val country: Int,
    val date: String,
    @ColumnInfo(name = "history_description") val historyDescription: String,
    val image: String
)