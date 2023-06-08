package com.marshal.mine.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.marshal.R
import com.marshal.databinding.DialogSheetCommitLayoutBinding

class OpenQuestionSelectDialog: BottomSheetDialogFragment() {

    private var rootView:View? = null
    private var binding:DialogSheetCommitLayoutBinding? = null

    private var questionDialogClick:QuestionDialogClickListener? = null

    fun setOnDialogClickListener(dialogClickListener: QuestionDialogClickListener) {
        questionDialogClick = dialogClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.dialog_sheet_commit_layout,container)
        binding = DialogSheetCommitLayoutBinding.inflate(layoutInflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.buttonFirst?.text = "单选题"
        binding?.buttonSecond?.text = "多选题"
        binding?.buttonThird?.text = "判断题"

        binding?.buttonFirst?.setOnClickListener {
            questionDialogClick?.onClickFirstItem(it)
            dismiss()
        }
        binding?.buttonSecond?.setOnClickListener {
            questionDialogClick?.onClickSecondItem(it)
            dismiss()
        }
        binding?.buttonThird?.setOnClickListener {
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