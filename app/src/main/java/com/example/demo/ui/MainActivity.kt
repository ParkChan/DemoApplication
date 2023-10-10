package com.example.demo.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.ui.viewmodel.LogEventViewModel
import com.example.demo.ui.viewmodel.MockInterceptorViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.net.URI


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        Timber.d("lifecycle Test >>> MainActivity onCreate")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.d("lifecycle Test >>> MainActivity onRestart")
    }
    override fun onStart() {
        super.onStart()
        Timber.d("lifecycle Test >>> MainActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("lifecycle Test >>> MainActivity onResume")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Timber.d("lifecycle Test >>> MainActivity onNewIntent")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("lifecycle Test >>> MainActivity onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d(">>> MainActivity onDestroy")
    }
}