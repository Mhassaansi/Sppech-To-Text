package com.fictivestudios.speechtocommand

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.util.*

class Create_Exercies : AppCompatActivity() {

    var txtView: TextView? = null
    var txtView1: TextView? = null
    var txtView2: TextView? = null


    var q1=false
    var q2=false
    var q3=false
    @SuppressLint("ObsoleteSdkInt")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_exercies)

      //  var text = getString(R.string.name)
      //  var text1 =
     //   var text2 = getString(R.string.weight)

        txtView = findViewById<TextView>(R.id.text)
        txtView1 = findViewById<TextView>(R.id.text1)
        txtView2 = findViewById<TextView>(R.id.text2)


        // Check if user hasn't input any text.
        if (getString(R.string.q1).isNotEmpty()) {
            // Lollipop and above requires an additional ID to be passed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                GlobalScope.launch(Dispatchers.Main)  {
                    delay(200)
                    q1=true
                    textToSpeechEngine.speak(getString(R.string.q1), TextToSpeech.QUEUE_FLUSH, null, "tts1")
                }

                textToSpeechEngine.setOnUtteranceProgressListener(object:
                    UtteranceProgressListener() {
                    override fun onStart(p0: String?) {
                    }

                    override fun onDone(sample: String?) {

                        if(q1){
                            q1=false
                            open_mic(RequestConstant.REQUEST_CODE_Q1)

                            Toast.makeText(this@Create_Exercies,sample,Toast.LENGTH_LONG).show()
                        }
                        else if(q2){
                            q2=false
                            open_mic(RequestConstant.REQUEST_CODE_Q2)

                        }
                        else if(q3){
                            q3=false
                            open_mic(RequestConstant.REQUEST_CODE_Q3)

                        }
                        else{

                        }

                    }

                    override fun onError(p0: String?) {
                    }
                })
            } else {
//                // Call Legacy function
//                textToSpeechEngine.speak(text, TextToSpeech.QUEUE_ADD, null)
//                open_mic()
//                textToSpeechEngine.speak(text1, TextToSpeech.QUEUE_ADD, null, "tts1")
//                open_mic()
//                textToSpeechEngine.speak(text2, TextToSpeech.QUEUE_ADD, null, "tts1")
//                open_mic()
            }
        } else {
            Toast.makeText(this, "Text cannot be empty", Toast.LENGTH_LONG).show()
        }

    }


    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(this,
            TextToSpeech.OnInitListener { status ->
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
            RequestConstant.REQUEST_CODE_Q1 -> {
                // Safety checks to ensure data is available.
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Retrieve the result array.
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    // Ensure result array is not null or empty to avoid errors.
                    if (!result.isNullOrEmpty()) {
                        // Recognized text is in the first position.
                        val recognizedText = result[0]
                        txtView!!.text = recognizedText
                        GlobalScope.launch(Dispatchers.Main) {
                            delay(200)
                            q2=true
                            textToSpeechEngine.speak(getString(R.string.q2), TextToSpeech.QUEUE_FLUSH, null, "tts1")
                        }


                    }
                }
            }


            RequestConstant.REQUEST_CODE_Q2 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                    if (!result.isNullOrEmpty()) {
                        val recognizedText = result[0]
                        txtView1!!.text = recognizedText
                        GlobalScope.launch(Dispatchers.Main)  {
                            delay(200)
                            q3=true
                            textToSpeechEngine.speak(getString(R.string.q3), TextToSpeech.QUEUE_FLUSH, null, "tts1")
                        }
                    }
                }
            }
            RequestConstant.REQUEST_CODE_Q3 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                    if (!result.isNullOrEmpty()) {
                        val recognizedText = result[0]
                        txtView2!!.text = recognizedText

                        if(recognizedText.toString()=="Performed " || recognizedText.toString()=="Scheduled" ){
                            val intent=Intent(this,DataEntry::class.java)
                            intent.putExtra("type",recognizedText.toString())
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this,"Wrong Data",Toast.LENGTH_LONG).show()
                        }
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


    fun open_mic(requestCode: Int) {
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
            startActivityForResult(sttIntent, requestCode)
        } catch (e: ActivityNotFoundException) {
            // Handling error when the service is not available.
            e.printStackTrace()
            Toast.makeText(this, "Your device does not support STT.", Toast.LENGTH_LONG).show()
        }


    }
}