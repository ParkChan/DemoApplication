package com.example.demo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.FragmentRecyclerLeftHoldScrollBinding
import com.example.demo.databinding.ItemCarLeftBinding
import com.example.demo.databinding.ItemCarRightBinding

/**
 *  RecyclerView 2개를 왼쪽 | 오른쪽 으로 배치
 *  상하 스크롤은은 2개의 RecyclerView 가 동시에 동작
 *  오른쪽 페이지는 좌우로 스크롤
 */
class RecyclerLeftHoldScrollTestFragment : Fragment() {

    private lateinit var binding: FragmentRecyclerLeftHoldScrollBinding
    private val leftRecyclerAdapter: LeftRecyclerAdapter by lazy {
        LeftRecyclerAdapter()
    }
    private val rightRecyclerAdapter: RightRecyclerAdapter by lazy {
        RightRecyclerAdapter()
    }
    private val mockItems: List<Car> by lazy {
        (1..3000).map {
            Car(
                it.toString().plus("번 차량"),
                it.toString().plus("번 차량 부품 테스트에 대한 상세설명 입니다. 테스트 문구 테스트 문구  테스트 문구 테스트 문구 테스트 문구")
            )
        }
    }

    data class Car(val name: String?, val description: String?)

    private val onLeftScrollListener: RecyclerView.OnScrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.rvRight.removeOnScrollListener(onRightScrollListener)
                binding.rvRight.scrollBy(dx, dy)
                binding.rvRight.addOnScrollListener(onRightScrollListener)
            }
        }
    }

    private val onRightScrollListener: RecyclerView.OnScrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.rvLeft.removeOnScrollListener(onLeftScrollListener)
                binding.rvLeft.scrollBy(dx, dy)
                binding.rvLeft.addOnScrollListener(onLeftScrollListener)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecyclerLeftHoldScrollBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initListener()

    }

    private fun initAdapter() {
        binding.rvLeft.adapter = leftRecyclerAdapter
        leftRecyclerAdapter.replaceItems(mockItems)

        binding.rvRight.adapter = rightRecyclerAdapter
        rightRecyclerAdapter.replaceItems(mockItems)
    }

    private fun initListener() {
        binding.rvLeft.addOnScrollListener(onLeftScrollListener)
        binding.rvRight.addOnScrollListener(onRightScrollListener)
    }

    internal class LeftRecyclerAdapter :
        RecyclerView.Adapter<LeftRecyclerAdapter.SampleViewHolder>() {

        private val itemList = mutableListOf<Car>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCarLeftBinding.inflate(inflater, parent, false)
            return SampleViewHolder(binding)
        }

        override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
            holder.bind(itemList[position])
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        @SuppressLint("NotifyDataSetChanged")
        internal fun replaceItems(item: List<Car>) {
            itemList.run {
                clear()
                addAll(item)
                notifyDataSetChanged()
            }
        }

        internal class SampleViewHolder(
            private val binding: ItemCarLeftBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(car: Car) {
                binding.car = car
                binding.executePendingBindings()
            }
        }
    }

    internal class RightRecyclerAdapter :
        RecyclerView.Adapter<RightRecyclerAdapter.SampleViewHolder>() {

        private val itemList = mutableListOf<Car>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCarRightBinding.inflate(inflater, parent, false)
            return SampleViewHolder(binding)
        }

        override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
            holder.bind(itemList[position])
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        @SuppressLint("NotifyDataSetChanged")
        internal fun replaceItems(item: List<Car>) {
            itemList.run {
                clear()
                addAll(item)
                notifyDataSetChanged()
            }
        }

        internal class SampleViewHolder(
            private val binding: ItemCarRightBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(car: Car) {
                binding.car = car
                binding.executePendingBindings()
            }
        }
    }
}