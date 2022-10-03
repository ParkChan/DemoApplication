package com.example.demo.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivitySecondBinding
import timber.log.Timber

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onRestart() {
        super.onRestart()
        Timber.d("lifecycle Test >>> SubActivity onRestart")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.d("lifecycle Test >>> SubActivity onCreate")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("lifecycle Test >>> SubActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("lifecycle Test >>> SubActivity onResume")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Timber.d("lifecycle Test >>> SubActivity onNewIntent")
        Toast.makeText(this, "onNewIntent", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Timber.d("lifecycle Test >>> SubActivity onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("lifecycle Test >>> SubActivity onDestroy")
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("testKey", "안녕하세요")
        setResult(ACTIVITY_RESULT_CODE, intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d(">>> SecondActivity requestCode : $requestCode resultCode : $resultCode")
    }

    companion object {
        const val ACTIVITY_RESULT_CODE = 1000
        fun start(activity: Activity) {
            val intent = Intent(activity, SecondActivity::class.java)
            activity.startActivity(intent)
        }

        fun startActivityResult(activity: Activity) {
            val intent = Intent(activity, SecondActivity::class.java)
            activity.startActivityForResult(intent, 0)
        }
    }
}