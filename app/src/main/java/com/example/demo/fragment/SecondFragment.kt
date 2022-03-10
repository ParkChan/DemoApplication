package com.example.demo.fragment

import android.os.Bundle
import android.view.View
import com.example.demo.databinding.FragmentSecondBinding
import com.example.demo.dialog.CommonDialog
import com.example.demo.dialog.RecyclerViewItemTouchEvent
import com.example.demo.util.SecondRecyclerAdapter

/**
 * 리스트 아이템 위치에 다이얼로그를 노출하는 테스트
 */
class SecondFragment : BaseFragment<FragmentSecondBinding>(FragmentSecondBinding::inflate),
    SecondRecyclerAdapter.OnItemSelectListener {

    private val secondRecyclerAdapter: SecondRecyclerAdapter by lazy {
        SecondRecyclerAdapter(this)
    }
    private lateinit var recyclerViewItemTouchEvent: RecyclerViewItemTouchEvent
    private val mockItems = (1..100).map { it.toString().plus("테스트 다이얼로그 입니다.") }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initDefaultData()
    }

    private fun initAdapter() {
        binding.rvSample.adapter = secondRecyclerAdapter
        recyclerViewItemTouchEvent =
            RecyclerViewItemTouchEvent(binding.rvSample)
    }

    private fun initDefaultData() {
        secondRecyclerAdapter.replaceItems(mockItems)
    }

    override fun select(text: String) {
        showDialog(text)
    }

    private fun showDialog(text: String) {
        val dialog = CommonDialog(
            text,
            recyclerViewItemTouchEvent.itemViewPositionInfo
        )
        dialog.positiveListener {
            if (dialog.isAdded) {
                dialog.dismiss()
            }
        }
        dialog.negativeListener {
            if (dialog.isAdded) {
                dialog.dismiss()
            }
        }
        dialog.show(childFragmentManager, null)
    }
}