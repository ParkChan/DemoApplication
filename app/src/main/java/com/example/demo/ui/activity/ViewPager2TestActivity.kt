package com.example.demo.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demo.databinding.ActivityViewpager2TestBinding
import com.example.demo.ui.fragment.viewpager2.FourFragment
import com.example.demo.ui.fragment.viewpager2.OneFragment
import com.example.demo.ui.fragment.viewpager2.ThreeFragment
import com.example.demo.ui.fragment.viewpager2.TwoFragment
import com.example.demo.ui.viewmodel.ViewPagerViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ViewPager2TestActivity : AppCompatActivity() {

    private val binding: ActivityViewpager2TestBinding by lazy {
        ActivityViewpager2TestBinding.inflate(layoutInflater)
    }
    private val viewModel: ViewPagerViewModel by viewModels()
    private val pagerAdapter by lazy { SamplePagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d(  "onCreate")

        setupBinding()
        setupPagerAdapter()
        setupTabLayout()
    }

    private fun setupBinding() {
        setContentView(binding.root)
    }

    private fun setupPagerAdapter() {
        binding.vpSample.adapter = pagerAdapter
    }

    private fun setupTabLayout() {
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.vpSample) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()

    }

    private fun startSystemEvent() {
        val count = 1..10
        count.forEach {
            CoroutineScope(Dispatchers.Main).launch {
                delay(1500)
                viewModel.reportSystemEvent(it)
            }
        }
    }

    class SamplePagerAdapter(activity : AppCompatActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> OneFragment.newInstance()
                1 -> TwoFragment.newInstance()
                2 -> ThreeFragment.newInstance()
                3 -> FourFragment.newInstance()
                else -> OneFragment.newInstance()
            }
        }

    }

}
