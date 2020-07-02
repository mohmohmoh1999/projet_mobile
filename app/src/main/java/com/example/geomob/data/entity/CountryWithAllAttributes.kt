package com.example.geomob.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CountryWithAllAttributes(
    @Embedded val country: Country,
    @Relation(
        parentColumn = "country_id",
        entityColumn = "country"
    )
    val ListHistories: List<History>,
    @Relation(
        parentColumn = "country_id",
        entityColumn = "country"
    )
    val ListVideos: List<Video>,
    @Relation(
        parentColumn = "country_id",
        entityColumn = "country"
    )
    val ListSlideshow: List<SlideShow>,
    @Relation(
        parentColumn = "country_id",
        entityColumn = "country"
    )
    val ListTwitter: List<Twitter>

)