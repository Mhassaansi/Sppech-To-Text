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
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class DataEntry : AppCompatActivity() {

    var q1 = false
    var q2 = false
    var q3 = false
    var q4 = false
    var q5 = false
    var q6 = false
    var q7 = false
    var q8 = false
    var q9 = false

    var editText1: EditText? = null
    var editText2: EditText? = null
    var editText3: EditText? = null
    var editText4: EditText? = null
    var editText5: EditText? = null
    var editText6: EditText? = null
    var editText7: EditText? = null
    var editText8: EditText? = null
    var editText9: EditText? = null
    var checkBox: CheckBox? = null
    var saveButton: Button? = null

    var type = intent.getStringExtra("type").toString()

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)


        editText1 = findViewById<EditText>(R.id.editTextTextPersonName)
        editText2 = findViewById<EditText>(R.id.editTextTextPersonName2)
        editText3 = findViewById<EditText>(R.id.editTextTextPersonName3)
        editText4 = findViewById<EditText>(R.id.editTextTextPersonName4)
        editText5 = findViewById<EditText>(R.id.editTextTextPersonName5)
        editText6 = findViewById<EditText>(R.id.editTextTextPersonName6)
        editText7 = findViewById<EditText>(R.id.editTextTextPersonName7)
        editText8 = findViewById<EditText>(R.id.editTextTextPersonName8)
        editText9 = findViewById<EditText>(R.id.editTextTextPersonName9)
        checkBox = findViewById<CheckBox>(R.id.checkBox)
        saveButton = findViewById<Button>(R.id.save)

        if (type == "Performed") {
            var qes1 = getString(R.string.q1exp)
            var qes2 = getString(R.string.q2exp)
            var qes3 = getString(R.string.q3exp)
            var qes4 = getString(R.string.q4exp)
            var qes5 = getString(R.string.q5exp)
            var qes6 = getString(R.string.q6exp)
            var qes7 = getString(R.string.q7exp)
            var qes8 = getString(R.string.q8exp)
            var qes9 = getString(R.string.q9exp)
        } else {
            var qes1 = getString(R.string.q1exs)
            var qes2 = getString(R.string.q2exs)
            var qes3 = getString(R.string.q3exs)
            var qes4 = getString(R.string.q4exs)
            var qes5 = getString(R.string.q5exs)
            var qes6 = getString(R.string.q6exs)
            var qes7 = getString(R.string.q7exs)
            var qes8 = getString(R.string.q8exs)
            var qes9 = getString(R.string.q9exs)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            GlobalScope.launch(Dispatchers.Main) {
                delay(1000)
                q1 = true
                if (type == "Performed") {

                    textToSpeechEngine.speak(
                        getString(R.string.q1exp),
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "tts1"
                    )
                } else {

                    textToSpeechEngine.speak(
                        getString(R.string.q1exs),
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "tts1"
                    )
                }

            }

            textToSpeechEngine.setOnUtteranceProgressListener(object :
                UtteranceProgressListener() {
                override fun onStart(p0: String?) {
                }

                override fun onDone(p0: String?) {
                    if (q1) {
                        q1 = false
                        open_mic(RequestConstant.REQUEST_CODE_QEXS1)
                    } else if (q2) {
                        q2 = false
                        open_mic(RequestConstant.REQUEST_CODE_QEXS2)
                    } else if (q3) {
                        q3 = false
                        open_mic(RequestConstant.REQUEST_CODE_QEXS3)
                    } else if (q4) {
                        q4 = false
                        open_mic(RequestConstant.REQUEST_CODE_QEXS4)
                    } else if (q5) {
                        q5 = false
                        open_mic(RequestConstant.REQUEST_CODE_QEXS5)
                    } else if (q6) {
                        q6 = false
                        open_mic(RequestConstant.REQUEST_CODE_QEXS6)
                    } else if (q7) {
                        q7 = false
                        open_mic(RequestConstant.REQUEST_CODE_QEXS7)
                    } else if (q8) {
                        q8 = false
                        open_mic(RequestConstant.REQUEST_CODE_QEXS8)
                    } else if (q9) {
                        q9 = false
                        open_mic(RequestConstant.REQUEST_CODE_QEXS9)
                    }
                }

                override fun onError(p0: String?) {

                }
            })

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
            RequestConstant.REQUEST_CODE_QEXS1 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Retrieve the result array.
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    // Ensure result array is not null or empty to avoid errors.
                    if (!result.isNullOrEmpty()) {
                        // Recognized text is in the first position.
                        val recognizedText = result[0]
                        editText1!!.setText(recognizedText)
                        //  editText1!!.text = recognizedText.toString()
                        GlobalScope.launch(Dispatchers.Main) {
                            delay(200)
                            q2 = true
                            if (type == "Performed") {
                                textToSpeechEngine.speak(
                                    getString(R.string.q2exs),
                                    TextToSpeech.QUEUE_FLUSH,
                                    null,
                                    "tts1"
                                )
                            } else {
                                textToSpeechEngine.speak(
                                    getString(R.string.q2exp),
                                    TextToSpeech.QUEUE_FLUSH,
                                    null,
                                    "tts1"
                                )
                            }

                        }


                    }
                }
            }
            RequestConstant.REQUEST_CODE_QEXS2 -> {}
            RequestConstant.REQUEST_CODE_QEXS3 -> {}
            RequestConstant.REQUEST_CODE_QEXS4 -> {}
            RequestConstant.REQUEST_CODE_QEXS5 -> {}
            RequestConstant.REQUEST_CODE_QEXS6 -> {}
            RequestConstant.REQUEST_CODE_QEXS7 -> {}
            RequestConstant.REQUEST_CODE_QEXS8 -> {}
            RequestConstant.REQUEST_CODE_QEXS9 -> {}
        }


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

    override fun onPause() {
        textToSpeechEngine.stop()
        super.onPause()
    }

    override fun onDestroy() {
        textToSpeechEngine.shutdown()
        super.onDestroy()
    }
}