package com.driving_school.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.driving_school.DrivingRouterPath.Driving_Practice_PATH
import com.driving_school.bean.DrivingBean
import com.driving_school.bean.QuestionsBean
import com.driving_school.databinding.ActivityPracticeBinding
import com.driving_school.getDrivingCollectQuestion
import com.driving_school.getDrivingCorrectQuestion
import com.driving_school.getDrivingErrorQuestion
import com.driving_school.getDrivingSubjectFour
import com.driving_school.getDrivingSubjectOne
import com.driving_school.getDrivingTestCurrentPosition
import com.driving_school.home.adapter.PracticeAdapter
import com.driving_school.putDrivingCorrectQuestion
import com.driving_school.putDrivingErrorQuestion
import com.driving_school.putDrivingTestCurrentPosition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.base_common.utils.GsonUtils

/**
 * 顺序练习
 */
@Route(path = Driving_Practice_PATH)
class PracticeActivity : BaseViewActivity<ActivityPracticeBinding>() {

    /**
     * 0 练习
     * 1 考试
     * 2 收藏集
     * 3 错题集
     */
    private var receiveType = 0
    private var adapter: PracticeAdapter? = null
    private var subjectType = 0

    //answerCorrect 正确回答的数量 answerError 错误回答的数量
    private var answerCorrect: Int = 0
    private var answerError: Int = 0
    //总题目数量
    private var answerCount:Int = 0
    //当前练习时的位置
    private var testCurrentPosition = 0
    private var examCurrentPosition = 1

    //收藏集
    private var collectQuestionList: ArrayList<QuestionsBean>? = ArrayList()

    //正确集
    private var correctQuestionList: ArrayList<QuestionsBean>? = ArrayList()

    //错题集
    private var errorQuestionList: ArrayList<QuestionsBean>? = ArrayList()

    override fun hasToolbar(): Boolean {
        return true
    }

    override fun getResLayoutBinding(): View? {
        binding = ActivityPracticeBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        receiveType = intent.getIntExtra("testOrExam", 0)
        subjectType = intent.getIntExtra("subjectType", 0)

        if (receiveType == 0) {
            testCurrentPosition = getDrivingTestCurrentPosition()
            parseErrorQuestionList()
            parseCorrectQuestionList()
        }

        if (hasIncludeToolbar) {

            val title = when (receiveType) {
                0 -> "顺序练习"
                1 -> "模拟考试"
                2 -> "我的收藏"
                3 -> "我的错题"
                else -> ""
            }
            setTitle(title)
        }

        if (receiveType == 0) {
            //练习 倒计时隐藏
            binding?.lvnTimeSubject?.visibility = View.GONE
            binding?.lvnCollectionSubject?.visibility = View.VISIBLE
            binding?.lvnTestCorrectSubject?.visibility = View.VISIBLE
            binding?.lvnTestErrorSubject?.visibility = View.VISIBLE
            binding?.tvQuestionCount?.visibility = View.VISIBLE
            binding?.tvYesSubject?.text = "$answerCorrect"
            binding?.tvNoSubject?.text = "$answerError"

        } else {
            binding?.lvnTimeSubject?.visibility = View.VISIBLE
            binding?.tvQuestionCount?.visibility = View.VISIBLE
        }

        adapter = PracticeAdapter()
        setSubjectData()
        binding?.viewpagerSubject?.adapter = adapter
        //禁止用户滑动
        binding?.viewpagerSubject?.isUserInputEnabled = false
        binding?.viewpagerSubject?.offscreenPageLimit = 5
        //receiveType 0 练习时
        if(receiveType == 0) {
            //如果是练习 从缓存中获取用户当前做的题目位置
            if (testCurrentPosition != 0) {
                binding?.viewpagerSubject?.currentItem = testCurrentPosition
            }
            binding?.tvQuestionCount?.text="$testCurrentPosition/$answerCount"
        }else {
            binding?.viewpagerSubject?.currentItem = 0
            binding?.tvQuestionCount?.text="$examCurrentPosition/$answerCount"
        }

        adapter?.setOnNextQuestionSelect(object : PracticeAdapter.PracticeItemSelectClick {
            override fun onItemOneSelectClick(position: Int, direction: Int) {
                val count = if (adapter?.itemCount == 0) 0 else (adapter?.itemCount ?: 0) - 1
                if (direction == 1) {
                    val beforePosition = position - 1
                    binding?.viewpagerSubject?.currentItem =
                        if (beforePosition <= 0) 0 else beforePosition
                } else {
                    if (position >= count) {
                        //如果是最后一题，完成全部练习
                    } else {
                        val nextPosition = position + 1
                        //点击切换到下一题
                        binding?.viewpagerSubject?.currentItem =
                            if (nextPosition >= count) position else nextPosition
                    }
                }
                val currentItemPosition = binding?.viewpagerSubject?.currentItem ?: 0
                putDrivingTestCurrentPosition(currentItemPosition)
                binding?.tvQuestionCount?.text="$currentItemPosition/$answerCount"
            }

            override fun onItemTwoSelectQuestion(position: Int, correct: Boolean) {
                val bean = adapter?.itemList?.get(position)
                //当每一题判断是否回答正确
                if (correct) {
                    addCorrectQuestion(bean)
                } else {
                    addErrorQuestion(bean)
                }

                answerCorrect = correctQuestionList?.size ?: 0
                answerError = errorQuestionList?.size ?: 0
                binding?.tvYesSubject?.text = "$answerCorrect"
                binding?.tvNoSubject?.text = "$answerError"
            }
        })
    }


    private fun addCorrectQuestion(bean: QuestionsBean?) {
        var addSuccess = false
        if (correctQuestionList.isNullOrEmpty()) {
            addSuccess = true
            bean?.let { correctQuestionList?.add(it) }
        } else {
            val hasErrorBean =
                correctQuestionList?.find { it.question == bean?.question && it.explains == bean?.explains }
            if (hasErrorBean == null) {
                addSuccess = true
                bean?.let { correctQuestionList?.add(it) }
            }
        }

        if (addSuccess) {
            if (correctQuestionList?.isNotEmpty() == true) {
                Log.d("TAG", "correctQuestionList:${correctQuestionList.toString()}")
                val correctJson = GsonUtils.objToJson(correctQuestionList ?: "")
                Log.d("TAG", "CorrectJson:${correctJson}")
                putDrivingCorrectQuestion(correctJson)
            }
        }
    }

    /**
     * 添加错题到错题集
     * 判断是否有重复
     */
    private fun addErrorQuestion(bean: QuestionsBean?) {
        var addSuccess = false
        if (errorQuestionList.isNullOrEmpty()) {
            addSuccess = true
            bean?.let { errorQuestionList?.add(it) }
        } else {
            val hasErrorBean =
                errorQuestionList?.find { it.question == bean?.question && it.explains == bean?.explains }
            if (hasErrorBean == null) {
                addSuccess = true
                bean?.let { errorQuestionList?.add(it) }
            }
        }

        if (addSuccess) {
            if (errorQuestionList?.isNotEmpty() == true) {
                Log.d("TAG", "errorQuestionList:${errorQuestionList.toString()}")
                val errorJson = GsonUtils.objToJson(errorQuestionList ?: "")
                Log.d("TAG", "errorJson:${errorJson}")
                putDrivingErrorQuestion(errorJson)
            }
        }
    }

    /**
     * 解析正确题的集合
     */
    private fun parseCorrectQuestionList() {
        val correctJson = getDrivingCorrectQuestion()
        if (correctJson.isEmpty()) return
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestionsBean>>() {}.type
        val dataList: ArrayList<QuestionsBean> = gson.fromJson(correctJson, type)
        correctQuestionList?.clear()
        correctQuestionList?.addAll(dataList)
        Log.d("TAG", "correct size:${correctQuestionList?.size}")
        answerCorrect = correctQuestionList?.size ?: 0
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
        Log.d("TAG", "error size:${errorQuestionList?.size}")
        answerError = errorQuestionList?.size ?: 0
    }

    /**
     * 解析收藏集
     */
    private fun parseCollectQuestionList() {
        val collectJson = getDrivingCollectQuestion()
        if (collectJson.isEmpty()) return
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestionsBean>>() {}.type
        val dataList: ArrayList<QuestionsBean> = gson.fromJson(collectJson, type)
        collectQuestionList?.clear()
        collectQuestionList?.addAll(dataList)
    }

    private fun setSubjectData() {
        if (subjectType == 1) {
            val subjectOneJson = getDrivingSubjectOne()
            val gson = Gson()
            val data = gson.fromJson(subjectOneJson, DrivingBean::class.java)
            val questionList = data.result
            adapter?.itemList?.clear()
            questionList?.let { adapter?.itemList?.addAll(it) }
        } else {
            val subjectOneJson = getDrivingSubjectFour()
            val gson = Gson()
            val data = gson.fromJson(subjectOneJson, DrivingBean::class.java)
            val questionList = data.result
            adapter?.itemList?.clear()
            questionList?.let { adapter?.itemList?.addAll(it) }
        }

        //初始化数据 + 正确和错误回答的融合
        if (correctQuestionList?.isNotEmpty() == true) {
            adapter?.itemList?.forEach { allData ->
                val correctHasBean =
                    correctQuestionList?.find { it.question == allData.question && it.explains == allData.explains }
                if(correctHasBean !=null) {
                    allData.isCompleteAnswer = correctHasBean.isCompleteAnswer
                    allData.selectItemAnswer = correctHasBean.selectItemAnswer
                }
            }
        }

        if (errorQuestionList?.isNotEmpty() == true) {
            adapter?.itemList?.forEach { allData ->
                val errorHasBean =
                    errorQuestionList?.find { it.question == allData.question && it.explains == allData.explains }
                if(errorHasBean !=null) {
                    allData.isCompleteAnswer = errorHasBean.isCompleteAnswer
                    allData.selectItemAnswer = errorHasBean.selectItemAnswer
                }
            }
        }

        Log.d("TAG","全数据源 all->${adapter?.itemList?.toString()}")
        answerCount = adapter?.itemCount?:0
    }


}