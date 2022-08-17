package com.chan.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.chan.navigation.databinding.FragmentOneBinding
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.testLiveData.observeEvent(viewLifecycleOwner){
//            Timber.d("onViewCreated One")
//        }
        viewModel.testLiveData.observeEvent(viewLifecycleOwner){
            Timber.d("CHAN >>> onViewCreated One")
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("CHAN >>>", "onPause: One")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("CHAN >>>", "onSaveInstanceState: One")
    }

    override fun onStop() {
        super.onStop()
        Log.d("CHAN >>>", "onStop: One")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("CHAN >>>", "onDestroyView: One")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CHAN >>>", "onDestroy: One")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("CHAN >>>", "onDetach: One")
    }

    companion object {
        val TAG = OneFragment::class.java.simpleName.toString()
    }
}
