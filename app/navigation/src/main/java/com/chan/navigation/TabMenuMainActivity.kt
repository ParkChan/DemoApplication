package com.chan.navigation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.chan.navigation.databinding.ActivityTabBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
class TabMenuMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabBinding
    private val viewModel: MainViewModel by viewModels()

    private val oneFragment = OneFragment()
    private val twoFragment = TwoFragment()
    private val threeFragment = ThreeFragment()
    private val fourFragment = FourFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d(  "onCreate")

        setupBinding()
        setupUI()
        setupListener()

        viewModel.doTest()

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.d( "onRestoreInstanceState")
    }

    override fun onResume() {
        super.onResume()
        Timber.d( "onResume")
    }

    private fun setupBinding() {
        binding = ActivityTabBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupUI() {
        supportFragmentManager
            .beginTransaction()
            .run {
                if (supportFragmentManager.findFragmentByTag(FourFragment.TAG) == null) {
                    add(R.id.fl_main, fourFragment, FourFragment.TAG)
                    hide(fourFragment)
                }
                if (supportFragmentManager.findFragmentByTag(ThreeFragment.TAG) == null) {
                    add(R.id.fl_main, threeFragment, ThreeFragment.TAG)
                    hide(threeFragment)
                }
                if (supportFragmentManager.findFragmentByTag(TwoFragment.TAG) == null) {
                    add(R.id.fl_main, twoFragment, TwoFragment.TAG)
                    hide(twoFragment)
                }
                if (supportFragmentManager.findFragmentByTag(OneFragment.TAG) == null) {
                    add(R.id.fl_main, oneFragment, OneFragment.TAG)
                }
                commitAllowingStateLoss()
            }
    }

    private fun setupListener() {
        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_bottom_navigation_one -> showFragment(OneFragment.TAG)
                R.id.item_bottom_navigation_two -> showFragment(TwoFragment.TAG)
                R.id.item_bottom_navigation_three -> showFragment(ThreeFragment.TAG)
                R.id.item_bottom_navigation_four -> showFragment(FourFragment.TAG)
            }
            true
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.testLiveData.collect {
                    Timber.d("CHAN >>> onViewCreated 액티비티")
                }
            }
        }
    }

    private fun showFragment(tag: String) {
        hideAllFragments()

        supportFragmentManager
            .beginTransaction()
            .show(supportFragmentManager.findFragmentByTag(tag) as Fragment)
            .commitAllowingStateLoss()
    }

    private fun hideAllFragments() {
        supportFragmentManager.fragments.forEach {
            it?.let {
                supportFragmentManager.beginTransaction().hide(it).commit()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Timber.d(  "onPause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d( "onSaveInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d( "onDestroy")
    }


}
