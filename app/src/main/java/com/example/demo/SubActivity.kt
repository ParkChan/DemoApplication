package com.example.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivitySubBinding
import timber.log.Timber

class SubActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.d(">>> SubActivity onCreate")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Timber.d(">>> SubActivity onNewIntent")
        Toast.makeText(this, "onNewIntent", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("testKey", "안녕하세요")
        setResult(ACTIVITY_RESULT_CODE, intent)
        finish()
    }

    companion object {
        const val ACTIVITY_RESULT_CODE = 1000
        fun start(activity: Activity) {
            val intent = Intent(activity, SubActivity::class.java)
            activity.startActivity(intent)
        }

        fun startActivityResult(activity: Activity) {
            val intent = Intent(activity, SubActivity::class.java)
            activity.startActivityForResult(intent, 0)
        }
    }
}