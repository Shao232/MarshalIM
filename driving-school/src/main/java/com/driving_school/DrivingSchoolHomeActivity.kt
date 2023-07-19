package com.driving_school

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.driving_school.DrivingRouterPath.Driving_HOME_PATH
import com.driving_school.databinding.ActivityDrivingSchoolHomeBinding
import com.driving_school.db.QuestionsDataBase
import com.driving_school.db.QuestionsMetaData
import com.marshal.base_common.baseview.BaseViewActivity


@Route(path = Driving_HOME_PATH)
class DrivingSchoolHomeActivity : BaseViewActivity<ActivityDrivingSchoolHomeBinding>() {

    var myDatabase: SQLiteDatabase? = null


    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityDrivingSchoolHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        if (hasIncludeToolbar) {
            setTitle("驾校模拟")
        }


        val dbHelper = QuestionsDataBase(this, QuestionsMetaData.DATABASE_NAME,null, 1)
        myDatabase = dbHelper.readableDatabase
        val cursor = myDatabase?.rawQuery("select * from collectionsSubject1",null)
        if(cursor!=null) {
            while(cursor.moveToNext()) {
                val id = cursor.getColumnIndex(QuestionsMetaData.MetaData.ID)
                val answer = cursor.getColumnIndex(QuestionsMetaData.MetaData.ANSWER)
                val explains = cursor.getColumnIndex(QuestionsMetaData.MetaData.EXPLAINS)
                Log.d("TAG","id --${id}, answer --${answer}, explains --${explains}")
            }
        }


        cursor?.close()
        myDatabase?.close()
    }
}