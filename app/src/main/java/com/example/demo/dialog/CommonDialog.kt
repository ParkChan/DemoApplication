package com.example.demo.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.example.demo.databinding.DialogCommonBinding
import com.example.demo.util.dp
import com.example.demo.util.px
import com.example.demo.util.statusBarHeight
import timber.log.Timber

/**
 * 선택한 뷰홀더의 위치에 따른 다이얼로그
 */
class CommonDialog : DialogFragment() {

    private lateinit var positiveListener: View.OnClickListener
    private lateinit var negativeListener: View.OnClickListener

    private lateinit var binding: DialogCommonBinding

    private val dialogMessage: String by lazy {
        arguments?.getString(BUNDLE_KEY_DIALOG_MESSAGE) ?: ""
    }
    private val recyYAxis: Int by lazy {
        arguments?.getInt(BUNDLE_KEY_RECYCLER_VIEW_Y_AXIS) ?: 0
    }
    private val viewHolderItemInfo: ViewHolderItemInfo by lazy {
        arguments?.getParcelable(BUNDLE_KEY_VIEW_HOLDER_ITEM_INFO) ?: ViewHolderItemInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCommonBinding.inflate(inflater, container, false)
        isCancelable = true
        requireDialog().window?.apply {
//            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = dialogMessage
        binding.btnPositive.setOnClickListener(positiveListener)
        binding.btnNegative.setOnClickListener(negativeListener)
        initDialogWindow()
    }

    fun positiveListener(listener: View.OnClickListener) {
        positiveListener = listener
    }

    fun negativeListener(listener: View.OnClickListener) {
        negativeListener = listener
    }

    private fun initDialogWindow() {
        val params: WindowManager.LayoutParams? = dialog?.window?.attributes
        val dialogHeight = DIALOG_HEIGHT_DP.px
        Timber.d("viewHolderItemInfo >>> is $viewHolderItemInfo")
        Timber.d("Test >>> dialogHeight >>> is $dialogHeight")
        Timber.d("Test >>> recyclerViewYAxis >>> is ${recyYAxis.dp}")
        Timber.d("Test >>> statusBarHeight >>> is ${statusBarHeight().dp}")
        dialog?.window?.attributes =
            if (viewHolderItemInfo.itemPositionY() <
                viewHolderItemInfo.itemViewHeight()
                    .plus(recyYAxis)
                    .minus(statusBarHeight())
            ) {
                viewDynamicSettings()
                params?.apply {
                    gravity = Gravity.TOP
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    height = dialogHeight
                    y = viewHolderItemInfo.itemPositionY()
                        .plus(recyYAxis - statusBarHeight())
                }
            } else {
                params?.apply {
                    gravity = Gravity.TOP
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    height = dialogHeight
                    y = viewHolderItemInfo.itemPositionY()
                        .minus(dialogHeight)
                        .plus(viewHolderItemInfo.itemViewHeight())
                        .plus(recyYAxis - statusBarHeight())
                }
            }
    }

    private fun viewDynamicSettings() {
        val buttonGroupLayoutParams: ConstraintLayout.LayoutParams =
            binding.clButtonGroup.layoutParams as ConstraintLayout.LayoutParams
        buttonGroupLayoutParams.apply {
            topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            bottomToBottom = -1
        }
        binding.clButtonGroup.layoutParams = buttonGroupLayoutParams

        val titleLayoutParams: ConstraintLayout.LayoutParams =
            binding.tvTitle.layoutParams as ConstraintLayout.LayoutParams
        titleLayoutParams.apply {
            bottomToTop = -1
            topToBottom = binding.clButtonGroup.id
        }
        binding.tvTitle.layoutParams = titleLayoutParams
    }

    companion object {
        const val BUNDLE_KEY_DIALOG_MESSAGE = "dialog_message"
        const val BUNDLE_KEY_VIEW_HOLDER_ITEM_INFO = "view_holder_item_info"
        const val BUNDLE_KEY_RECYCLER_VIEW_Y_AXIS = "recycler_view_y_axis"
        private const val DIALOG_HEIGHT_DP = 300
    }
}