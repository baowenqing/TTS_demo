package com.android.testdemo

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 *  TTS 文字转语音功能
 *  谷歌支持的语种最多
 *  其次百度
 *  测试讯飞的效果最差 可能包有点老了
 *
 *  一般不需要单独设置播放语种  会自动识别 并把语音转化为音频输出
 */
class MainActivity : AppCompatActivity() {
     val TAG = "MainActivity"
    lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTTS()
        btn_tts.setOnClickListener {
//            startAuto("支付宝到账!!!!!!   一!! 百 !!!万 !! 元")
            startAuto("Love is more thicker than forget")
        }

    }


    fun startAuto(data: String) {
        // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
        textToSpeech.setPitch(1.0f);
        // 设置语速
        textToSpeech.setSpeechRate(0.6f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null, null)
        };
    }

    fun initTTS() {
//实例化自带语音对象
        textToSpeech = TextToSpeech(this, TextToSpeech.OnInitListener {

            if (it == TextToSpeech.SUCCESS) {
                //可以用此方法选择要使用的引擎库
//                textToSpeech.setEngineByPackageName("com.google.android.tts")
                textToSpeech.setPitch(1.0f);//方法用来控制音调
                textToSpeech.setSpeechRate(1.0f);//用来控制语速


//                val result = textToSpeech.setLanguage(Locale.CHINA)
                val result = textToSpeech.setLanguage(Locale.US)
                if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                    && result != TextToSpeech.LANG_AVAILABLE
                ) {
                    Toast.makeText(
                        this, "TTS暂时不支持这种语音的朗读！",
                        Toast.LENGTH_SHORT
                    ).show();
                }


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Log.e(TAG, "${textToSpeech.engines.map {
                        it.name
                    }}")
                    Log.e(TAG, "${textToSpeech.defaultVoice}")
                    Log.e(TAG, "${textToSpeech.defaultEngine}")


                    Log.e(TAG, textToSpeech.availableLanguages.map {
                        it.displayName
                    }.toString())

                }
            } else {
                Toast.makeText(this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
            }

        });

    }


    override fun onStop() {
        super.onStop()
        textToSpeech.stop(); // 不管是否正在朗读TTS都被打断


    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.shutdown(); // 关闭，释放资源
    }


}