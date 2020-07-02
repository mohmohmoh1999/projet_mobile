package com.example.geomob

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.geomob.data.dao.CountryDao
import com.example.geomob.data.database.CountryDatabase
import com.example.geomob.data.entity.*
import com.example.geomob.data.fileConverter.ImageConverter
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var countryDao: CountryDao
    private lateinit var countryDatabase: CountryDatabase
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        countryDatabase = Room.inMemoryDatabaseBuilder(
            context, CountryDatabase::class.java
        ).build()
        countryDao = countryDatabase.countryDao()!!
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        countryDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeCountryAndReadInList() {
        val imageConverter  =
            ImageConverter()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val  icon : Bitmap = BitmapFactory.decodeResource(context.resources,
        R.drawable.img)

        val country = Country(1, "Algeria", "my country", "2 m km2", "40 m",imageConverter.fileToByteArray(icon))
        val history1 = History(1, 1, "01-1-1954", "revolution")
        val history2 = History(2, 1, "05-07-1962", "Independence")
        val personality = Personality(1, 1, "Arabs", "first personality")
        val resource = Resource(1, 1, "Oil", "first Resource")

        val countryWithAllAttributes = CountryWithAllAttributes(
            country,
            arrayListOf(
                history1,
                history2
            ),
            arrayListOf(personality),
            arrayListOf(resource)
        )
        countryDao.addCountry(country)
        countryDao.addHistory(history1)
        countryDao.addHistory(history2)
        countryDao.addPersonality(personality)
        countryDao.addResource(resource)
        val countrySelected : CountryWithAllAttributes = countryDao.getCountryByName("Algeria")
        assertThat(countrySelected, equalTo(countryWithAllAttributes))
    }
}