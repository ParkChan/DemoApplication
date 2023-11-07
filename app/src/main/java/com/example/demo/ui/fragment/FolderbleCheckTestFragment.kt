package com.example.demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import com.example.demo.databinding.FragmentFolderbleTestBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 참조 : https://developer.android.com/guide/topics/large-screens/make-apps-fold-aware?hl=ko
 * Folerble 폰인지 확인하는 테스트 코드
 */
class FolderbleCheckTestFragment : Fragment() {

    private lateinit var binding: FragmentFolderbleTestBinding
    val number = MutableLiveData<Int>()
    var observeCnt = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFolderbleTestBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                activity?.let {
                    WindowInfoTracker.getOrCreate(it)
                        .windowLayoutInfo(it)
                        .collectLatest { newLayoutInfo ->
                            if (newLayoutInfo.displayFeatures.isNotEmpty()) {
                                // Use newLayoutInfo to update the layout.
                                val result = extractTypeAndState(newLayoutInfo.displayFeatures.toString())
                                Snackbar.make(binding.root, "폴더블 디바이스 입니다. $result", Snackbar.LENGTH_INDEFINITE).show()
                            } else {
                                Snackbar.make(binding.root, "폴더블이 아닙니다.", Snackbar.LENGTH_INDEFINITE).show()
                            }
                        }
                }

            }
        }
    }

    companion object {
        fun extractTypeAndState(str: String): Pair<String, String> {
            // 정규식을 이용하여 type 과 state 값을 찾는다
            val regex = Regex("type=(\\w+), state=(\\w+)")
            val matchResult = regex.find(str)

            return if (matchResult != null) {
                // 그룹 1과 2에 해당하는 값들을 변수에 저장한다
                val type = matchResult.groupValues[1]
                val state = matchResult.groupValues[2]
                Pair(type, state)
            } else {
                // 매치되는 값이 없으면 에러 메시지를 출력한다
                Timber.d("No match found")
                Pair("", "")
            }
        }
    }
}
