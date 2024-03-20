package com.example.demo.ui.fragment.viewpager2

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.databinding.FragmentTwoBinding
import com.example.demo.databinding.ItemCarLeftBinding
import com.example.demo.ui.fragment.RecyclerLeftHoldScrollTestFragment
import com.example.demo.ui.viewmodel.ViewPagerViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class TwoFragment : Fragment() {

    private lateinit var binding: FragmentTwoBinding
    private val viewModel: ViewPagerViewModel by activityViewModels()
    private val adapter: RecyclerAdapter by lazy {
        RecyclerAdapter()
    }

    private val mockItems: List<RecyclerLeftHoldScrollTestFragment.Car> by lazy {
        (1..3000).map {
            RecyclerLeftHoldScrollTestFragment.Car(
                it.toString().plus("번 차량"),""
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTwoBinding.inflate(layoutInflater)
        Timber.d("lifecycle is onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("lifecycle is onViewCreated")

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.systemEvent.collect {
                    Timber.d("systemEvent is $it")
                }
            }
        }
        initAdapter()
    }

    private fun initAdapter() {
        binding.rvList.adapter = adapter
        adapter.replaceItems(mockItems)

    }

    class RecyclerAdapter :
        RecyclerView.Adapter<RecyclerAdapter.SampleViewHolder>() {

        private val itemList = mutableListOf<RecyclerLeftHoldScrollTestFragment.Car>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCarLeftBinding.inflate(inflater, parent, false)
            return SampleViewHolder(binding)
        }

        override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
            holder.bind(position, itemList[position])
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        @SuppressLint("NotifyDataSetChanged")
        internal fun replaceItems(item: List<RecyclerLeftHoldScrollTestFragment.Car>) {
            itemList.run {
                clear()
                addAll(item)
                notifyDataSetChanged()
            }
        }

        class SampleViewHolder(
            private val binding: ItemCarLeftBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(position: Int, car: RecyclerLeftHoldScrollTestFragment.Car) {

                binding.car = car
                binding.tvTest.maxLines = 2

                val carName = car.name + "테스트 입니다  "

                val spannableString = SpannableString("$carName  ")

                val context = binding.tvTest.context

                ContextCompat.getDrawable(context, R.drawable.ic_text_1)?.run {
                    setBounds(0, 0, 50, 50)
                    spannableString.setSpan(
                        ImageSpan(this, ImageSpan.ALIGN_BASELINE),
                        carName.length,
                        carName.length + 1,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                }

                ContextCompat.getDrawable(context, R.drawable.ic_text_2)?.run {
                    setBounds(0, 0, 50, 50)
                    spannableString.setSpan(
                        ImageSpan(this, ImageSpan.ALIGN_BASELINE),
                        carName.length + 1,
                        carName.length + 2,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                }

                binding.tvTest.text = spannableString
                binding.executePendingBindings()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Timber.d("lifecycle is onPause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("lifecycle is onSaveInstanceState")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("lifecycle is onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("lifecycle is onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("lifecycle is onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("lifecycle is onDetach")
    }

    companion object {
        fun newInstance(): TwoFragment = TwoFragment()
    }
}
