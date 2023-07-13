package com.example.texttospeech

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.texttospeech.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    private lateinit var binding:ActivityMainBinding
    private lateinit var tts:TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            tts= TextToSpeech(this@MainActivity,this@MainActivity)
            btnstart.setOnClickListener {
                val text=edittext.text.toString()
                if(text.isEmpty()) Toast.makeText(this@MainActivity,"Please Enter to Text",Toast.LENGTH_SHORT).show()
                else speak(text)
            }
        }

    }

    override fun onInit(status: Int) {

        if(status==TextToSpeech.SUCCESS)
        {
            val result=tts.setLanguage(Locale.US)

            if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this@MainActivity,"Language not Supported",Toast.LENGTH_SHORT).show()
        }else{
            binding.btnstart.isEnabled=true
            tts.setSpeechRate(0.5f)
        }
        }else
        {
            Toast.makeText(this@MainActivity,"error please try again",Toast.LENGTH_SHORT).show()
        }
    }
    private fun speak(text:String){
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")

    }

    override fun onDestroy() {
        if(tts.isSpeaking)
            tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}