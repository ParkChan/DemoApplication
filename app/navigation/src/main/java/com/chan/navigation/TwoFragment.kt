package com.chan.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.chan.navigation.databinding.FragmentTwoBinding
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
//        viewModel.testLiveData.observeEvent(viewLifecycleOwner) {
//            Timber.d("onViewCreated Two")
//        }
        viewModel.testLiveData.observeEvent(viewLifecycleOwner){
            Timber.d("CHAN >>> onViewCreated Two")
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("CHAN >>>", "onPause: Two")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("CHAN >>>", "onSaveInstanceState: Two")
    }

    override fun onStop() {
        super.onStop()
        Log.d("CHAN >>>", "onStop: Two")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("CHAN >>>", "onDestroyView: Two")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CHAN >>>", "onDestroy: Two")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("CHAN >>>", "onDetach: Two")
    }

    companion object {
        val TAG = TwoFragment::class.java.simpleName.toString()
    }
}
