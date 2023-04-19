package com.example.demo.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.FragmentOneItemAutoScrollBinding
import com.example.demo.databinding.ItemOneBinding
import com.example.demo.ui.fragment.common.ext.setCurrentItemWithDuration
import kotlinx.coroutines.*
import timber.log.Timber


/**
 * 한줄 자동 스크롤 테스트 
 * CancellationException 예외처리에 대한 간략 내용 포함
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

    private val handler = CoroutineExceptionHandler { _, exception ->

        //CancellationException 예외는 Handler에 호출되지 않는다.
        //코루틴 동작 중 취소 상황 시 처리를 해야할 일이 있다면 try-catch블록을 이용해서 예외처리용으로 사용
        /**
         * 보통 Internet 통신 / 파일(File)에 사용할 경우 코루틴 종료 후 자원 반납도 해야하기 때문에
         * try-catch문으로 CancellationException에만 처리를 할 경우 취소시(catch) 자원반납, 종료시 자원반납 2중코드가 됨
         * 그렇기 때문에 try-finally문에서 자원반납을 한번에 처리한다. cancel() 취소 호출에도 finally는 필수 호출되기 때문
         */
        Timber.d("TEST >>> CoroutineExceptionHandler Caught $exception")
    }


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
        autoScrollJob = CoroutineScope(Dispatchers.Main + handler).launch {
            runCatching {
                repeat(AUTO_SCROLL_COUNT) {
                    delay(AUTO_SCROLL_DELAY_TIME)
                    scrollPosition++
                    if(mockItems.size < scrollPosition){
                        scrollPosition = FIRST_ITEM_POSITION
                    }

                    if (scrollPosition == FIRST_ITEM_POSITION) {
                        binding.vpSample.setCurrentItem(scrollPosition, false)
                    } else {
                        binding.vpSample.setCurrentItemWithDuration(
                            scrollPosition,
                            AUTO_SCROLL_ANIM_DURATION,
                            pagePxWidth = binding.vpSample.height
                        )
                    }
                    Timber.d("TEST >>> scrollPosition is >>> $scrollPosition")
                }
            }.onFailure {
                Timber.d("TEST >>> Throwable is ${it.message}")
            }
        }
    }

    private fun cancelAutoScroll() {
        autoScrollJob?.cancel()
    }

    override fun onClickItem(text: String) {
        Toast.makeText(context, "선택한 버튼은 >> $text", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val FIRST_ITEM_POSITION = 0
        private const val AUTO_SCROLL_COUNT = 100_000_000
        private const val AUTO_SCROLL_ANIM_DURATION = 1_500L
        private const val AUTO_SCROLL_DELAY_TIME = 2_000L
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
        val binding = ItemOneBinding.inflate(inflater, parent, false)
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
        private val binding: ItemOneBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.sampleText = text
            binding.executePendingBindings()
        }
    }
}