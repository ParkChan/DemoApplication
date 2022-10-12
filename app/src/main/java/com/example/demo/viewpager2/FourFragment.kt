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
import com.example.demo.databinding.FragmentFourBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class FourFragment : Fragment() {

    private lateinit var binding: FragmentFourBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFourBinding.inflate(layoutInflater)
        Timber.d("CHAN >>> lifecycle is onCreateView Four")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("CHAN >>> lifecycle is onViewCreated Four")
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.testLiveData.collect {

                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Timber.d("CHAN >>> lifecycle is onPause Four")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("CHAN >>> lifecycle is onSaveInstanceState Four")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("CHAN >>> lifecycle is onStop Four")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("CHAN >>> lifecycle is onDestroyView Four")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("CHAN >>> lifecycle is onDestroy Four")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("CHAN >>> lifecycle is onDetach Four")
    }

    companion object {
    }
}
