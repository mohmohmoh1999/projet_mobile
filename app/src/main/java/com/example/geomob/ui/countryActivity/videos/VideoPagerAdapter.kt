package com.example.geomob.ui.countryActivity.videos

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.example.geomob.R
import com.example.geomob.data.entity.Video
import com.example.geomob.utilities.Browser
import com.example.geomob.utilities.ChromeClient


class VideoPagerAdapter(
    private val context: Context,
    private val listVideos: List<Video>
) :
    PagerAdapter() {
    private lateinit var inflater: LayoutInflater

    override fun getCount(): Int {
        return listVideos.size
    }

    override fun isViewFromObject(@NonNull view: View, @NonNull `object`: Any): Boolean {
        return view === `object`
    }

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        inflater =
            context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.video_slide_item, container, false)
        setVideo(
            itemView.findViewById(R.id.video_view),
            listVideos[position].video,
            itemView.findViewById(R.id.progress_bar)
        )
        itemView.findViewById<TextView>(R.id.title_view).text = listVideos[position].title
        container.addView(itemView)
        return itemView
    }


    override fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {
        container.removeView(`object` as View)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setVideo(webView: WebView, video: String, progressBar: ProgressBar) {
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
        webView.loadUrl(video)

    }

}