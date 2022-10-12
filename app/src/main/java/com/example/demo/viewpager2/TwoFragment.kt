package com.example.demo.viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.demo.databinding.FragmentTwoBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class TwoFragment : Fragment() {

    private lateinit var binding: FragmentTwoBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTwoBinding.inflate(layoutInflater)
        Timber.d("CHAN >>> lifecycle is onCreateView Two")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("CHAN >>> lifecycle is onViewCreated Two")
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.testLiveData.collect {

                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Timber.d("CHAN >>> lifecycle is onPause Two")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("CHAN >>> lifecycle is onSaveInstanceState Two")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("CHAN >>> lifecycle is onStop Two")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("CHAN >>> lifecycle is onDestroyView Two")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("CHAN >>> lifecycle is onDestroy Two")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("CHAN >>> lifecycle is onDetach Two")
    }

    companion object {
    }
}