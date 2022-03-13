package com.example.demo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.FragmentDialogPositionTestBinding
import com.example.demo.databinding.RvTestItemBinding
import com.example.demo.dialog.CommonDialog
import com.example.demo.dialog.CommonDialog.Companion.BUNDLE_KEY_DIALOG_MESSAGE
import com.example.demo.dialog.CommonDialog.Companion.BUNDLE_KEY_RECYCLER_VIEW_Y_AXIS
import com.example.demo.dialog.CommonDialog.Companion.BUNDLE_KEY_VIEW_HOLDER_ITEM_INFO
import com.example.demo.fragment.base.BaseFragment
import com.example.demo.util.RecyclerViewItemTouchEvent

/**
 * 리스트 아이템 위치에 다이얼로그를 노출하는 테스트
 */
class DialogPositionTestFragment :
    BaseFragment<FragmentDialogPositionTestBinding>(FragmentDialogPositionTestBinding::inflate),
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
        recyclerViewItemTouchEvent = RecyclerViewItemTouchEvent(binding.rvSample)
        binding.rvSample.adapter = secondRecyclerAdapter
    }

    private fun initDefaultData() {
        secondRecyclerAdapter.replaceItems(mockItems)
    }

    override fun select(text: String) {
        showDialog(text)
    }

    private fun showDialog(
        text: String
    ) {
        val dialog = CommonDialog().apply {
            arguments = bundleOf(
                BUNDLE_KEY_DIALOG_MESSAGE to text,
                BUNDLE_KEY_RECYCLER_VIEW_Y_AXIS to recyclerViewYAxis(binding.rvSample),
                BUNDLE_KEY_VIEW_HOLDER_ITEM_INFO to recyclerViewItemTouchEvent.itemViewPositionInfo
            )
        }

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

    private fun recyclerViewYAxis(recycler: RecyclerView): Int {
        val recyclerViewPos = IntArray(2) { 1 }
        recycler.getLocationInWindow(recyclerViewPos)
        return recyclerViewPos[1]
    }
}

class SecondRecyclerAdapter(
    private val selectListener: OnItemSelectListener
) : RecyclerView.Adapter<SecondRecyclerAdapter.SampleViewHolder>() {

    private val itemList = mutableListOf<String>()

    interface OnItemSelectListener {
        fun select(text: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvTestItemBinding.inflate(inflater, parent, false)
        binding.listener = selectListener
        return SampleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceItems(item: List<String>) {
        itemList.run {
            clear()
            addAll(item)
            notifyDataSetChanged()
        }
    }

    class SampleViewHolder(
        private val binding: RvTestItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.sampleText = text
            binding.executePendingBindings()
        }
    }
}