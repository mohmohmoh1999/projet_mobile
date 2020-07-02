package com.example.geomob.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "twitter_table")
data class Twitter(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "twitter_id") val twitterId: Int,
    val country: Int,
    val twitter: String
)