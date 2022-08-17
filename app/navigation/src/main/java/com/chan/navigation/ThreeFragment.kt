package com.chan.navigation


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.chan.navigation.databinding.FragmentThreeBinding
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
//        viewModel.testLiveData.observeEvent(viewLifecycleOwner) {
//            Timber.d("CHAN >>> onViewCreated Three")
//        }
        viewModel.testLiveData.observeEvent(viewLifecycleOwner){
            Timber.d("CHAN >>> onViewCreated Three")
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("CHAN >>>", "onPause: Three")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("CHAN >>>", "onSaveInstanceState: Three")
    }

    override fun onStop() {
        super.onStop()
        Log.d("CHAN >>>", "onStop: Three")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("CHAN >>>", "onDestroyView: Three")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CHAN >>>", "onDestroy: Three")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("CHAN >>>", "onDetach: Three")
    }

    companion object {
        val TAG = ThreeFragment::class.java.simpleName.toString()
    }
}
