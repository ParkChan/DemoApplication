package com.example.demo.ui.fragment.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.example.demo.databinding.DialogPositionBinding
import com.example.demo.ui.util.DisplayUtils.dpToPx
import timber.log.Timber

/**
 * 선택한 뷰홀더의 위치에 따른 다이얼로그
 */
class PositionDialog : DialogFragment() {

    private lateinit var positiveListener: View.OnClickListener
    private lateinit var negativeListener: View.OnClickListener

    private lateinit var binding: DialogPositionBinding

    private val dialogMessage: String by lazy {
        arguments?.getString(BUNDLE_KEY_DIALOG_MESSAGE) ?: ""
    }

    private val viewHolderItemInfo: ViewHolderItemInfo by lazy {
        arguments?.getParcelable(BUNDLE_KEY_VIEW_HOLDER_ITEM_INFO) ?: ViewHolderItemInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogPositionBinding.inflate(inflater, container, false)
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
        val dialogHeight = DIALOG_HEIGHT_DP.dpToPx.toInt()

        Timber.d("viewHolderItemInfo >>> is $viewHolderItemInfo")
        Timber.d("Test >>> dialogHeight >>> is $dialogHeight")
        Timber.d("Test >>> viewHolderItemInfo.itemPosition() >>> ${viewHolderItemInfo.itemPosition()}")
        Timber.d("Test >>> viewHolderItemInfo.itemPositionY() >>> ${viewHolderItemInfo.itemPositionY()}")
        Timber.d("Test >>> viewHolderItemInfo.itemViewHeight() >>> ${viewHolderItemInfo.itemViewHeight()}")

        dialog?.window?.attributes = params?.apply {
            gravity = Gravity.TOP
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = dialogHeight
        }

        if (viewHolderItemInfo.itemPositionY() < 0 ||
            viewHolderItemInfo.itemPositionY() < dialogHeight
        ) {
            viewDynamicSettings()
            params?.apply {
                y = DIALOG_TEST_TOP_HEIGHT.dpToPx.toInt().plus(viewHolderItemInfo.itemPositionY())
            }
        } else {
            params?.apply {
                y = DIALOG_TEST_TOP_HEIGHT.dpToPx.toInt().plus(viewHolderItemInfo.itemPositionY())
                    .minus(dialogHeight.minus(viewHolderItemInfo.itemViewHeight()))
            }
        }
    }

    private fun viewDynamicSettings() {
        val buttonGroupLayoutParams: ConstraintLayout.LayoutParams =
            binding.clButtonGroup.layoutParams as ConstraintLayout.LayoutParams
        buttonGroupLayoutParams.apply {
            topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            bottomToBottom = DEFAULT_LAYOUT_ID
        }
        binding.clButtonGroup.layoutParams = buttonGroupLayoutParams

        val titleLayoutParams: ConstraintLayout.LayoutParams =
            binding.tvTitle.layoutParams as ConstraintLayout.LayoutParams
        titleLayoutParams.apply {
            bottomToTop = DEFAULT_LAYOUT_ID
            topToBottom = binding.clButtonGroup.id
        }
        binding.tvTitle.layoutParams = titleLayoutParams
    }

    companion object {
        const val BUNDLE_KEY_DIALOG_MESSAGE = "dialog_message"
        const val BUNDLE_KEY_VIEW_HOLDER_ITEM_INFO = "view_holder_item_info"
        const val BUNDLE_KEY_RECYCLER_VIEW_Y_AXIS = "bundle_key_recycler_view_y_axis"
        private const val DEFAULT_LAYOUT_ID = -1
        private const val DIALOG_TEST_TOP_HEIGHT = 120
        private const val DIALOG_HEIGHT_DP = 250
    }
}