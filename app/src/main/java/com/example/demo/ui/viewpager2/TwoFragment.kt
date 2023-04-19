package com.example.demo.ui.viewpager2

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
