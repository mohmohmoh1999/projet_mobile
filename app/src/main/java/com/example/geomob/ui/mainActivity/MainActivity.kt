package com.example.geomob.ui.mainActivity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geomob.R
import com.example.geomob.data.entity.Country
import com.example.geomob.data.entity.CountryWithAllAttributes
import com.example.geomob.ui.countryActivity.CountryActivity
import com.example.geomob.ui.countryActivity.CountryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModel: MainViewModel by instance()
    private lateinit var adapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set recycle view adapter
        val recyclerView: RecyclerView = list_countries as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        adapter = CountryAdapter()
        recyclerView.adapter = adapter

        //set Countries in recycler view
        viewModel.showFavoriteLiveData.observe(this, Observer { showFavorite ->
            title = if (showFavorite) {
                viewModel.getFavoritesCountries()
                    ?.observe(this,
                        Observer { countries -> adapter.setCountries(countries as List<CountryWithAllAttributes>) })
                "Favorites Countries"
            } else {
                viewModel.getAllCountries()
                    ?.observe(this,
                        Observer { countries -> adapter.setCountries(countries as List<CountryWithAllAttributes>) })
                "GeoMob"
            }
        })

        //on item click
        adapter.setOnItemClickListener(object : CountryAdapter.OnItemClickListener {
            override fun onItemClick(country: CountryWithAllAttributes) {
                val countryViewModel: CountryViewModel by instance()
                countryViewModel.countryClicked = country
                val intent = Intent(this@MainActivity, CountryActivity::class.java)
                startActivity(intent)
            }

            //on favorite button click
            override fun onFavoriteClick(country: Country) {
                country.favourite = !country.favourite
                viewModel.updateCountry(country)
            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.popup_menu, menu)
        val favoritesCountriesItem = menu!!.findItem(R.id.favourite)
        val allCountriesItem = menu.findItem(R.id.all)

        viewModel.showFavoriteLiveData.observe(this, Observer { showFavorite ->
            if (showFavorite) {
                favoritesCountriesItem.isVisible = false
                allCountriesItem.isVisible = true
            } else {
                favoritesCountriesItem.isVisible = true
                allCountriesItem.isVisible = false
            }
        })
        //set search bar
        val searchItem = menu.findItem(R.id.search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object :
            OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favourite -> {
                viewModel.showFavoriteLiveData.value = true
                true
            }
            R.id.all -> {
                viewModel.showFavoriteLiveData.value = false
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
