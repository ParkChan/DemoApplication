package com.example.demo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.databinding.FragmentOneItemAutoScrollBinding
import com.example.demo.databinding.RvOneItemBinding
import com.example.demo.fragment.common.ext.setCurrentItemWithDuration
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*


/**
 * 한줄 자동 스크롤 테스트
 */
class OneItemAutoScrollFragment : Fragment(), OneItemRecyclerAdapter.OnItemSelectListener {

    private lateinit var binding: FragmentOneItemAutoScrollBinding

    private val oneItemRecyclerAdapter: OneItemRecyclerAdapter by lazy {
        OneItemRecyclerAdapter(this)
    }
    private val mockItems: List<String> by lazy {
        (1..5).map { it.toString().plus("테스트 공지 사항입니다.테스트 공지 사항입니다.테스트 공지 사항입니다.") }
    }
    private var autoScrollJob: Job? = null
    private var scrollPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOneItemAutoScrollBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initDefaultData()
        initListener()
    }

    private fun initAdapter() {
        binding.vpSample.adapter = oneItemRecyclerAdapter
        binding.vpSample.isUserInputEnabled = false
    }

    private fun initDefaultData() {
        oneItemRecyclerAdapter.replaceItems(mockItems)
    }

    private fun initListener() {
        binding.btnStart.setOnClickListener {
            startAutoScroll()
        }
        binding.btnCancel.setOnClickListener {
            cancelAutoScroll()
        }
    }

    private fun startAutoScroll() {
        if(autoScrollJob?.isActive == true){
            return
        }
        autoScrollJob = CoroutineScope(Dispatchers.Main).launch {
            runCatching {
                repeat(100_000_000) {
                    delay(2_000)
                    scrollPosition++
                    if(mockItems.size < scrollPosition){
                        scrollPosition = 0
                    }

                    if (scrollPosition == FIRST_ITEM_POSITION) {
                        binding.vpSample.setCurrentItem(scrollPosition, false)
                    } else {
                        binding.vpSample.setCurrentItemWithDuration(scrollPosition, 1_500)
                    }
                    Timber.d("scrollPosition is >>> $scrollPosition")
                }
            }.onFailure {
                Timber.d("Throwable is ${it.message}")
            }
        }
    }

    private fun cancelAutoScroll() {
        autoScrollJob?.cancel()
    }

    override fun onClickItem(text: String) {
        Toast.makeText(context, "선택한 버튼은 >> " + text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val FIRST_ITEM_POSITION = 0
    }

}

internal class OneItemRecyclerAdapter(
    private val selectListener: OnItemSelectListener,
) : RecyclerView.Adapter<OneItemRecyclerAdapter.SampleViewHolder>() {

    private val itemList = mutableListOf<String>()

    interface OnItemSelectListener {
        fun onClickItem(text: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvOneItemBinding.inflate(inflater, parent, false)
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
        private val binding: RvOneItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.sampleText = text
            binding.executePendingBindings()
        }
    }
}