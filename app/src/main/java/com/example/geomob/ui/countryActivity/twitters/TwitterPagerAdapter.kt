package com.example.geomob.ui.countryActivity.twitters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.example.geomob.R
import com.example.geomob.data.entity.Twitter
import com.example.geomob.utilities.Browser
import com.example.geomob.utilities.ChromeClient

class TwitterPagerAdapter(
    private val context: Context,
    private val listTwitter: List<Twitter>
) :
    PagerAdapter() {
    private lateinit var inflater: LayoutInflater

    override fun getCount(): Int {
        return listTwitter.size
    }

    override fun isViewFromObject(@NonNull view: View, @NonNull `object`: Any): Boolean {
        return view === `object`
    }

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        inflater =
            context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.twitter_slide_item, container, false)
        setTwitter(
            itemView.findViewById(R.id.twitter_view),
            listTwitter[position].twitter,
            itemView.findViewById(R.id.progress_bar)
        )
        container.addView(itemView)
        return itemView
    }


    override fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {
        container.removeView(`object` as View)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setTwitter(webView: WebView, twitter: String, progressBar: ProgressBar) {
        webView.webViewClient = object : Browser() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        }
        webView.webChromeClient = ChromeClient(context as Activity)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.setAppCacheEnabled(true)
        webView.loadUrl(twitter)
    }
}