package com.marshalim.moudle.open.question.dialog

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.marshal.base_common.baseadapter.AdapterItemOnClickListener
import com.marshal.base_common.basedialog.BaseBottomSheetDialogFragment
import com.marshalim.moudle.open.question.R
import com.marshalim.moudle.open.question.adapter.OpenSelectJobAdapter
import com.marshalim.moudle.open.question.databinding.DialogSheetCommitLayoutBinding

class OpenQuestionSelectDialog(titleList:ArrayList<String>) :
    BaseBottomSheetDialogFragment<DialogSheetCommitLayoutBinding>() {

    private var questionDialogClick: QuestionDialogClickListener? = null
    private var jobAdapter: OpenSelectJobAdapter? = null
    private val dataList = titleList


    fun setOnDialogClickListener(dialogClickListener: QuestionDialogClickListener) {
        questionDialogClick = dialogClickListener
    }

    override fun getResLayoutId(): Int = R.layout.dialog_sheet_commit_layout

    override fun getResLayoutBinding(): View? {
        binding = DialogSheetCommitLayoutBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun getPeekHeight(): Int = resources.getDimensionPixelOffset(R.dimen.bottom_170_dp)

    override fun initView() {

        jobAdapter = OpenSelectJobAdapter()
        binding?.recyclerview?.layoutManager =
            LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        binding?.recyclerview?.adapter = jobAdapter

        jobAdapter?.addListAll(dataList)
        jobAdapter?.setAdapterItemOnClickListener(object :AdapterItemOnClickListener<String>{
            override fun onClick(view: View, bean: String) {
                super.onClick(view, bean)
                questionDialogClick?.onClickTitleContent(bean)
                dismiss()
            }
        })

    }

    interface QuestionDialogClickListener {
        fun onClickTitleContent(title: String)
    }

}