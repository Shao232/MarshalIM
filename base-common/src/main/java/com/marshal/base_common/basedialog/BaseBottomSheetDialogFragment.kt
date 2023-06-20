package com.marshal.base_common.basedialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<T: ViewBinding>: BottomSheetDialogFragment() {

    var rootView:View? = null
    var binding:T? = null

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


        initView()

        subscribeBack()
    }


    abstract fun getResLayoutId():Int

    abstract fun getResLayoutBinding(): View?

    abstract fun initView()

    open fun subscribeBack(){}

}