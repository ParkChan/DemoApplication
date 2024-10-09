package com.example.demo.ui.activity

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
import timber.log.Timber

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
            holder.bind(itemList[position], position)
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

            fun bind(message: String, position: Int) {
                //binding.sampleText = text
                binding.tvNumber.run {
                    suffix = "suffix"
                    text = sampleText[(0..1).random()]
                }
                binding.tvNumber.post {
                    Timber.d("CHAN >>> position $position suffix 는 다음줄로 출력 되는가? ${binding.tvNumber.isSuffixNextLine}")
                }
                binding.executePendingBindings()
            }
        }
        companion object {
            private val sampleText = arrayOf(
                "1.문자열이긴경우를 테스트 합니다." +
                        "문자열이긴경우를 테스트 합니다." +
                        "문자열이긴경우를 테스트 합니다." +
                        "문자열이긴경우를 테스트 합니다.",
                "2.문자열이긴경우를 테스트 합니다." +
                        "문자열이긴경우를 테스트 합니다." +
                        "문자열이긴경우를 테스트 합니다." +
                        "문자열이긴경우를 테스트 합니다." +
                        "문자열이긴경우를 테스트 합니다." +
                        "문자열이긴경우를 테스트 합니다. 문자열이긴경우"
            )
        }
    }

    class ItemTouchHelperCallback(private val listener: ItemTouchHelperListener) : ItemTouchHelper.Callback() {

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            return makeMovementFlags(dragFlags, swipeFlags) // swipeFlags 대신 0 으로 설정시 스와이프 처리X
        }


        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return listener.onItemMove(viewHolder.bindingAdapterPosition, target.bindingAdapterPosition)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            listener.onItemSwipe(viewHolder.bindingAdapterPosition)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            listener.onDropDown()
        }
    }
    interface ItemTouchHelperListener {
        fun onItemMove(startPosition: Int, endPosition: Int): Boolean
        fun onItemSwipe(position: Int)
        fun onDropDown()
    }
}

