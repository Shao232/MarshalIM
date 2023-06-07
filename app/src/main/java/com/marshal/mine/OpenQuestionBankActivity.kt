package com.marshal.mine

import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.IMPath.OPEN_QUESTION_BANK
import com.marshal.baseview.BaseViewActivity
import com.marshal.databinding.ActivityOpenQuestionBankBinding
import com.marshal.utils.FileUtils
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator


@Route(path = OPEN_QUESTION_BANK)
class OpenQuestionBankActivity : BaseViewActivity<ActivityOpenQuestionBankBinding>() {


    override fun getResLayoutBinding(): View? {
        binding = ActivityOpenQuestionBankBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        Thread(OpenReadQuestionRunnable()).start()

    }


    inner class OpenReadQuestionRunnable : Runnable {
        override fun run() {

            val file = FileUtils.copyAssetsResFile(
                "计算机应用基础.xls",
                "${FileUtils.app_cacheDir_path}/open/",
                "计算机应用基础.xls"
            )

            val zzExcelCreator = ZzExcelCreator
                .getInstance()
                .openExcel(file)
                .openSheet(0)
            //读取单元格内容
            //读取单元格内容
            val writableSheet = zzExcelCreator.writableSheet
            Log.d("TAG", "columns: 有${writableSheet.columns}列")
            Log.d("TAG", "rows: 有${writableSheet.rows}行")

            var index = 0
            val array1 = writableSheet.getColumn(0)
            val array2 = writableSheet.getColumn(1)
            val array3 = writableSheet.getColumn(2)
            val array4 = writableSheet.getColumn(3)
            val array5 = writableSheet.getColumn(4)


            Log.d("TAG", "array:${array1.forEach { Log.d("TAG", it.contents) }}")


            //别忘了close
            zzExcelCreator.close()
        }

    }


}