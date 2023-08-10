package com.driving_school.home.practice

import android.os.Bundle
import android.util.Log
import com.driving_school.R
import com.driving_school.bean.DrivingBean
import com.driving_school.getDrivingSubjectThree
import com.google.gson.Gson

class TestAndExamSubjectThreeFragment:TestAndExamBaseFragment() {

    companion object{
        fun getInstance(bundle: Bundle):TestAndExamSubjectThreeFragment {
            val fragment = TestAndExamSubjectThreeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
        receiveType = arguments?.getInt("testOrExam",0)?:0
        subjectType = arguments?.getInt("subjectType",0)?:0
        super.initView()

        if (receiveType == 0) {
            when (subjectType) {
                3 -> {
                    parseCorrectQuestionListThree()
                    parseErrorQuestionListThree()
                }
                else -> {}
            }
            parseCollectQuestionList()
        }

        setSubjectData()

        //receiveType 0 练习时
        if (receiveType == 0) {
            binding?.viewpagerSubject?.currentItem = testCurrentPosition
            if (adapter?.itemList?.isNotEmpty() == true) {
                val bean = adapter?.itemList?.get(testCurrentPosition)
                if (bean?.isHasCollection == true) {
                    binding?.ivCollectionSubject?.setImageResource(R.drawable.collectionsed_img)
                } else {
                    binding?.ivCollectionSubject?.setImageResource(R.drawable.my_collections_img)
                }
                setShowQuestionCount(testCurrentPosition)
            }
        } else {
            binding?.viewpagerSubject?.currentItem = 0
            binding?.tvQuestionCount?.text = "$examCurrentPosition/$answerCount"
        }

    }

    private fun setSubjectData() {
        when(subjectType){
            3->{
                val subjectThreeJson = getDrivingSubjectThree()
                if(subjectThreeJson.isNotEmpty()) {
                    val gson = Gson()
                    val data = gson.fromJson(subjectThreeJson, DrivingBean::class.java)
                    val questionList = data.result
                    adapter?.itemList?.clear()
                    questionList?.let { adapter?.itemList?.addAll(it) }
                }
            }
            else->{}
        }

        //如果是考试，随机取100道题目，后续操作取消
        if (receiveType == 1) {
            //考试时
            val randomList = adapter?.itemList?.shuffled()?.take(100)
            adapter?.itemList?.clear()
            randomList?.let { adapter?.itemList?.addAll(it) }
            //统计总数
            answerCount = adapter?.itemCount ?: 0
            return
        } else {
            //练习时
            //统计总数
            answerCount = adapter?.itemCount ?: 0

            when(subjectType){
                3->{
                  //练习时 统计正确和错误题目
                    //初始化数据 + 正确和错误回答的融合
                    if (correctQuestionThreeList?.isNotEmpty() == true) {
                        adapter?.itemList?.forEach { allData ->
                            val correctHasBean =
                                correctQuestionThreeList?.find { it.question == allData.question && it.explains == allData.explains }
                            if (correctHasBean != null) {
                                allData.isCompleteAnswer = correctHasBean.isCompleteAnswer
                                allData.selectItemAnswer = correctHasBean.selectItemAnswer
                            }
                        }
                    }

                    if (errorQuestionThreeList?.isNotEmpty() == true) {
                        adapter?.itemList?.forEach { allData ->
                            val errorHasBean =
                                errorQuestionThreeList?.find { it.question == allData.question && it.explains == allData.explains }
                            if (errorHasBean != null) {
                                allData.isCompleteAnswer = errorHasBean.isCompleteAnswer
                                allData.selectItemAnswer = errorHasBean.selectItemAnswer
                            }
                        }
                    }
                }
            }

            //如果收藏不为空，设置题目为收藏状态
            if (collectQuestionList?.isNotEmpty() == true) {
                adapter?.itemList?.forEach { allData ->
                    val hasCollectionItem = collectQuestionList?.find { it.id == allData.id }
                    if (hasCollectionItem != null) {
                        allData.isHasCollection = hasCollectionItem.isHasCollection
                    }
                }
            }
        }
        Log.d("TAG", "全数据源 all->${adapter?.itemList?.toString()}")
    }

}