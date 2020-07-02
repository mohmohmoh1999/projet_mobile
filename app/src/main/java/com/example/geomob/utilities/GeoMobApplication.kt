package com.example.geomob.utilities

import android.app.Application
import com.example.geomob.ui.countryActivity.CountryViewModel
import com.example.geomob.ui.mainActivity.MainViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class GeoMobApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {

        import(androidXModule(this@GeoMobApplication))
        bind<MainViewModel>() with singleton { MainViewModel(instance()) }
        bind<CountryViewModel>() with singleton { CountryViewModel() }

    }
}