package com.chan.navigation

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
import com.chan.navigation.databinding.FragmentTwoBinding
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.testLiveData.collect {
                    Timber.d("CHAN >>> onViewCreated Two")
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause: Two")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("onSaveInstanceState: Two")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop: Two")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("onDestroyView: Two")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy: Two")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("onDetach: Two")
    }

    companion object {
        val TAG = TwoFragment::class.java.simpleName.toString()
    }
}
