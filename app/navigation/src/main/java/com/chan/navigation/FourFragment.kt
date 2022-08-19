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
import com.chan.navigation.databinding.FragmentFourBinding
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.testLiveData.collect {
                    Timber.d("onViewCreated Four")
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause: Four")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("onSaveInstanceState: Four")
    }

    override fun onStop() {
        super.onStop()
        Timber.d( "onStop: Four")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d( "onDestroyView: Four")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy: Four")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("onDetach: Four")
    }

    companion object {
        val TAG = FourFragment::class.java.simpleName.toString()
    }
}
