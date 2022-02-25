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
import com.fictivestudios.speechtocommand.Common.Companion.open_mic
import kotlinx.coroutines.*
import java.util.*

class Create_Exercies : AppCompatActivity() {

    var txtView: TextView? = null
    var txtView1: TextView? = null
    var txtView2: TextView? = null
    var txtView3: TextView? = null
    var txtView4: TextView? = null
    var txtView5: TextView? = null


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
        txtView3=findViewById<TextView>(R.id.ans)
        txtView4=findViewById<TextView>(R.id.ans1)
        txtView5=findViewById<TextView>(R.id.ans2)


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
                            open_mic(RequestConstant.REQUEST_CODE_Q1,this@Create_Exercies)

                            Toast.makeText(this@Create_Exercies,sample,Toast.LENGTH_LONG).show()
                        }
                        else if(q2){
                            q2=false
                            open_mic(RequestConstant.REQUEST_CODE_Q2,this@Create_Exercies)

                        }
                        else if(q3){
                            q3=false
                            open_mic(RequestConstant.REQUEST_CODE_Q3,this@Create_Exercies)

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
                        txtView3!!.text = recognizedText
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
                        txtView4!!.text = recognizedText
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
                        txtView5!!.text = recognizedText

                        if(recognizedText.toString().lowercase(Locale.getDefault())=="performed" || recognizedText.toString().lowercase(Locale.getDefault())=="schedule" ){
                            val intent=Intent(this,DataEntry::class.java)
                            intent.putExtra("type",recognizedText.toString())
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this,"Speak Again",Toast.LENGTH_LONG).show()
                            GlobalScope.launch(Dispatchers.Main)  {
                                delay(200)
                                q3=true
                                textToSpeechEngine.speak(getString(R.string.q3), TextToSpeech.QUEUE_FLUSH, null, "tts1")
                            }

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
}