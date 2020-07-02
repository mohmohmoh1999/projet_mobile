package com.example.geomob.ui.countryActivity.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.geomob.R
import com.example.geomob.data.entity.Country
import com.example.geomob.ui.countryActivity.CountryViewModel
import kotlinx.android.synthetic.main.info_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class InfoFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModel: CountryViewModel by instance()
    private lateinit var adapter: ImagePagerAdapter
    private lateinit var navController: NavController
    private var dotsCount = 0
    private lateinit var dots: Array<ImageView?>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController =
            requireActivity().let { Navigation.findNavController(it, R.id.nav_host_fragment) }

        //set slideshow
        setSlideShow()

        //go to anthem fragment on button click
        anthem.setOnClickListener {
            navController.navigate(R.id.to_anthem_fragment_action)
        }

        val country: Country = viewModel.countryClicked?.country!!
        description_view.text = country.countryDescription
        surface_view.text = country.surface
        population_view.text = country.population
        personalities_view.text = country.personalities
        resources_view.text = country.resources

    }

    private fun setSlideShow() {
        //set pager adapter
        adapter = viewModel.countryClicked?.ListSlideshow?.let {
            ImagePagerAdapter(
                requireContext(),
                it
            )
        }!!
        image_slider.adapter = adapter
        implementDots()
    }

    private fun implementDots() {
        try {
            dotsCount = adapter.count
            dots = arrayOfNulls(dotsCount)
            for (i in 0 until dotsCount) {
                dots[i] = ImageView(requireContext())
                dots[i]!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity().applicationContext,
                        R.drawable.non_active_dot
                    )
                )

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                params.setMargins(8, 0, 8, 0)

                slider_dots.addView(dots[i], params)
            }
            dots[0]?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity().applicationContext,
                    R.drawable.active_dot
                )
            )

            image_slider.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    for (i in 0 until dotsCount) {
                        dots[i]!!.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireActivity().applicationContext,
                                R.drawable.non_active_dot
                            )
                        )
                    }
                    dots[position]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext().applicationContext,
                            R.drawable.active_dot
                        )
                    )
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })

        } catch (e: ArrayIndexOutOfBoundsException) {
        }
    }
}





