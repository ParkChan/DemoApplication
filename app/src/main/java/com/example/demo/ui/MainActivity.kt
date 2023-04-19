package com.example.demo.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
//        Timber.d("lifecycle test >>> MainActivity lifeCycle is onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)


        viewModel.start()

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