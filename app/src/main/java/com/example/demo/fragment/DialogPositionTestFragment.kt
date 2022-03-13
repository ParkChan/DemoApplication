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
import com.example.demo.dialog.CommonDialog.Companion.BUNDLE_KEY_VIEW_HOLDER_ITEM_INFO
import com.example.demo.dialog.ViewHolderItemInfo
import com.example.demo.fragment.base.BaseFragment

/**
 * 리스트 아이템 위치에 다이얼로그를 노출하는 테스트
 */
class DialogPositionTestFragment :
    BaseFragment<FragmentDialogPositionTestBinding>(FragmentDialogPositionTestBinding::inflate),
    SecondRecyclerAdapter.OnItemSelectListener {

    private val secondRecyclerAdapter: SecondRecyclerAdapter by lazy {
        SecondRecyclerAdapter(this)
    }
    private val mockItems = (1..100).map { it.toString().plus("테스트 다이얼로그 입니다.") }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initDefaultData()
    }

    private fun initAdapter() {
        binding.rvSample.adapter = secondRecyclerAdapter
    }

    private fun initDefaultData() {
        secondRecyclerAdapter.replaceItems(mockItems)
    }

    override fun select(text: String, viewHolderItemInfo: ViewHolderItemInfo) {
        showDialog(text, viewHolderItemInfo)
    }

    private fun showDialog(
        text: String,
        viewHolderItemInfo: ViewHolderItemInfo,
    ) {
        val dialog = CommonDialog().apply {
            arguments = bundleOf(
                BUNDLE_KEY_DIALOG_MESSAGE to text,
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
}

class SecondRecyclerAdapter(
    private val selectListener: OnItemSelectListener
) : RecyclerView.Adapter<SecondRecyclerAdapter.SampleViewHolder>() {

    private val itemList = mutableListOf<String>()

    interface OnItemSelectListener {
        fun select(text: String, viewHolderItemInfo: ViewHolderItemInfo)
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
            itemView.post {
                binding.viewHolderItemInfo = ViewHolderItemInfo(
                    recyclerViewYAxis(itemView.parent as RecyclerView),
                    itemView.y,
                    itemView.width,
                    itemView.height
                )
            }
            binding.executePendingBindings()
        }

        private fun recyclerViewYAxis(recycler: RecyclerView): Int {
            val recyclerViewPos = IntArray(2) { 1 }
            recycler.getLocationInWindow(recyclerViewPos)
            return recyclerViewPos[1]
        }
    }
}