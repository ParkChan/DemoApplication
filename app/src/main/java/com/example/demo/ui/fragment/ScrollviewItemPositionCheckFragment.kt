package com.example.demo.ui.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import com.example.demo.R
import com.example.demo.databinding.FragmentFolderbleTestBinding
import com.example.demo.databinding.FragmentScrollviewItemPositionCheckBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 스크롤 뷰안의 아이템이 현재 위치한 y 축의 위치를 확인
 *
 */
class ScrollviewItemPositionCheckFragment : Fragment() {

    private lateinit var binding: FragmentScrollviewItemPositionCheckBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScrollviewItemPositionCheckBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity?.window?.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.test_bg)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.svRoot.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            Timber.d(
//                "CHAN >>> scrollY is ${
//                    scrollY + binding.svRoot.height - getNavigationBarHeight(requireContext())
//                } binding.tvResult.y is ${binding.tvResult.y}"
//            )

            val button = binding.tvResult
            val location = IntArray(2)
            button.getLocationOnScreen(location)
            val y = location[1]

            Timber.d("CHAN >>> $y  ${binding.tvResult.y}")

//            if (scrollY + binding.svRoot.height - getNavigationBarHeight(requireContext()) > binding.tvResult.y) {
//                binding.tvMessage.visibility = View.VISIBLE
//                Timber.d("CHAN >>> 하단까지 모두 노출됨")
//            }
        }
    }

    private fun getNavigationBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }

    companion object {

    }
}
