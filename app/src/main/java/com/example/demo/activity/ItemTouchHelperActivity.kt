package com.example.demo.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.ActivityItemTouchHelperBinding
import com.example.demo.databinding.ItemTouchBinding

/**
 * 리스트 아이템을 터치하여 정렬하는 예제
 * 참조 1 : https://github.com/dhtmaks2540/ItemTouchHelperExample
 * 참조 2 : https://github.com/iPaulPro/Android-ItemTouchHelper-Demo
 */
class ItemTouchHelperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemTouchHelperBinding
    private lateinit var adapter: SecondRecyclerAdapter
    private val mockItems: List<String> by lazy {
        (1..100).map { it.toString() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemTouchHelperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
    }

    private fun initAdapter() {

        adapter = SecondRecyclerAdapter()
        if (::adapter.isInitialized) {
            binding.rvSample.adapter = adapter
            adapter.replaceItems(mockItems)

            val itemTouchHelperCallback = ItemTouchHelperCallback(adapter)
            val helper = ItemTouchHelper(itemTouchHelperCallback)
            helper.attachToRecyclerView(binding.rvSample)

            adapter.setItemTouchHelper(helper)
        }

    }

    private class SecondRecyclerAdapter :
        RecyclerView.Adapter<SecondRecyclerAdapter.SampleViewHolder>(), ItemTouchHelperListener {

        private val itemList = mutableListOf<String>()
        private lateinit var touchHelper: ItemTouchHelper

        @SuppressLint("ClickableViewAccessibility")
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemTouchBinding.inflate(inflater, parent, false)

            val holder = SampleViewHolder(binding)

            binding.tvDrag.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        touchHelper.startDrag(holder)
                        binding.root.isEnabled = false
                    }
                }
                false
            }
            return holder
        }

        override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
            holder.bind(itemList[position])
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun onItemMove(startPosition: Int, endPosition: Int): Boolean {
            val name = itemList[startPosition]

            itemList.removeAt(startPosition)
            itemList.add(endPosition, name)

            notifyItemMoved(startPosition, endPosition)
            return true
        }

        override fun onItemSwipe(position: Int) {
            itemList.removeAt(position)
            notifyItemRemoved(position)
        }

        override fun onDropDown() {

        }

        @SuppressLint("NotifyDataSetChanged")
        fun replaceItems(item: List<String>) {
            itemList.run {
                clear()
                addAll(item)
                notifyDataSetChanged()
            }
        }

        fun setItemTouchHelper(helper: ItemTouchHelper) {
            touchHelper = helper
        }

        class SampleViewHolder(
            private val binding: ItemTouchBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(text: String) {
                binding.sampleText = text
                binding.executePendingBindings()
            }
        }
    }
}