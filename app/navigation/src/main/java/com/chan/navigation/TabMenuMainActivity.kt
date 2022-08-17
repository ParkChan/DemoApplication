package com.chan.navigation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chan.navigation.databinding.ActivityTabBinding
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
        Log.d("CHAN >>>", "onCreate")

        Log.d("beokbeok", "MainActivity is $this")
        Log.d("beokbeok", "oneFragment is $oneFragment")
        Log.d("beokbeok", "twoFragment is $twoFragment")
        Log.d("beokbeok", "threeFragment is $threeFragment")
        Log.d("beokbeok", "fourFragment is $fourFragment")

        setupBinding()
        setupUI()
        setupListener()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("beokbeok", "onRestoreInstanceState")
    }

    override fun onResume() {
        super.onResume()
        Log.d("beokbeok", "onResume")
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
        viewModel.doTest()
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

        viewModel.testLiveData.observeEvent(this){
            Timber.d("CHAN >>> onViewCreated 액티비티")
        }
    }

    private fun showFragment(tag: String) {
        hideAllFragments()

        supportFragmentManager
            .beginTransaction()
            .show(supportFragmentManager.findFragmentByTag(tag) as Fragment)
            .commitAllowingStateLoss()
    }

    private fun hideAllFragments(){
        supportFragmentManager.fragments.forEach {
            it?.let {
                supportFragmentManager.beginTransaction().hide(it).commit()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("beokbeok", "onPause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("beokbeok", "onSaveInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("beokbeok", "onDestroy")
    }


}
