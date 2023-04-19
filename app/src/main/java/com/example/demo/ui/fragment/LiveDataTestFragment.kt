package com.example.demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.demo.databinding.FragmentLivedataTestBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 라이브데이터 post 와 value의 차이점을 확인
 */
class LiveDataTestFragment : Fragment() {

    private lateinit var binding: FragmentLivedataTestBinding
    val number = MutableLiveData<Int>()
    var observeCnt = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLivedataTestBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::binding.isInitialized) {
            return
        }
        val scope = CoroutineScope(Dispatchers.Main)
        binding.btnSet.setOnClickListener {
            scope.launch {
                observeCnt = 0
                Toast.makeText(context, "곧 시작합니다.", Toast.LENGTH_SHORT).show()
                number.value = 0
                delay(START_DELAY_TIME)
                for (num: Int in 1..TEST_CNT) {
                    number.value = num
                }
            }
        }
        binding.btnPost.setOnClickListener {
            scope.launch {
                observeCnt = 0
                Toast.makeText(context, "곧 시작합니다.", Toast.LENGTH_SHORT).show()
                number.postValue(0)
                delay(START_DELAY_TIME)
                for (num: Int in 1..TEST_CNT) {
                    number.postValue(num)
                }
            }
        }
        number.observe(viewLifecycleOwner) {

            binding.tvNumber.text = it.toString()
            binding.tvObserveCnt.text = "observe cnt is ${observeCnt ++}"
        }
    }
    companion object {
        private const val START_DELAY_TIME = 500L
        private const val TEST_CNT = 1000
    }
}