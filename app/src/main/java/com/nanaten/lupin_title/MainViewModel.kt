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
    val isTyping = ObservableField<Boolean>()

    fun startTitleCall(context: Context) {
        // SoundPool
        val attributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
        val sound = SoundPool.Builder().setMaxStreams(1).setAudioAttributes(attributes).build()
        val resType = sound.load(context, R.raw.typewriter, 1)
        val resCall = sound.load(context, R.raw.title, 1)

        val fullTitle = title.get()
        if(fullTitle.isNullOrBlank()) return

        isTyping.set(true)
        GlobalScope.launch {
            fullTitle.map {
                delay(150)
                sound.play(resType, 1.0f, 1.0f, 0, 0, 1.0f)
                typewriter.set(it.toString())
            }
            delay(150)
            sound.play(resCall, 1.0f, 1.0f, 0, 0, 1.0f)
            typewriter.set(fullTitle)
            delay(2000)
            isTyping.set(false)
            typewriter.set("")
        }
    }


}