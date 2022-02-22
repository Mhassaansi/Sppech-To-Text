package com.fictivestudios.speechtocommand

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.*

class Set_Goal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goal)


        setTextToSpeech()

    }

    private fun setTextToSpeech() {
      val  textToSpeech = TextToSpeech(this@Set_Goal, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
               // val languageToSpeak = Locale(intent.getStringExtra(DESIRED_LOCALE))
               // val result = textToSpeech.setLanguage(languageToSpeak)
//                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                  //  toast("This language is not supported for text-to-speech")
//                } else {
//                    languageCanBeSpoken = true
//                }
            } else
                Log.e("error", "Initilisation Failed!")
        })

        class speechListener : UtteranceProgressListener() {
            override fun onDone(utteranceId: String?) {
               // toast("finished speaking")
            }

            override fun onError(utteranceId: String?) {
              //  toast("error when speaking")
            }

            override fun onStart(utteranceId: String?) {
              //  toast("speaking started")
            }

        }

        textToSpeech.setOnUtteranceProgressListener(speechListener())
    }
}