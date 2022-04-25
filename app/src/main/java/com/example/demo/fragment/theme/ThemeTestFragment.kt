package com.example.demo.fragment.theme

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.demo.databinding.FragmentThemeTestBinding
import timber.log.Timber

/**
 * 테마 설정을 변경 테스트
 * 다이얼로그 위에서 테마 설정을 변경하더라도 일반적인 상황에서의 다이얼로그는 유지 된다.
 */
class ThemeTestFragment : Fragment() {

    private lateinit var binding: FragmentThemeTestBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("lifecycle test >>> ThemeTestFragment lifeCycle is onAttach")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("lifecycle test >>> ThemeTestFragment lifeCycle is onCreateView")
        binding = FragmentThemeTestBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPopup.setOnClickListener {
            val dialog = ThemeDialog()
            dialog.show(childFragmentManager, null)
        }
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("lifecycle test >>> ThemeTestFragment lifeCycle is onDetach")
    }
}