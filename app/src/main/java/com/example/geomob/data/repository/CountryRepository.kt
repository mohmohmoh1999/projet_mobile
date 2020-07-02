package com.example.geomob.data.repository

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.geomob.data.dao.CountryDao
import com.example.geomob.data.database.CountryDatabase
import com.example.geomob.data.entity.Country
import com.example.geomob.data.entity.CountryWithAllAttributes

class CountryRepository (application: Application?) {


    private val countryDao: CountryDao?
    private val allCountries: LiveData<List<CountryWithAllAttributes?>?>?
    private val favoritesCountries: LiveData<List<CountryWithAllAttributes?>?>?

    init {
        val database: CountryDatabase? = CountryDatabase.getInstance(application as Context)
        countryDao = database?.countryDao()
        allCountries = countryDao?.getAllCountries()
        favoritesCountries = countryDao?.getFavoritesCountries()
    }


    fun getAllCountries(): LiveData<List<CountryWithAllAttributes?>?>? {
        return allCountries
    }

    fun getFavoritesCountries(): LiveData<List<CountryWithAllAttributes?>?>? {
        return favoritesCountries
    }


    fun updateCountry(country: Country) {
        countryDao?.let { UpdateCountryAsyncTask(it).execute(country) }
    }

    class UpdateCountryAsyncTask(private val countryDao: CountryDao) :
        AsyncTask<Country?, Void?, Void?>() {
        override fun doInBackground(vararg countries: Country?): Void? {
            countries[0]?.let { countryDao.updateCountry(it) }
            return null
        }
    }
}

