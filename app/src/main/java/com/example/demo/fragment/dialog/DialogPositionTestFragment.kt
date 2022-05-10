package com.example.demo.fragment.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.databinding.FragmentDialogPositionTestBinding
import com.example.demo.databinding.RvTestItemBinding
import com.example.demo.fragment.dialog.PositionDialog.Companion.BUNDLE_KEY_DIALOG_MESSAGE
import com.example.demo.fragment.dialog.PositionDialog.Companion.BUNDLE_KEY_RECYCLER_VIEW_Y_AXIS
import com.example.demo.fragment.dialog.PositionDialog.Companion.BUNDLE_KEY_VIEW_HOLDER_ITEM_INFO
import com.example.demo.fragment.base.BaseFragment


/**
 * 리스트 아이템 위치에 다이얼로그를 노출하는 테스트
 */
internal class DialogPositionTestFragment :
    BaseFragment<FragmentDialogPositionTestBinding>(FragmentDialogPositionTestBinding::inflate),
    SecondRecyclerAdapter.OnItemSelectListener {

    private val recyclerViewYAxis: Int by lazy {
        getRecyclerViewYAxis(binding.rvSample)
    }

    private val secondRecyclerAdapter: SecondRecyclerAdapter by lazy {
        SecondRecyclerAdapter(this)
    }

    private lateinit var recyclerViewItemTouchEvent: RecyclerViewItemTouchEvent

    private val mockItems: List<String> by lazy {
        (1..100).map { it.toString().plus(getString(R.string.dialog_position_test_content)) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initDefaultData()
    }

    private fun initAdapter() {
        binding.rvSample.adapter = secondRecyclerAdapter
        recyclerViewItemTouchEvent = RecyclerViewItemTouchEvent(binding.rvSample)
    }

    private fun initDefaultData() {
        secondRecyclerAdapter.replaceItems(mockItems)
    }

    override fun onClickItem(text: String) {
        if (::recyclerViewItemTouchEvent.isInitialized) {
            showDialog(text, recyclerViewItemTouchEvent.viewHolderItemInfo)
        }
    }

    private fun showDialog(
        text: String,
        viewHolderItemInfo: ViewHolderItemInfo,
    ) {
        val dialog = PositionDialog().apply {
            arguments = bundleOf(
                BUNDLE_KEY_DIALOG_MESSAGE to text,
                BUNDLE_KEY_RECYCLER_VIEW_Y_AXIS to recyclerViewYAxis,
                BUNDLE_KEY_VIEW_HOLDER_ITEM_INFO to viewHolderItemInfo
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

    private fun getRecyclerViewYAxis(recycler: RecyclerView): Int {
        val recyclerViewPos = IntArray(2) { 1 }
        recycler.getLocationInWindow(recyclerViewPos)
        return recyclerViewPos[1]
    }
}

internal class SecondRecyclerAdapter(
    private val selectListener: OnItemSelectListener,
) : RecyclerView.Adapter<SecondRecyclerAdapter.SampleViewHolder>() {

    private val itemList = mutableListOf<String>()

    interface OnItemSelectListener {
        fun onClickItem(text: String)
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
    internal fun replaceItems(item: List<String>) {
        itemList.run {
            clear()
            addAll(item)
            notifyDataSetChanged()
        }
    }

    internal class SampleViewHolder(
        private val binding: RvTestItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.sampleText = text
            binding.executePendingBindings()
        }
    }
}