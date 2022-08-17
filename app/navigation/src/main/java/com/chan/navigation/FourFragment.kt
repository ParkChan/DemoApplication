package com.chan.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.chan.navigation.databinding.FragmentFourBinding
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
//        viewModel.testLiveData.observeEvent(viewLifecycleOwner) {
//            Timber.d("CHAN >>> onViewCreated Four")
//        }
        viewModel.testLiveData.observeEvent(viewLifecycleOwner){
            Timber.d("CHAN >>> onViewCreated Four")
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("CHAN >>>", "onPause: Four")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("CHAN >>>", "onSaveInstanceState: Four")
    }

    override fun onStop() {
        super.onStop()
        Log.d("beokbeok", "onStop: Four")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("beokbeok", "onDestroyView: Four")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("beokbeok", "onDestroy: Four")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("beokbeok", "onDetach: Four")
    }

    companion object {
        val TAG = FourFragment::class.java.simpleName.toString()
    }
}
