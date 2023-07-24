package com.marshal.mine.dialog

import NoShakeBtnUtil
import android.view.View
import com.marshal.R
import com.marshal.base_common.basedialog.BaseBottomSheetDialogFragment
import com.marshal.databinding.DialogSheetCommitLayoutBinding

class OpenQuestionSelectDialog(first:String?="",second:String?="",third:String?=""):
    BaseBottomSheetDialogFragment<DialogSheetCommitLayoutBinding>() {

    private var questionDialogClick:QuestionDialogClickListener? = null

    /**
     * data
     */
    private var firstTitle:String? = first
    private var secondTitle:String? = second
    private var thirdTitle:String? = third

    fun setOnDialogClickListener(dialogClickListener: QuestionDialogClickListener) {
        questionDialogClick = dialogClickListener
    }

    override fun getResLayoutId(): Int = R.layout.dialog_sheet_commit_layout

    override fun getResLayoutBinding(): View? {
        binding = DialogSheetCommitLayoutBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        binding?.buttonFirst?.text = firstTitle
        binding?.buttonSecond?.text =secondTitle
        binding?.buttonThird?.text = thirdTitle

        binding?.buttonFirst?.setOnClickListener {
            if(NoShakeBtnUtil.isFastDoubleClick(it)){
                return@setOnClickListener
            }

            questionDialogClick?.onClickFirstItem(it)
            dismiss()
        }
        binding?.buttonSecond?.setOnClickListener {
            if(NoShakeBtnUtil.isFastDoubleClick(it)){
                return@setOnClickListener
            }

            questionDialogClick?.onClickSecondItem(it)
            dismiss()
        }
        binding?.buttonThird?.setOnClickListener {
            if(NoShakeBtnUtil.isFastDoubleClick(it)){
                return@setOnClickListener
            }

            questionDialogClick?.onClickThirdItem(it)
            dismiss()
        }
    }

    interface QuestionDialogClickListener{
        fun onClickFirstItem(view: View)
        fun onClickSecondItem(view: View)
        fun onClickThirdItem(view: View)
    }

}