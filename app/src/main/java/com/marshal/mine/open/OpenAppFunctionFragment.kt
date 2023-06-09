package com.marshal.mine.open

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.marshal.mine.adapter.OpenQuestionAdapter
import com.marshal.sharedata.CommitShareData


/**
 * 应用基础
 */
class OpenAppFunctionFragment : OpenQuestionBaseFragment() {

    private val adapter = OpenQuestionAdapter()

    override fun getAdapter():  RecyclerView.Adapter<RecyclerView.ViewHolder>? {
        return adapter as?  RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun viewCreate() {
        super.viewCreate()
        adapter.addListAll(CommitShareData.appSingleQuestions)
    }

    override fun onResume() {
        super.onResume()
    }

    fun updateList(type:Int){
        adapter.itemList.clear()
        when(type){
            1->{
                adapter.addListAll(CommitShareData.appSingleQuestions)
            }
            2->{
                adapter.addListAll(CommitShareData.appMultipleQuestions)
            }
            3->{
                adapter.addListAll(CommitShareData.appEstimateQuestions)
            }
        }
        layoutManager?.scrollToPosition(0)
    }
}