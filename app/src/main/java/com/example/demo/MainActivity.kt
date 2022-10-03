package com.example.demo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.databinding.ActivitySecondBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
//        Timber.d("lifecycle test >>> MainActivity lifeCycle is onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.d("lifecycle Test >>> MainActivity onCreate")
        //emit 시작
//        MainScope().launch {
//            viewModel.startSendDataToStateFlow()
//        }
//
//        MainScope().launch {
//            viewModel.stateFlow.collect {
//                Timber.d("Test CHAN >>> stateFlow #1: $it - ${Thread.currentThread().name}")
//            }
//            Timber.d("Test CHAN >>> State Collect End #1 - ${Thread.currentThread().name}")
//        }

//        viewModel.startSendDataToSharedFlow()
//        //참조: https://developer.android.com/kotlin/flow/stateflow-and-sharedflow?hl=ko
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.sharedFlow.collect {
//                    Timber.d("Test CHAN >>> sharedFlow #2: $it - ${Thread.currentThread().name}")
//                }
//                Timber.d("Test CHAN >>> State Collect End #2 - ${Thread.currentThread().name}")
//            }
//        }
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