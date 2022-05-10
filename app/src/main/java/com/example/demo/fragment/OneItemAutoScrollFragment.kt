package com.example.demo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.databinding.FragmentOneItemAutoScrollBinding
import com.example.demo.databinding.RvOneItemBinding
import com.example.demo.fragment.common.ext.setCurrentItemWithDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    private var timerTask: Timer? = null
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
        timerTask?.cancel()
        timerTask = kotlin.concurrent.timer(period = 5000) {

            CoroutineScope(Dispatchers.Main).launch {

                if (scrollPosition == FIRST_ITEM_POSITION) {
                    binding.vpSample.setCurrentItem(scrollPosition, true)
                } else {
                    binding.vpSample.setCurrentItemWithDuration(scrollPosition, 2500)
                }

                if (scrollPosition >= mockItems.size - 1) {
                    scrollPosition = FIRST_ITEM_POSITION
                } else {
                    scrollPosition++
                }
                Timber.d("scrollPosition is >>> $scrollPosition")
            }
        }
    }

    private fun cancelAutoScroll() {
        timerTask?.cancel()
    }

    override fun onClickItem(text: String) {

    }

    companion object {
        private const val FIRST_ITEM_POSITION = 0
    }

}

internal class OneItemRecyclerAdapter(
    private val selectListener: OnItemSelectListener,
) : RecyclerView.Adapter<OneItemRecyclerAdapter.SampleViewHolder>() {

    private val itemList = mutableListOf<String>()
    private var isAnim = false

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
//        val animation = AnimationUtils.loadAnimation(
//            holder.itemView.context,
//            R.anim.up_from_bottom
//        )
//        holder.itemView.startAnimation(animation)
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