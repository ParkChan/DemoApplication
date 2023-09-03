package com.example.demo.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.demo.R
import com.example.demo.ui.activity.ItemTouchHelperActivity
import com.example.demo.ui.activity.PopupwindowActivity
import com.example.demo.ui.activity.WebViewActivity
import com.example.demo.databinding.FragmentMainBinding
import com.example.demo.ui.activity.ViewPager2TestActivity

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
            findNavController().navigate(R.id.action_MainFragment_to_ThemeTestFragment)
        }
        binding.btnDialogPosition.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_DialogPositionTestFragment)
        }
        binding.btnStartActivityTest.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_StartActivityTestFragment)
        }
        binding.btnLiveDataTest.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_LiveDataTestFragment)
        }
        binding.btnOneItemAutoScroll.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_OneItemAutoScrollFragment)
        }
        binding.btn2WayScroll.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_RecyclerLeftHoldScrollTestFragment)
        }
        binding.btnItemTouchHelper.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    ItemTouchHelperActivity::class.java
                ).apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP })
        }
        binding.btnWebviewDebug.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    WebViewActivity::class.java
                ).apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP })
        }
        binding.btnSpinner.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    PopupwindowActivity::class.java
                ).apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP })
        }
        binding.btnNav.setOnClickListener{
            startActivity(
                Intent(
                    context,
                    ViewPager2TestActivity::class.java
                ).apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP })
        }
    }
}