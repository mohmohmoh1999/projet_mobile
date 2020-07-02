package com.example.geomob.ui.countryActivity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.example.geomob.R
import kotlinx.android.synthetic.main.activity_country.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class CountryActivity : AppCompatActivity(), KodeinAware {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    override val kodein by closestKodein()
    private val viewModel: CountryViewModel by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        /*************Initialisation*************************/
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        //set tool bar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.info_fragment,
                R.id.videos_fragment,
                R.id.twitters_fragment,
                R.id.history_fragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
        /*************************************************************/
        //initialize the navigation header
        val header: View? = nav_view.getHeaderView(0)
        val countryNameView = header?.findViewById<TextView>(R.id.country_name_view)
        val capitalView = header?.findViewById<TextView>(R.id.capital_view)
        val flagView = header?.findViewById<ImageView>(R.id.flag_view)
        flagView?.setImageResource(viewModel.countryClicked!!.country.flag)
        countryNameView?.text = viewModel.countryClicked!!.country.name
        capitalView?.text = viewModel.countryClicked!!.country.capital
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
