package com.example.demo.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.svRoot.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            Timber.d(
                "CHAN >>> scrollY is ${
                    binding.svRoot.height + scrollY + getNavigationBarHeight(
                        requireContext()
                    )
                } binding.tvResult.y is ${binding.tvResult.y}"
            )

            if (binding.svRoot.height + scrollY + getNavigationBarHeight(requireContext()) > binding.tvResult.y) {
                binding.tvMessage.visibility = View.VISIBLE
            }
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
