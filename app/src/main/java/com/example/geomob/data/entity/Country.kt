package com.example.geomob.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_table")
data class Country(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "country_id") val countryId: Int,
    val name: String,
    val capital: String,
    @ColumnInfo(name = "country_description") val countryDescription: String,
    val surface: String,
    val population: String,
    val flag: Int,
    val anthem: Int,
    val personalities : String,
    val resources : String,
    var favourite : Boolean = false
) {


    override fun hashCode(): Int {
        var result = countryId
        result = 31 * result + name.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Country

        if (name != other.name) return false

        return true
    }
}