package com.example.demo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.demo.R
import com.example.demo.databinding.FragmentMainBinding

/**
 * 테스트 항목들을 추가하는 메인 화면
 */
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::binding.isInitialized) {
            return
        }
        binding.btnTheme.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_FirstFragment)
        }
        binding.btnDialogPosition.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_SecondFragment)
        }
        binding.btnStartActivityTest.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_StartActivityTestFragment)
        }
    }
}