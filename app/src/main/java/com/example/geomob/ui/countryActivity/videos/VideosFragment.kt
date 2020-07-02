package com.example.geomob.ui.countryActivity.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.geomob.R
import com.example.geomob.ui.countryActivity.CountryViewModel
import kotlinx.android.synthetic.main.videos_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class VideosFragment : Fragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModel: CountryViewModel by instance()
    private lateinit var adapter: VideoPagerAdapter
    private var dotsCount = 0
    private lateinit var dots: Array<ImageView?>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.videos_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
    }


    private fun initialize() {
        adapter = viewModel.countryClicked?.ListVideos?.let {
            VideoPagerAdapter(
                requireContext(),
                it
            )
        }!!
        video_slider.adapter = adapter
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
                        R.drawable.non_active_dots_white
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

            video_slider.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
                                R.drawable.non_active_dots_white
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
