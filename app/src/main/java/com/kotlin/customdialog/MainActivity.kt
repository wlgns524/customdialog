package com.kotlin.customdialog

import android.content.Context
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var vibrator: Vibrator
    lateinit var ringtone: Ringtone

    var isBellOn = false
    var isVibrateOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO - 앱이 죽을 경우 Permission 확인
        // 벨소리, 진동모드, 무음 모드 바꿔가면서 확인
        findViewById<TextView>(R.id.tv_show).setOnClickListener {
            audioCheck()
        }
    }

    private fun audioCheck() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        when (audioManager.ringerMode) {
            // 진동 모드
            AudioManager.RINGER_MODE_VIBRATE -> {
                if (isVibrateOn) vibrateOff() else vibrateOn()
                isVibrateOn = !isVibrateOn
            }
            AudioManager.RINGER_MODE_NORMAL -> {
                if (isBellOn) bellOff() else bellOn()
                isBellOn = !isBellOn
            }
            else -> {
                if (isVibrateOn) vibrateOff() else vibrateOn()
                isVibrateOn = !isVibrateOn
            }
        }
    }

    private fun bellOn() {
        val uriManager = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        ringtone = RingtoneManager.getRingtone(this@MainActivity, uriManager)
        ringtone.play()
    }

    private fun bellOff() {
        ringtone.stop()
    }

    private fun vibrateOn() {
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        val DELAY = 0
        val VIBRATE = 1000
        val SLEEP = 1000
        val START = 0
        val vibratePattern = longArrayOf(DELAY.toLong(), VIBRATE.toLong(), SLEEP.toLong())
        vibrator.vibrate(VibrationEffect.createWaveform(vibratePattern, START))
    }

    private fun vibrateOff() {
        vibrator.cancel()
    }
}
