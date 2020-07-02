package com.example.geomob.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.geomob.data.entity.*


@Dao
interface CountryDao {
    @Transaction
    @Query("SELECT * FROM country_table ORDER BY name ASC")
    fun getAllCountries(): LiveData<List<CountryWithAllAttributes?>?>?//so we can observe the changes

    @Transaction
    @Query("SELECT * FROM country_table WHERE favourite ORDER BY name ASC")
    fun getFavoritesCountries(): LiveData<List<CountryWithAllAttributes?>?>?//so we can observe the changes

    @Update
    fun updateCountry(country: Country)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCountries(countries: List<Country>)



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addHistories(histories: List<History>)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addVideos(videos: List<Video>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTwitter(twitters: List<Twitter>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addSlideshow(slideShows: List<SlideShow>)


}