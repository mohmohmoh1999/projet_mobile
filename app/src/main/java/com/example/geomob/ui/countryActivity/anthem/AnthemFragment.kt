package com.example.geomob.ui.countryActivity.anthem

import android.content.Context.AUDIO_SERVICE
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.geomob.R
import com.example.geomob.ui.countryActivity.CountryViewModel
import kotlinx.android.synthetic.main.anthem_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class AnthemFragment : Fragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModel: CountryViewModel by instance()
    private var player: MediaPlayer? = null
    private lateinit var audioManager : AudioManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.anthem_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.title  = "Anthem"
        viewModel.countryClicked?.country?.flag?.let { flag_view.setImageResource(it) }
        audioManager =
            requireActivity().applicationContext.getSystemService(
                AUDIO_SERVICE
            ) as AudioManager


        play.setOnClickListener {
            viewModel.countryClicked?.country?.anthem?.let { it1 -> play(it1) }
            play.visibility = View.GONE
            pause.visibility = View.VISIBLE
        }
        pause.setOnClickListener {
            pause()
            play.visibility = View.VISIBLE
            pause.visibility = View.GONE
        }
        volume_up.setOnClickListener {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
        }
        volume_down.setOnClickListener {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
        }

    }

    private fun play(anthem: Int) {
        if (player == null) {
            player = MediaPlayer.create(activity, anthem)
            player!!.setOnCompletionListener { stopPlayer() }

        }
        player!!.start()
    }

    private fun pause() {
        if (player != null) {
            player!!.pause()
        }
    }



    private fun stopPlayer() {
        if (player != null) {
            player!!.release()
            player = null
            Toast.makeText(activity, "Anthem released", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
        stopPlayer()
    }
}
