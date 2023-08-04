package com.driving_school.home

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.driving_school.DrivingRouterPath
import com.driving_school.DrivingRouterPath.Driving_Practice_PATH
import com.driving_school.R
import com.driving_school.bean.DrivingBean
import com.driving_school.bean.QuestionsBean
import com.driving_school.databinding.ActivityPracticeBinding
import com.driving_school.getDrivingCollectQuestion
import com.driving_school.getDrivingCorrectQuestion
import com.driving_school.getDrivingCorrectQuestionFourList
import com.driving_school.getDrivingErrorQuestion
import com.driving_school.getDrivingErrorQuestionFourList
import com.driving_school.getDrivingSubjectFour
import com.driving_school.getDrivingSubjectOne
import com.driving_school.getDrivingTestCurrentPosition
import com.driving_school.home.adapter.PracticeAdapter
import com.driving_school.putDrivingCollectQuestion
import com.driving_school.putDrivingCorrectQuestion
import com.driving_school.putDrivingErrorQuestion
import com.driving_school.putDrivingErrorQuestionFourList
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
     */
    private var receiveType = 0
    private var adapter: PracticeAdapter? = null

    // 1 科目1 4 科目4
    private var subjectType = 0

    //answerCorrect 正确回答的数量 answerError 错误回答的数量
    private var answerCorrect: Int = 0
    private var answerError: Int = 0

    //总题目数量
    private var answerCount: Int = 0

    //当前练习时的位置
    private var testCurrentPosition = 1
    private var examCurrentPosition = 1

    //收藏集
    private var collectQuestionList: ArrayList<QuestionsBean>? = ArrayList()

    //正确集
    private var correctQuestionList: ArrayList<QuestionsBean>? = ArrayList()

    //科四正确集合
    private var correctQuestionFourList: ArrayList<QuestionsBean>? = ArrayList()

    //错题集
    private var errorQuestionList: ArrayList<QuestionsBean>? = ArrayList()

    //科四错题集
    private var errorQuestionFourList: ArrayList<QuestionsBean>? = ArrayList()


    //59分59秒 * 60秒 * 1000毫秒 + 59秒 = 3150秒，每隔1秒执行一次onTick方法
    private var timer = object : CountDownTimer(60 * 60 * 1000, 1000L) {
        override fun onTick(millisUntilFinished: Long) {
            val minutes = (millisUntilFinished / (60 * 1000)) % 60
            val seconds = (millisUntilFinished % (60 * 1000)) / 1000
            binding?.tvTimerExam?.text = "${minutes}:${seconds}"
        }

        override fun onFinish() {
            Log.d("TAG", "考试结束 <<<<<<<<")
            //完成时 处理逻辑
            examResult()
        }

    }

    override fun hasToolbar(): Boolean {
        return true
    }

    override fun getResLayoutBinding(): View? {
        binding = ActivityPracticeBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun initView() {
        receiveType = intent.getIntExtra("testOrExam", 0)
        subjectType = intent.getIntExtra("subjectType", 0)

        if (receiveType == 0) {
            testCurrentPosition = getDrivingTestCurrentPosition()
            parseErrorQuestionList()
            parseErrorQuestionListFour()
            parseCorrectQuestionList()
            parseCorrectQuestionListFour()
            parseCollectQuestionList()
        }

        if (hasIncludeToolbar) {
            val title = when (receiveType) {
                0 -> "顺序练习"
                1 -> "模拟考试"
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

        if (receiveType == 1) {
            //模拟考试
            timer.start()
        }

        adapter = PracticeAdapter(receiveType)
        setSubjectData()

        binding?.viewpagerSubject?.adapter = adapter
        //禁止用户滑动
        binding?.viewpagerSubject?.isUserInputEnabled = false
        binding?.viewpagerSubject?.offscreenPageLimit = 5
        //receiveType 0 练习时
        if (receiveType == 0) {
            //如果是练习 从缓存中获取用户当前做的题目位置
            if (testCurrentPosition != 0) {
                binding?.viewpagerSubject?.currentItem = testCurrentPosition
            }
            if(adapter?.itemList?.isNotEmpty() == true) {
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
                putDrivingTestCurrentPosition(currentItemPosition)
                setShowQuestionCount(currentItemPosition)
                val bean = adapter?.itemList?.get(currentItemPosition)
                if (bean?.isHasCollection == true) {
                    binding?.ivCollectionSubject?.setImageResource(R.drawable.collectionsed_img)
                } else {
                    binding?.ivCollectionSubject?.setImageResource(R.drawable.my_collections_img)
                }

                //完成时 处理逻辑
                if (position == count) {
                    if (receiveType == 1) {
                        timer.cancel()
                        examResult()
                    }
                }
            }

            override fun onItemTwoSelectQuestion(position: Int, correct: Boolean) {
                //如果是考试，不需要保存
                if (receiveType == 1) return
                val bean = adapter?.itemList?.get(position)
                //当每一题判断是否回答正确
                if (subjectType == 1) {
                    if (correct) addCorrectQuestion(bean) else addErrorQuestion(bean)
                } else {
                    if (correct) addCorrectQuestionFour(bean) else addErrorQuestionFour(bean)
                }

                if (subjectType == 1) {
                    answerCorrect = correctQuestionList?.size ?: 0
                    answerError = errorQuestionList?.size ?: 0
                } else {
                    answerCorrect = errorQuestionFourList?.size ?: 0
                    answerError = errorQuestionFourList?.size ?: 0
                }

                binding?.tvYesSubject?.text = "$answerCorrect"
                binding?.tvNoSubject?.text = "$answerError"
            }
        })

        binding?.lvnCollectionSubject?.setOnClickListener {
            val position = binding?.viewpagerSubject?.currentItem ?: 0
            val bean = adapter?.itemList?.get(position)
            bean?.isHasCollection = bean?.isHasCollection == false
            Log.d("TAG", "处理前 collect:${collectQuestionList?.size}")
            if (bean?.isHasCollection == true) {
                binding?.ivCollectionSubject?.setImageResource(R.drawable.collectionsed_img)
                addCollectionList(bean)
            } else {
                binding?.ivCollectionSubject?.setImageResource(R.drawable.my_collections_img)
                val collectionListFindBean = collectQuestionList?.find {
                    it.question == bean?.question && it.explains == bean?.explains
                }
                collectQuestionList?.remove(collectionListFindBean)
                val collectionJson = GsonUtils.objToJson(collectQuestionList ?: "")
                putDrivingCollectQuestion(collectionJson)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    private fun examResult() {
        var examCount = 0
        adapter?.itemList?.forEach {
            var success = false
            if (it.selectItemAnswer.isNotEmpty()) {
               success = it.answer == it.selectItemAnswer
            }
            if(success){
                examCount++
            }
        }
        Log.d("TAG","测试结果: ${examCount}")
        ARouter.getInstance().build(DrivingRouterPath.Driving_Exam_Result_Path)
            .withInt("resultScore", examCount)
            .navigation()
        finish()
    }

    @SuppressLint("SetTextI18n")
    private fun setShowQuestionCount(currentItemPosition: Int) {
        val showPosition = currentItemPosition + 1
        binding?.tvQuestionCount?.text = "$showPosition/$answerCount"
    }

    /**
     * 添加收藏集
     */
    private fun addCollectionList(bean: QuestionsBean?) {
        var addSuccess = false
        if (collectQuestionList.isNullOrEmpty() && bean?.isHasCollection == true) {
            addSuccess = true
            bean.let { collectQuestionList?.add(it) }
        } else {
            val hasCollectionBean =
                collectQuestionList?.find { it.question == bean?.question && it.explains == bean?.explains }
            if (hasCollectionBean == null) {
                addSuccess = true
                bean?.let { collectQuestionList?.add(it) }
            }
        }

        if (addSuccess) {
            if (collectQuestionList?.isNotEmpty() == true) {
                val collectionJson = GsonUtils.objToJson(collectQuestionList ?: "")
                putDrivingCollectQuestion(collectionJson)
            }
        }
    }

    /**
     * 添加科目1的正确题目
     */
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
                val correctJson = GsonUtils.objToJson(correctQuestionList ?: "")
                putDrivingCorrectQuestion(correctJson)
            }
        }
    }

    private fun addCorrectQuestionFour(bean: QuestionsBean?) {
        var addSuccess = false
        if (correctQuestionFourList.isNullOrEmpty()) {
            addSuccess = true
            bean?.let { correctQuestionFourList?.add(it) }
        } else {
            val hasErrorBean =
                correctQuestionFourList?.find { it.question == bean?.question && it.explains == bean?.explains }
            if (hasErrorBean == null) {
                addSuccess = true
                bean?.let { correctQuestionFourList?.add(it) }
            }
        }

        if (addSuccess) {
            if (correctQuestionFourList?.isNotEmpty() == true) {
                val correctJson = GsonUtils.objToJson(correctQuestionFourList ?: "")
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
                val errorJson = GsonUtils.objToJson(errorQuestionList ?: "")
                putDrivingErrorQuestion(errorJson)
            }
        }
    }

    private fun addErrorQuestionFour(bean: QuestionsBean?) {
        var addSuccess = false
        if (errorQuestionFourList.isNullOrEmpty()) {
            addSuccess = true
            bean?.let { errorQuestionFourList?.add(it) }
        } else {
            val hasErrorBean =
                errorQuestionFourList?.find { it.question == bean?.question && it.explains == bean?.explains }
            if (hasErrorBean == null) {
                addSuccess = true
                bean?.let { errorQuestionFourList?.add(it) }
            }
        }

        if (addSuccess) {
            if (errorQuestionFourList?.isNotEmpty() == true) {
                val errorJson = GsonUtils.objToJson(errorQuestionFourList ?: "")
                putDrivingErrorQuestionFourList(errorJson)
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
        answerCorrect = correctQuestionList?.size ?: 0
    }

    private fun parseCorrectQuestionListFour() {
        val correctJson = getDrivingCorrectQuestionFourList()
        if (correctJson.isEmpty()) return
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestionsBean>>() {}.type
        val dataList: ArrayList<QuestionsBean> = gson.fromJson(correctJson, type)
        correctQuestionFourList?.clear()
        correctQuestionFourList?.addAll(dataList)
        answerCorrect = correctQuestionFourList?.size ?: 0
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
        answerError = errorQuestionList?.size ?: 0
    }

    private fun parseErrorQuestionListFour() {
        val errorQuestionJson = getDrivingErrorQuestionFourList()
        if (errorQuestionJson.isEmpty()) return
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestionsBean>>() {}.type
        val dataList: ArrayList<QuestionsBean> = gson.fromJson(errorQuestionJson, type)
        errorQuestionFourList?.clear()
        errorQuestionFourList?.addAll(dataList)
        answerError = errorQuestionFourList?.size ?: 0
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

        //如果是考试，随机取100道题目，后续操作取消
        if (receiveType == 1) {
            val randomList = adapter?.itemList?.shuffled()?.take(100)
            adapter?.itemList?.clear()
            randomList?.let { adapter?.itemList?.addAll(it) }
            //统计总数
            answerCount = adapter?.itemCount ?: 0
            return
        } else {
            //统计总数
            answerCount = adapter?.itemCount ?: 0
        }

        //初始化数据 + 正确和错误回答的融合
        if (subjectType == 1) {
            if (correctQuestionList?.isNotEmpty() == true) {
                adapter?.itemList?.forEach { allData ->
                    val correctHasBean =
                        correctQuestionList?.find { it.question == allData.question && it.explains == allData.explains }
                    if (correctHasBean != null) {
                        allData.isCompleteAnswer = correctHasBean.isCompleteAnswer
                        allData.selectItemAnswer = correctHasBean.selectItemAnswer
                    }
                }
            }

            if (errorQuestionList?.isNotEmpty() == true) {
                adapter?.itemList?.forEach { allData ->
                    val errorHasBean =
                        errorQuestionList?.find { it.question == allData.question && it.explains == allData.explains }
                    if (errorHasBean != null) {
                        allData.isCompleteAnswer = errorHasBean.isCompleteAnswer
                        allData.selectItemAnswer = errorHasBean.selectItemAnswer
                    }
                }
            }
        } else {
            if (correctQuestionFourList?.isNotEmpty() == true) {
                adapter?.itemList?.forEach { allData ->
                    val correctHasBean =
                        correctQuestionFourList?.find { it.question == allData.question && it.explains == allData.explains }
                    if (correctHasBean != null) {
                        allData.isCompleteAnswer = correctHasBean.isCompleteAnswer
                        allData.selectItemAnswer = correctHasBean.selectItemAnswer
                    }
                }
            }

            if (errorQuestionFourList?.isNotEmpty() == true) {
                adapter?.itemList?.forEach { allData ->
                    val errorHasBean =
                        errorQuestionFourList?.find { it.question == allData.question && it.explains == allData.explains }
                    if (errorHasBean != null) {
                        allData.isCompleteAnswer = errorHasBean.isCompleteAnswer
                        allData.selectItemAnswer = errorHasBean.selectItemAnswer
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

        Log.d("TAG", "全数据源 all->${adapter?.itemList?.toString()}")
    }

}