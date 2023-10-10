package com.example.demo.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demo.databinding.ActivityMockHttpTestBinding
import com.example.demo.databinding.ActivityViewpager2TestBinding
import com.example.demo.ui.fragment.viewpager2.FourFragment
import com.example.demo.ui.fragment.viewpager2.OneFragment
import com.example.demo.ui.fragment.viewpager2.ThreeFragment
import com.example.demo.ui.fragment.viewpager2.TwoFragment
import com.example.demo.ui.viewmodel.MockInterceptorViewModel
import com.example.demo.ui.viewmodel.ViewPagerViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ViewMockHttpTestActivity : AppCompatActivity() {

    private val binding: ActivityMockHttpTestBinding by lazy {
        ActivityMockHttpTestBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<MockInterceptorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        fetchBookmarkList()
    }

    private fun setupBinding() {
        binding.lifecycleOwner = this
        binding.vm = viewModel
        setContentView(binding.root)
    }

    private fun fetchBookmarkList() {
        viewModel.fetchBookmarkList()
    }

}
