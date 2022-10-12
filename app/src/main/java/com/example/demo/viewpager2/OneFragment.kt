package com.example.demo.viewpager2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.demo.databinding.FragmentOneBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class OneFragment : Fragment() {

    private lateinit var binding: FragmentOneBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOneBinding.inflate(layoutInflater)
        Timber.d("CHAN >>> lifecycle is onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("CHAN >>> lifecycle is onViewCreated")
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.systemEvent.collect {
                    Timber.d("CHAN >>> systemEvent is $it")
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Timber.d("CHAN >>> lifecycle is onPause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("CHAN >>> lifecycle is onSaveInstanceState")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("CHAN >>> lifecycle is onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("CHAN >>> lifecycle is onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("CHAN >>> lifecycle is onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("CHAN >>> lifecycle is onDetach")
    }

    companion object {
        fun newInstance(): OneFragment = OneFragment()
    }
}
