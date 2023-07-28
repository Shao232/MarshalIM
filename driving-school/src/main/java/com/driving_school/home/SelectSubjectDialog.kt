package com.driving_school.home

import android.view.View
import com.driving_school.R
import com.driving_school.databinding.DialogSelectSubjectBinding
import com.marshal.base_common.basedialog.BaseBottomSheetDialogFragment

class SelectSubjectDialog : BaseBottomSheetDialogFragment<DialogSelectSubjectBinding>() {

    var onSelectSubjectClick:SelectSubjectClick? = null

    private var drivingSubjectOneSize:Int = 0
    private var drivingSubjectFourSize:Int = 0

    fun setOnSelectSubjectItemClick(selectSubjectClick:SelectSubjectClick){
        this.onSelectSubjectClick = selectSubjectClick
    }

    override fun getResLayoutId(): Int = R.layout.dialog_select_subject

    override fun getResLayoutBinding(): View? {
        binding = DialogSelectSubjectBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        binding?.tvSubjectOneSelectItem?.text = "c1 科目1(${drivingSubjectOneSize}道)"
        binding?.tvSubjectFourSelectItem?.text = "c1 科目4(${drivingSubjectFourSize}道)"

        binding?.tvSubjectOneSelectItem?.setOnClickListener {
            onSelectSubjectClick?.onSelectSubjectClick(1)
            dismiss()
        }

        binding?.tvSubjectFourSelectItem?.setOnClickListener {
            onSelectSubjectClick?.onSelectSubjectClick(4)
            dismiss()
        }
    }

    fun setSubjectOneContent(size:Int):SelectSubjectDialog{
        this.drivingSubjectOneSize = size
        return this
    }

    fun setSubjectFourContent(size:Int):SelectSubjectDialog{
        this.drivingSubjectFourSize = size
        return this
    }

    interface SelectSubjectClick{
        fun onSelectSubjectClick(subjectType:Int)
    }
}