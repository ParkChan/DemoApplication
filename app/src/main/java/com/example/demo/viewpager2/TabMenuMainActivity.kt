package com.example.demo.viewpager2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivityViewpager2TestBinding
import timber.log.Timber

class ViewPager2TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewpager2TestBinding
    private val viewModel: MainViewModel by viewModels()

    private val oneFragment = OneFragment()
    private val twoFragment = TwoFragment()
    private val threeFragment = ThreeFragment()
    private val fourFragment = FourFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d(  "onCreate")

        setupBinding()
    }

    private fun setupBinding() {
        binding = ActivityViewpager2TestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
