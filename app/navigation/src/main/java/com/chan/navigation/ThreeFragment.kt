package com.chan.navigation


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.chan.navigation.databinding.FragmentThreeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 라이트 | 다크모드 테스트
 */
class ThreeFragment : Fragment() {

    private lateinit var binding: FragmentThreeBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThreeBinding.inflate(layoutInflater)
        binding.btnLight.setOnClickListener {
            ThemeUtil.applyTheme(ThemeType.LIGHT_MODE)
        }
        binding.btnDark.setOnClickListener {
            ThemeUtil.applyTheme(ThemeType.DARK_MODE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.testLiveData.collect {
                    Timber.d( "onViewCreated Three")
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Timber.d( "onPause: Three")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d( "onSaveInstanceState: Three")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop: Three")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("onDestroyView: Three")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy: Three")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d( "onDetach: Three")
    }

    companion object {
        val TAG = ThreeFragment::class.java.simpleName.toString()
    }
}
