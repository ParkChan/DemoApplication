package com.example.demo.fragment

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.demo.SecondActivity
import com.example.demo.databinding.FragmentStartActivityTestBinding
import timber.log.Timber

/**
 *
 * startActivity() → startActivityForResult() 호출시 버전에 따른 동작 차이
 *
 * api level 31 : singleTop 으로 startActivity() → startActivityForResult()
 * 호출시 SubActivity가 한개만 호출되고 onNewIntent() 수행됨
 *
 * 31 미만에서 동일 액티비티를 싱글탑을 주더라도
 * startActivity() → startActivityForResult() 액티비티 2개 생성됨
 * singleTop이 적용되지 않음
 *
 * [공통] startActivity() → startActivity() 는 singleTop 정상동작
 */
class StartActivityTestFragment : Fragment() {

    private lateinit var binding: FragmentStartActivityTestBinding

    private fun openActivityForResult() {
        val intent = Intent(requireContext(), SecondActivity::class.java)
        startForResult.launch(intent)
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentStartActivityTestBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNextSubActiviy.setOnClickListener {
            val intent = Intent(context, SecondActivity::class.java)
            //SingleTop이 필요한 경우 분기
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            } else {
//                //누갓은 클리어 탑을 주어야 onNewIntent가 호출됨
//                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            }
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                //한번 더 호출 결과는??
                it.postDelayed({
                    Timber.d(">>> 액티비티를 한번 더 호출합니다.")
                    startActivityForResult(intent, 1111)
                }, 500)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("requestCode : $requestCode resultCode : $resultCode")
        if (requestCode == SecondActivity.ACTIVITY_RESULT_CODE) {
            Toast.makeText(requireContext(), "onActivityResult >> $resultCode", Toast.LENGTH_SHORT)
                .show()
        }
    }

}