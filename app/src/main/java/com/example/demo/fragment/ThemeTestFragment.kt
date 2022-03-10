package com.example.demo.fragment

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.demo.SubActivity
import com.example.demo.databinding.FragmentThemeTestBinding
import com.example.demo.util.ThemeType
import com.example.demo.util.ThemeUtil
import timber.log.Timber

/**
 * 테마 설정을 변경 테스트
 */
class ThemeTestFragment : Fragment() {

    private lateinit var binding: FragmentThemeTestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThemeTestBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d(">>> onViewCreated Dark theme ${isSystemDarkMode()}")
        binding.btnLight.setOnClickListener {
            //mainViewModel.selectTheme(ThemeType.LIGHT_MODE)
            ThemeUtil.applyTheme(ThemeType.LIGHT_MODE)
        }
        binding.btnDark.setOnClickListener {
            //mainViewModel.selectTheme(ThemeType.DARK_MODE)
            ThemeUtil.applyTheme(ThemeType.DARK_MODE)
        }
    }

    private fun isSystemDarkMode(): Boolean {
        val nightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        Timber.d("nightMode is $nightMode")
        return when (nightMode) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            Configuration.UI_MODE_NIGHT_UNDEFINED -> false
            else -> false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("requestCode : $requestCode resultCode : $resultCode")
        if (requestCode == SubActivity.ACTIVITY_RESULT_CODE) {
            Toast.makeText(requireContext(), "onActivityResult >> $resultCode", Toast.LENGTH_SHORT)
                .show()
        }
    }
}