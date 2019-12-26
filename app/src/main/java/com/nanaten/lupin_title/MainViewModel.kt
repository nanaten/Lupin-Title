package com.nanaten.lupin_title

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Handler
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*


class MainViewModel : ViewModel() {
    val title = ObservableField<String>()
    val typewriter = ObservableField<String>()
    val isVisible = ObservableField<Boolean>()

    fun startTitleCall(context: Context) {
        // SoundPool
        val attributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
        val sound = SoundPool.Builder().setMaxStreams(1).setAudioAttributes(attributes).build()
        val resType = sound.load(context, R.raw.typewriter, 1)
        val resCall = sound.load(context, R.raw.title, 1)

        val title = title.get()
        if(title.isNullOrBlank()) return

        isVisible.set(true)
        GlobalScope.launch {
            title.map {
                delay(100)
                sound.play(resType, 1.0f, 1.0f, 0, 0, 1.0f)
                typewriter.set(it.toString())
            }
            delay(150)
            sound.play(resCall, 1.0f, 1.0f, 0, 0, 1.0f)
            typewriter.set(title)
            delay(2000)
            isVisible.set(false)
            typewriter.set("")
        }
    }

}