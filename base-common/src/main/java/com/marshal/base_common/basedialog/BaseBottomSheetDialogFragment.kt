package com.marshal.base_common.basedialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseBottomSheetDialogFragment<T: ViewBinding>: BottomSheetDialogFragment() {

    var rootView:View? = null
    var binding:T? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_TITLE,getDialogTheme())
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(getResLayoutId(), container)
        return getResLayoutBinding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(dialog !=null) {
            // 获取BottomSheetBehavior对象

            // 获取BottomSheetBehavior对象
            val behavior = BottomSheetBehavior.from(
                dialog?.findViewById(R.id.design_bottom_sheet) as? View?:return
            )
            behavior.peekHeight = getPeekHeight()
        }

        initView()

        subscribeBack()
    }

    open fun getDialogTheme():Int{
        return 0
    }

    abstract fun getResLayoutId():Int

    abstract fun getResLayoutBinding(): View?

    open fun getPeekHeight():Int = resources.getDimensionPixelOffset(com.marshal.base_common.R.dimen.bottom_200_dp)

    abstract fun initView()

    open fun subscribeBack(){}

}