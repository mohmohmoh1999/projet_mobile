package com.example.geomob.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(tableName = "slideshow_table")
data class SlideShow(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "slideshow_id") val slideshowId: Int,
    val country: Int,
    val slideshow : String
)