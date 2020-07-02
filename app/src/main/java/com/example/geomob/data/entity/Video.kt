package com.example.geomob.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_table")
data class Video(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "video_id") val videoId: Int,
    val country: Int,
    val title: String,
    val video: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Video

        if (videoId != other.videoId) return false
        if (country != other.country) return false
        if (video != other.video) return false

        return true
    }

    override fun hashCode(): Int {
        var result = videoId
        result = 31 * result + country
        result = 31 * result + video.hashCode()
        return result
    }
}