package com.example.demo.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.databinding.ActivityPopupWindowBinding
import com.example.demo.databinding.ItemSpinnerBinding
import com.example.demo.databinding.SpinnerPopupWindowBinding
import com.example.demo.util.DisplayUtils.dpToPx
import com.example.demo.util.doOnGlobalLayout


/**
 * [참고 Blog](https://als2019.tistory.com/46)
 * PopupWindow 생성 및 리스트 아이템 가운데 정렬 표시
 */
class PopupwindowActivity : AppCompatActivity(),
    SecondRecyclerAdapter.OnItemSelectListener {

    private lateinit var binding: ActivityPopupWindowBinding
    private val mockItems: List<String> by lazy {
        (1..100).map { it.toString() }
    }
    private val secondRecyclerAdapter: SecondRecyclerAdapter by lazy {
        SecondRecyclerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPopupWindowBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSpinner.setOnClickListener {

            val button = it as AppCompatButton

            //팝업이 열렸을때 버튼 아이콘 상태 변경
            button.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.expand_less,
                0
            )

            val binding = SpinnerPopupWindowBinding.inflate(layoutInflater)
            binding.rvList.adapter = secondRecyclerAdapter
            secondRecyclerAdapter.replaceItems(mockItems)

            val popupWindow = PopupWindow(binding.root, 200.dpToPx.toInt(), 200.dpToPx.toInt())
            popupWindow.contentView = binding.root
            popupWindow.isOutsideTouchable = true           // 팝업 윈도우 바깥 영역 터치 허용
            popupWindow.isFocusable = true                  // 포커스를 가질 수 있도록 허용
            popupWindow.showAsDropDown(it)                  // DropDown 타입으로 팝업 생성

            binding.rvList.doOnGlobalLayout {
                val layoutManager = binding.rvList.layoutManager as LinearLayoutManager
                layoutManager.scrollToPositionWithOffset(
                    mockItems.count() / 2,
                    layoutManager.height / 2
                )
            }

            //팝업이 닫혔을때 버튼 아이콘 상태 변경
            popupWindow.setOnDismissListener {
                button.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.expand_more,
                    0
                )
            }
        }
    }

    override fun onClickItem(text: String) {
        Toast.makeText(this, "리스트 아이템 선택 $text", Toast.LENGTH_SHORT).show()
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
        val binding = ItemSpinnerBinding.inflate(inflater, parent, false)
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
        private val binding: ItemSpinnerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.sampleText = text
            binding.executePendingBindings()
        }
    }
}


