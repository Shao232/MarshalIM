package com.driving_school.home

import android.annotation.SuppressLint
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.driving_school.DrivingRouterPath.Driving_Collection_Error_Path
import com.driving_school.bean.QuestionsBean
import com.driving_school.databinding.ActivityMineCollectionAndErrorBinding
import com.driving_school.getDrivingCollectQuestion
import com.driving_school.getDrivingErrorQuestion
import com.driving_school.getDrivingErrorQuestionFourList
import com.driving_school.home.adapter.PracticeAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshal.base_common.baseview.BaseViewActivity

@Route(path = Driving_Collection_Error_Path)
class MineCollectionAndErrorActivity : BaseViewActivity<ActivityMineCollectionAndErrorBinding>() {

    /**
     * 2 收藏集
     * 3 错题集
     */
    private var receiveType = 0

    private var adapter: PracticeAdapter? = null

    //收藏集
    private var collectQuestionList: ArrayList<QuestionsBean>? = ArrayList()

    //错题集
    private var errorQuestionList: ArrayList<QuestionsBean>? = ArrayList()

    //科四错题集
    private var errorQuestionFourList: ArrayList<QuestionsBean>? = ArrayList()

    private var currentPosition = 0

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityMineCollectionAndErrorBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        receiveType = intent.getIntExtra("collectionOrError", 0)

        if (hasIncludeToolbar) {
            val title = when (receiveType) {
                2 -> "我的收藏"
                3 -> "错题集"
                else -> ""
            }
            setTitle(title)
        }

        adapter = PracticeAdapter()
        if (receiveType == 2) {
            loadCollectionQuestionData()
        } else {
            loadErrorQuestionData()
        }


        binding?.drivingButtonMyCollection?.setQuestionCountVisibility(true)
        binding?.drivingButtonMyCollection?.setQuestionCountContent("${currentPosition}/${adapter?.itemList?.size}")
        binding?.viewpagerSubject?.adapter = adapter
        //禁止用户滑动
        binding?.viewpagerSubject?.isUserInputEnabled = false
        binding?.viewpagerSubject?.offscreenPageLimit = 5
        binding?.viewpagerSubject?.currentItem = currentPosition
        if (adapter?.itemList?.isNotEmpty() == true) {
            val bean = adapter?.itemList?.get(currentPosition)
            binding?.drivingButtonMyCollection?.setCollectionVisibility(true)
            binding?.drivingButtonMyCollection?.setCollectionStatus(bean?.isHasCollection == true)
        } else {
            binding?.drivingButtonMyCollection?.setCollectionVisibility(false)
        }


        adapter?.setOnNextQuestionSelect(object : PracticeAdapter.PracticeItemSelectClick {
            override fun onItemOneSelectClick(position: Int, direction: Int) {
                val count = if (adapter?.itemCount == 0) 0 else (adapter?.itemCount ?: 0) - 1
                if (direction == 1) {
                    val beforePosition = position - 1
                    binding?.viewpagerSubject?.currentItem =
                        if (beforePosition <= 0) 0 else beforePosition
                } else {
                    val nextPosition = position + 1
                    binding?.viewpagerSubject?.currentItem =
                        if (nextPosition > count) position else nextPosition

                }
                val currentItemPosition = binding?.viewpagerSubject?.currentItem ?: 0
                setShowQuestionCount(currentItemPosition)
                val bean = adapter?.itemList?.get(currentItemPosition)
                binding?.drivingButtonMyCollection?.setCollectionStatus(bean?.isHasCollection == true)

                if (position == count) {
                    finish()
                }

            }

            override fun onItemTwoSelectQuestion(position: Int, correct: Boolean) {
                val bean = adapter?.itemList?.get(position)
                //当每一题判断是否回答正确

            }
        })

    }

    @SuppressLint("SetTextI18n")
    private fun setShowQuestionCount(currentItemPosition: Int) {
        val showPosition = currentItemPosition + 1
        binding?.drivingButtonMyCollection?.setQuestionCountContent("${showPosition}/${adapter?.itemList?.size}")
    }

    private fun loadCollectionQuestionData() {
        parseCollectQuestionList()
        adapter?.itemList?.clear()
        collectQuestionList?.let { adapter?.itemList?.addAll(it) }

    }

    private fun loadErrorQuestionData() {
        parseErrorQuestionList()
        parseErrorQuestionListFour()
        errorQuestionList?.let { adapter?.itemList?.addAll(it) }
        errorQuestionFourList?.let { adapter?.itemList?.addAll(it) }
    }

    private fun parseCollectQuestionList() {
        val collectJson = getDrivingCollectQuestion()
        if (collectJson.isEmpty()) return
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestionsBean>>() {}.type
        val dataList: ArrayList<QuestionsBean> = gson.fromJson(collectJson, type)
        collectQuestionList?.clear()
        collectQuestionList?.addAll(dataList)
    }

    /**
     * 解析错题集
     */
    private fun parseErrorQuestionList() {
        val errorQuestionJson = getDrivingErrorQuestion()
        if (errorQuestionJson.isEmpty()) return
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestionsBean>>() {}.type
        val dataList: ArrayList<QuestionsBean> = gson.fromJson(errorQuestionJson, type)
        errorQuestionList?.clear()
        errorQuestionList?.addAll(dataList)
    }

    private fun parseErrorQuestionListFour() {
        val errorQuestionJson = getDrivingErrorQuestionFourList()
        if (errorQuestionJson.isEmpty()) return
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestionsBean>>() {}.type
        val dataList: ArrayList<QuestionsBean> = gson.fromJson(errorQuestionJson, type)
        errorQuestionFourList?.clear()
        errorQuestionFourList?.addAll(dataList)
    }

}