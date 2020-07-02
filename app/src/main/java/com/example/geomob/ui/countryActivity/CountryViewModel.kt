package com.example.geomob.ui.countryActivity

import androidx.lifecycle.ViewModel
import com.example.geomob.data.entity.CountryWithAllAttributes

class CountryViewModel() : ViewModel() {
    var countryClicked: CountryWithAllAttributes? = null
}