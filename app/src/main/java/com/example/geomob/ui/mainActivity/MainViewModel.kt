package com.example.geomob.ui.mainActivity

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geomob.data.entity.Country
import com.example.geomob.data.entity.CountryWithAllAttributes
import com.example.geomob.data.repository.CountryRepository

class MainViewModel(@NonNull application: Application?) : ViewModel() {
    private val repository: CountryRepository = CountryRepository(application)
    private val allCountries: LiveData<List<CountryWithAllAttributes?>?>?
    private val favoritesCountries: LiveData<List<CountryWithAllAttributes?>?>?

    val showFavoriteLiveData = MutableLiveData<Boolean>(false)

    fun getAllCountries(): LiveData<List<CountryWithAllAttributes?>?>? {
        return allCountries
    }

    fun getFavoritesCountries(): LiveData<List<CountryWithAllAttributes?>?>? {
        return favoritesCountries
    }

    fun updateCountry(country: Country) {
        repository.updateCountry(country)
    }

    init {
        allCountries = repository.getAllCountries()
        favoritesCountries = repository.getFavoritesCountries()
    }
}