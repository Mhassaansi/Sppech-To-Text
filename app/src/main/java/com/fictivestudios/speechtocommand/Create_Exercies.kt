package com.fictivestudios.speechtocommand

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Create_Exercies : AppCompatActivity() {

    var txtView: TextView? = null
    var txtView1: TextView? = null
    var txtView2: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_exercies)
        val text = getString(R.string.name)
        val text1 = getString(R.string.height)
        val text2 = getString(R.string.weight)


        txtView = findViewById<TextView>(R.id.text)
        txtView1 = findViewById<TextView>(R.id.text1)
        txtView2 = findViewById<TextView>(R.id.text2)


        // Check if user hasn't input any text.
        if (text.isNotEmpty()) {
            // Lollipop and above requires an additional ID to be passed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Call Lollipop+ function
                textToSpeechEngine.speak(text, TextToSpeech.QUEUE_ADD, null, "tts1")
                open_mic()
                textToSpeechEngine.speak(text1, TextToSpeech.QUEUE_ADD, null, "tts1")
                open_mic()
                textToSpeechEngine.speak(text2, TextToSpeech.QUEUE_ADD, null, "tts1")
                open_mic()
            } else {
                // Call Legacy function
                textToSpeechEngine.speak(text, TextToSpeech.QUEUE_ADD, null)
                open_mic()
                textToSpeechEngine.speak(text1, TextToSpeech.QUEUE_ADD, null, "tts1")
                open_mic()
                textToSpeechEngine.speak(text2, TextToSpeech.QUEUE_ADD, null, "tts1")
                open_mic()
            }
        } else {
            Toast.makeText(this, "Text cannot be empty", Toast.LENGTH_LONG).show()
        }

    }


    private val textToSpeechEngine: TextToSpeech by lazy {
        // Pass in context and the listener.
        TextToSpeech(this,
            TextToSpeech.OnInitListener { status ->
                // set our locale only if init was success.
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeechEngine.language = Locale.US
                }
            }
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // Handle the result for our request code.
            REQUEST_CODE_STT -> {
                // Safety checks to ensure data is available.
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Retrieve the result array.
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    // Ensure result array is not null or empty to avoid errors.
                    if (!result.isNullOrEmpty()) {
                        // Recognized text is in the first position.
                        val recognizedText = result[0]
                        txtView!!.text = recognizedText
                    }
                }
            }


            REQUEST_CODE_STT1->{
                if(resultCode==Activity.RESULT_OK && data !=null){
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                    if(!result.isNullOrEmpty()){
                        val recognizedText = result[0]
                        txtView1!!.text = recognizedText
                    }
                }
            }
            REQUEST_CODE_STT2->{
                if(resultCode==Activity.RESULT_OK && data !=null){
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                    if(!result.isNullOrEmpty()){
                        val recognizedText = result[0]
                        txtView2!!.text = recognizedText
                    }
                }
            }
        }
    }

    override fun onPause() {
        textToSpeechEngine.stop()
        super.onPause()
    }

    override fun onDestroy() {
        textToSpeechEngine.shutdown()
        super.onDestroy()
    }

    companion object {
        private const val REQUEST_CODE_STT = 1
        private const val REQUEST_CODE_STT1 = 2
        private const val REQUEST_CODE_STT2 = 3


    }

    fun open_mic() {
        // Get the Intent action
        val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        // Language model defines the purpose, there are special models for other use cases, like search.
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        // Adding an extra language, you can use any language from the Locale class.
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        // Text that shows up on the Speech input prompt.
        sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")
        try {
            // Start the intent for a result, and pass in our request code.
            startActivityForResult(sttIntent, REQUEST_CODE_STT)
        } catch (e: ActivityNotFoundException) {
            // Handling error when the service is not available.
            e.printStackTrace()
            Toast.makeText(this, "Your device does not support STT.", Toast.LENGTH_LONG).show()
        }


    }
}