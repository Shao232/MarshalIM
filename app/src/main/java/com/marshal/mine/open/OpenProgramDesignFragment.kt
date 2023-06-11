package com.marshal.mine.open

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.marshal.mine.adapter.OpenQuestionProgramDesignAdapter
import com.marshal.sharedata.CommitShareData


/**
 * 程序设计
 */
class OpenProgramDesignFragment : OpenQuestionBaseFragment()  {

    private var adapter= OpenQuestionProgramDesignAdapter()


    override fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>? {
        return adapter as?  RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun viewCreate() {
        super.viewCreate()
        adapter.addListAll(CommitShareData.programSingleQuestions)
    }

    override fun onResume() {
        super.onResume()
    }




}