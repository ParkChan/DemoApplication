package com.example.demo.fragment.theme

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.demo.databinding.DialogThemeBinding
import com.example.demo.util.StatusBarUtil
import com.example.demo.util.SystemBarColorType
import com.example.demo.util.ThemeType
import com.example.demo.util.ThemeUtil
import timber.log.Timber

class ThemeDialog : DialogFragment() {

    private lateinit var binding: DialogThemeBinding
    private lateinit var positiveListener: View.OnClickListener
    private lateinit var negativeListener: View.OnClickListener
    lateinit var name: String
    private var themeType = LIGHT_THEME

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("lifecycle test >>> ThemeDialog lifeCycle is onAttach")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("lifecycle test >>> ThemeDialog lifeCycle is onCreateView")
        binding = DialogThemeBinding.inflate(inflater, container, false)
        isCancelable = true
        requireDialog().window?.apply {
//            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val params: WindowManager.LayoutParams? = dialog?.window?.attributes
        dialog?.window?.attributes = params?.apply {
            gravity = Gravity.CENTER
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
        }

        binding.btnConfirm.setOnClickListener { dismiss() }

        binding.btnLight.setOnClickListener {
            //해당 설정을 하게되면 두번 깜빡이는 이슈가 발생되는것을 확인
            //onSaveInstanceState에서 설정하면 정상동작 됨
//            StatusBarUtil.setStatusBarColorAndNavigationColor(requireActivity(),
//                SystemBarColorType.DEFAULT_STATUS_BAR)

            themeType = LIGHT_THEME
            ThemeUtil.applyTheme(ThemeType.LIGHT_MODE)
        }
        binding.btnDark.setOnClickListener {
            //해당 설정을 하게되면 두번 깜빡이는 이슈가 발생되는것을 확인
            //onSaveInstanceState에서 설정하면 정상동작 됨
//            StatusBarUtil.setStatusBarColorAndNavigationColor(requireActivity(),
//                SystemBarColorType.DARK_STATUS_BAR)

            themeType = DARK_THEME
            ThemeUtil.applyTheme(ThemeType.DARK_MODE)
        }
        binding.btnSystem.setOnClickListener {
            ThemeUtil.applyTheme(ThemeType.SYSTEM_MODE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("lifecycle test >>> ThemeDialog lifeCycle is onSaveInstanceState")
        outState.putString(BUNDLE_KEY_THEME_TYPE, themeType)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        themeType = savedInstanceState?.getString(BUNDLE_KEY_THEME_TYPE) ?: ""
        Timber.d("lifecycle test >>> ThemeDialog lifeCycle is onViewStateRestored themeType is $themeType")

        if (themeType.isEmpty().not()) {
            if (themeType == DARK_THEME) {
                StatusBarUtil.setStatusBarColorAndNavigationColor(
                    requireActivity(),
                    SystemBarColorType.DARK_STATUS_BAR
                )
            } else {
                StatusBarUtil.setStatusBarColorAndNavigationColor(
                    requireActivity(),
                    SystemBarColorType.DEFAULT_STATUS_BAR
                )
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("lifecycle test >>> ThemeDialog lifeCycle is onDetach")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("lifecycle test >>> ThemeDialog lifeCycle is onDestroy")
    }

    fun positiveListener(listener: View.OnClickListener) {
        positiveListener = listener
    }

    fun negativeListener(listener: View.OnClickListener) {
        negativeListener = listener
    }

    companion object {
        private const val BUNDLE_KEY_THEME_TYPE = "BUNDLE_KEY_THEME_TYPE"
        private const val LIGHT_THEME = "LIGHT_THEME"
        private const val DARK_THEME = "DARK_THEME"

    }
}