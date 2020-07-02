package com.example.geomob.ui.countryActivity.info

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.example.geomob.R
import com.example.geomob.data.entity.SlideShow
import com.squareup.picasso.Picasso

class ImagePagerAdapter(
    private val context: Context,
    private val listImage: List<SlideShow>
) :
    PagerAdapter() {
    private lateinit var inflater: LayoutInflater

    override fun getCount(): Int {
        return listImage.size
    }

    override fun isViewFromObject(@NonNull view: View, @NonNull `object`: Any): Boolean {
        return view === `object`
    }

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        inflater =
            context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.image_slide_item, container, false)
        setImage(itemView.findViewById(R.id.image_view), listImage[position].slideshow)
        container.addView(itemView)
        return itemView
    }


    override fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {
        container.removeView(`object` as View)
    }

    private fun setImage(imageView: ImageView, slideshow: String) {
        Picasso.get()
            .load(slideshow)
            .fit()
            .centerCrop()
            .into(imageView)

    }
}