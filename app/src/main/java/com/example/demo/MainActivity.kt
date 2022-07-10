package com.example.demo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivityMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("lifecycle test >>> MainActivity lifeCycle is onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        MainScope().launch {
            viewModel.startSendDataToSharedFlow()
        }

        MainScope().launch {
            viewModel.sharedFlow.collect {
                Timber.d("Test CHAN >>> sharedFlow #2: $it - ${Thread.currentThread().name}")
            }
            Timber.d("Test CHAN >>> State Collect End #2 - ${Thread.currentThread().name}")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("lifecycle test >>> MainActivity lifeCycle is onDestroy")
    }
}