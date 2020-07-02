package com.example.geomob.ui.countryActivity.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.example.geomob.R
import com.example.geomob.data.entity.History
import com.squareup.picasso.Picasso

class HistoryPagerAdapter(
    private val context: Context,
    private val listHistory: List<History>
) :
    PagerAdapter() {
    private lateinit var inflater: LayoutInflater

    override fun getCount(): Int {
        return listHistory.size
    }

    override fun isViewFromObject(@NonNull view: View, @NonNull `object`: Any): Boolean {
        return view === `object`
    }

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        inflater =
            context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.history_slide_item, container, false)
        setImage(itemView.findViewById(R.id.image_view),listHistory[position].image)
        itemView.findViewById<TextView>(R.id.history_date).text = listHistory[position].date
        itemView.findViewById<TextView>(R.id.history_description).text = listHistory[position].historyDescription
        container.addView(itemView)
        return itemView
    }


    override fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {
        container.removeView(`object` as View)
    }

    private fun setImage(imageView: ImageView, image: String) {
        Picasso.get()
            .load(image)
            .fit()
            .centerCrop()
            .into(imageView);

    }


}