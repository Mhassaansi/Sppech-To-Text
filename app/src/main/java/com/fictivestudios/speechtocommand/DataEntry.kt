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
import com.fictivestudios.speechtocommand.Common.Companion.open_mic
import kotlinx.coroutines.*
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
    var q10= false

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

    var type: String? = null

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)

        type = intent.getStringExtra("type").toString().lowercase(Locale.getDefault())

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


        val stringExerciesSchdule= arrayOf(R.string.q1exs,R.string.q2exs,R.string.q3exs,R.string.q4exs,R.string.q5exs,
        R.string.q6exs,R.string.q7exs,R.string.q8exs,R.string.q9exs)

        val stringExerciesPerformed= arrayOf(R.string.q1exp,R.string.q2exp,R.string.q3exp,R.string.q4exp,R.string.q5exp,
            R.string.q6exp,R.string.q7exp,R.string.q8exp,R.string.q9exp)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            GlobalScope.launch(Dispatchers.Main) {
                delay(8000)
                q1 = true
                if (type == "perfromed") {
                    textToSpeechEngine.speak(
                        getString(R.string.q1exp),
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "tts1")
                } else {
                    textToSpeechEngine.speak(
                        getString(R.string.q1exs),
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "tts1")}}

            textToSpeechEngine.setOnUtteranceProgressListener(object :
                UtteranceProgressListener() {
                override fun onStart(p0: String?) {
                }
                override fun onDone(p0: String?) {
                    if (q1) {
                        q1 = false
                            open_mic(RequestConstant.REQUEST_CODE_QEXS1,this@DataEntry)
                    } else if (q2) {
                        q2 = false
                            open_mic(RequestConstant.REQUEST_CODE_QEXS2,this@DataEntry)
                    } else if (q3) {
                        q3 = false
                            open_mic(RequestConstant.REQUEST_CODE_QEXS3,this@DataEntry)
                    } else if (q4) {
                        q4 = false
                            open_mic(RequestConstant.REQUEST_CODE_QEXS4,this@DataEntry)
                    } else if (q5) {
                        q5 = false
                            open_mic(RequestConstant.REQUEST_CODE_QEXS5,this@DataEntry)
                    } else if (q6) {
                        q6 = false
                            open_mic(RequestConstant.REQUEST_CODE_QEXS6,this@DataEntry)
                    } else if (q7) {
                        q7 = false
                            open_mic(RequestConstant.REQUEST_CODE_QEXS7,this@DataEntry)
                    } else if (q8) {
                        q8 = false
                            open_mic(RequestConstant.REQUEST_CODE_QEXS8,this@DataEntry)
                    } else if (q9) {
                        q9 = false
                            open_mic(RequestConstant.REQUEST_CODE_QEXS9,this@DataEntry)
                    }
                    else if(q10){
                        q10=false
                        open_mic(RequestConstant.REQUEST_CODE_Q4,this@DataEntry)
                    }
                }
                override fun onError(p0: String?) {
                }})}}


    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(this,
            TextToSpeech.OnInitListener { status ->
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeechEngine.language = Locale.US }})}


    @OptIn(DelicateCoroutinesApi::class)
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
                            if (type == "perfromed") {
                                textToSpeechEngine.speak(
                                    getString(R.string.q2exs),
                                    TextToSpeech.QUEUE_FLUSH,
                                    null,
                                    "tts1") } else {
                                textToSpeechEngine.speak(
                                    getString(R.string.q2exp),
                                    TextToSpeech.QUEUE_FLUSH,
                                    null,
                                    "tts1")}}}}}
            RequestConstant.REQUEST_CODE_QEXS2 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Retrieve the result array.
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    // Ensure result array is not null or empty to avoid errors.
                    if (!result.isNullOrEmpty()) {
                        // Recognized text is in the first position.
                        val recognizedText = result[0]
                        editText2!!.setText(recognizedText)
                        //  editText1!!.text = recognizedText.toString()
                        GlobalScope.launch(Dispatchers.Main) {
                            delay(200)
                            q3 = true
                            if (type == "performed") {
                                textToSpeechEngine.speak(
                                    getString(R.string.q3exs),
                                    TextToSpeech.QUEUE_FLUSH,
                                    null,
                                    "tts1")} else {
                                textToSpeechEngine.speak(
                                    getString(R.string.q3exp),
                                    TextToSpeech.QUEUE_FLUSH,
                                    null,
                                    "tts1")}}}}}
            RequestConstant.REQUEST_CODE_QEXS3 -> {

                val result = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                // Ensure result array is not null or empty to avoid errors.
                if (!result.isNullOrEmpty()) {
                    // Recognized text is in the first position.
                    val recognizedText = result[0]
                    editText3!!.setText(recognizedText)
                    //  editText1!!.text = recognizedText.toString()
                    GlobalScope.launch(Dispatchers.Main) {
                        delay(200)
                        q4 = true
                        if (type == "performed") {
                            textToSpeechEngine.speak(
                                getString(R.string.q4exs),
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1"
                            )
                        } else {
                            textToSpeechEngine.speak(
                                getString(R.string.q4exp),
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1")}}}}
            RequestConstant.REQUEST_CODE_QEXS4 -> {
                val result = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                // Ensure result array is not null or empty to avoid errors.
                if (!result.isNullOrEmpty()) {
                    // Recognized text is in the first position.
                    val recognizedText = result[0]
                    editText4!!.setText(recognizedText)
                    //  editText1!!.text = recognizedText.toString()
                    GlobalScope.launch(Dispatchers.Main) {
                        delay(200)
                        q5 = true
                        if (type == "performed") {
                            textToSpeechEngine.speak(
                                getString(R.string.q5exs),
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1"
                            )} else {
                            textToSpeechEngine.speak(
                                getString(R.string.q5exp),
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1")}}}}
            RequestConstant.REQUEST_CODE_QEXS5 -> {
                val result = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                // Ensure result array is not null or empty to avoid errors.
                if (!result.isNullOrEmpty()) {
                    // Recognized text is in the first position.
                    val recognizedText = result[0]
                    editText5!!.setText(recognizedText)
                    //  editText1!!.text = recognizedText.toString()
                    GlobalScope.launch(Dispatchers.Main) {
                        delay(200)
                        q6 = true
                        if (type == "performed") {
                            textToSpeechEngine.speak(
                                getString(R.string.q6exs),
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1")} else {
                            textToSpeechEngine.speak(
                                getString(R.string.q6exp),
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1")}}}}
            RequestConstant.REQUEST_CODE_QEXS6 -> {
                val result = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                // Ensure result array is not null or empty to avoid errors.
                if (!result.isNullOrEmpty()) {
                    // Recognized text is in the first position.
                    val recognizedText = result[0]
                    editText6!!.setText(recognizedText)
                    //  editText1!!.text = recognizedText.toString()
                    GlobalScope.launch(Dispatchers.Main) {
                        delay(200)
                        q7 = true
                        if (type == "performed") {
                            textToSpeechEngine.speak(
                                getString(R.string.q7exs),
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1")} else {
                            textToSpeechEngine.speak(
                                getString(R.string.q7exp),
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1"
                            )}}}}
            RequestConstant.REQUEST_CODE_QEXS7 -> {

                val result = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                // Ensure result array is not null or empty to avoid errors.
                if (!result.isNullOrEmpty()) {
                    // Recognized text is in the first position.
                    val recognizedText = result[0]
                    editText7!!.setText(recognizedText)
                    //  editText1!!.text = recognizedText.toString()
                    GlobalScope.launch(Dispatchers.Main) {
                        delay(200)
                        q8 = true
                        if (type == "performed") {
                            textToSpeechEngine.speak(
                                getString(R.string.q8exs),
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1")} else {
                            textToSpeechEngine.speak(
                                getString(R.string.q8exp),
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1"
                            )}}}}
            RequestConstant.REQUEST_CODE_QEXS8 -> {
                val result = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                // Ensure result array is not null or empty to avoid errors.
                if (!result.isNullOrEmpty()) {
                    // Recognized text is in the first position.
                    val recognizedText = result[0]
                    editText8!!.setText(recognizedText)
                    //  editText1!!.text = recognizedText.toString()
                    GlobalScope.launch(Dispatchers.Main) {
                        delay(200)
                        q9 = true
                        if (type == "performed") {
                            textToSpeechEngine.speak(
                                getString(R.string.q9exs),
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1")}
                        else {
                            textToSpeechEngine.speak(
                                getString(R.string.q9exp),
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1"
                            )}}}}
            RequestConstant.REQUEST_CODE_QEXS9 -> {

                val result = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                // Ensure result array is not null or empty to avoid errors.
                if (!result.isNullOrEmpty()) {
                    // Recognized text is in the first position.
                    val recognizedText = result[0]
                    editText9!!.setText(recognizedText)
                    //  editText1!!.text = recognizedText.toString()
                    GlobalScope.launch(Dispatchers.Main) {
                        delay(200)
                        q10 = true
                            textToSpeechEngine.speak(
                                getString(R.string.q4),
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1"
                            )}}}
            RequestConstant.REQUEST_CODE_Q4->
            {
                val result = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                // Ensure result array is not null or empty to avoid errors.
                if (!result.isNullOrEmpty()) {
                    // Recognized text is in the first position.
                    val recognizedText = result[0]
                 if(recognizedText.toString().lowercase()=="yes")
                    //  editText1!!.text = recognizedText.toString()
                 { checkBox!!.isChecked=true }
                    else{}}}}}

    override fun onPause() {
        textToSpeechEngine.stop()
        super.onPause()
    }

    override fun onDestroy() {
        textToSpeechEngine.shutdown()
        super.onDestroy()
    }
}